package EjericiosWriter;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Bufferwritered1 {
    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\Alumno\\Desktop\\notas.txt";
        List<String> lineas=new ArrayList<>();

        try {
            lineas = Files.readAllLines(Path.of(rutaArchivo));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter fw = new BufferedWriter(new FileWriter(new File(rutaArchivo)))) {

            fw.write("Esta es la prinera línea del archivo\n");

            for (String linea : lineas) {
                fw.write(linea+"\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
