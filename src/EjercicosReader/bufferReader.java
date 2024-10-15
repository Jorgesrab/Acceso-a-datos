package EjercicosReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bufferReader {
    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\Alumno\\Desktop\\nostrum.txt";
        int contador = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;

            while ((linea = reader.readLine())!=null){
                contador+=1;
            }

        }catch (IOException e){
            System.out.println("Error al leer el archivo");
        }

        System.out.println("El archivo tiene "+contador+" lineas");
    }
}
