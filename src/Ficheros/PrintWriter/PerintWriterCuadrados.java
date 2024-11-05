package Ficheros.PrintWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class PerintWriterCuadrados {
    public static void main(String[] args) {
        String nombreArchivo = "C:\\Users\\Alumno\\Desktop\\PrintWriter.txt";

        try(PrintWriter pw = new PrintWriter(new FileWriter(new File(nombreArchivo),true))) {

            for (int i = 1; i <= 10; i++) {
                double cuadrado = i*i;
                pw.printf("El cuadrado de %d es igual a %f %n",i,cuadrado);
            }


      } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }
}
