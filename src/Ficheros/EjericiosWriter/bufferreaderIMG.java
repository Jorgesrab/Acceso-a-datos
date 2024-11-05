package Ficheros.EjericiosWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class bufferreaderIMG {
    public static void main(String[] args) {


        String rutaArchivo = "C:\\Users\\Alumno\\Desktop\\descargar.jpg";


        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea= br.readLine())!=null){
                System.out.println(linea);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
