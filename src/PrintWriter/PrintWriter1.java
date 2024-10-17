package PrintWriter;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class PrintWriter1 {
    public static void main(String[] args) {
        // Nombre del archivo donde se escribirá el contenido
        String nombreArchivo = "C:\\Users\\Alumno\\Desktop\\PrintWriter.txt";

        // Texto que se va a escribir en el archivo
        Integer contenido = 124;
        // Uso de FileWriter para escribir en el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println(contenido); // Escribir el contenido
            System.out.println("Contenido escrito en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}
