package Ficheros.Encriptador;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Encriptador {

    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\Alumno\\Desktop\\notas.txt";
        List<String> lineas=new ArrayList<>();

        try{
            lineas = Files.readAllLines(Path.of(rutaArchivo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fw = new FileWriter(rutaArchivo)){


            StringBuilder textoModificado = new StringBuilder("");

            for(String linea : lineas){
                List<Character> listaCaracteres = new ArrayList<>();

                for (char c : linea.toCharArray()) {

                    char charSiguiente = (char) (c+1);
                    textoModificado.append(charSiguiente) ;

                }
                textoModificado.append("\n");

            }

            fw.write(textoModificado.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
