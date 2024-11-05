package Ficheros.PrintWriter;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class PrintWriter1 {
    public static void main(String[] args) {
        // Nombre del archivo donde se escribirá el contenido
        Scanner sc = new Scanner(System.in);
        String nombreArchivo = "C:\\Users\\Alumno\\Desktop\\PrintWriter.txt";


        System.out.println("Escribe un primer numero");
        int numero1 = sc.nextInt();

        System.out.println("escribe un segundo numero");
        int numero2 = sc.nextInt();

        int suma = numero1 + numero2;


        // Texto que se va a escribir en el archivo

        // Uso de FileWriter para escribir en el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.printf("Operacion: %d + %d = %d",numero1,numero2,suma); // Escribir el contenido
            System.out.println("Contenido escrito en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }
}
