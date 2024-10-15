package Ejercicio4;

import java.util.Scanner;

public class verificarContraseña {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Escribe la contraseña");
        String contrasenia = sc.nextLine();
        try {

            veridicarContrasenia(contrasenia);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void veridicarContrasenia(String contrasenia) throws Exception {
        if (contrasenia.length()<=8 || !contrasenia.matches(".*\\d.*")){
            throw new Exception("ContraseniaInvalidaException");
        }else {
            System.out.println("Contraseña validada");
        }


    }

}
