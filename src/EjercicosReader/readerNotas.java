package EjercicosReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readerNotas {
    public static void main(String[] args) {
        String ruta = "C:\\Users\\Alumno\\Desktop\\notas.txt";

        StringBuilder notas = new StringBuilder("Las notas son: ");
        List <Integer> listaNotas = new ArrayList<Integer>();


        try(BufferedReader reader = new BufferedReader(new FileReader(ruta))){
            String linea = reader.readLine();
            String[] separados = linea.split("\\W+");
            for(String separado : separados){
                if (separado.matches("\\d+")){
                    notas.append(separado).append(", ");
                    listaNotas.add(Integer.parseInt(separado));
                }

            }



        }catch (IOException e){
            System.out.println("Error al leer el archivo");
        }
        System.out.println(notas);
    }
}
