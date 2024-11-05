package Ficheros.EjercicosReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class bifferReaderBuscapalabras {
    public static void main(String[] args) {
        String ruta = "C:\\Users\\Alumno\\Desktop\\nostrum.txt";
        String palabraBusar ="Robert";
        int contador = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))){
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.contains(palabraBusar)){
                    String[] palabras = linea.split("\\W+");
                    for (String palabra : palabras){
                        if (palabra.equalsIgnoreCase(palabraBusar)){
                            contador+=1;
                        }

                    }
                }

            }

        }catch (IOException e){
            System.out.println("Error de lectura");
        }

        if (contador == 0){
            System.out.println("La palabra "+palabraBusar+" no se ha encontrado");

        }else {
            System.out.println("La palabra "+palabraBusar+" se ha encontrado "+ contador+" veces");
        }
    }
}
