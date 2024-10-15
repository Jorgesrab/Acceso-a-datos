package ejerciciosç;

public class Main {
    public static void main(String[] args) {
        String texto = null;  // No se inicializa, por lo que es null

        try {
            // Intentamos llamar a un método sobre un objeto null
            System.out.println(texto.length());  // Esto lanzará el NullPointerException
        } catch (NullPointerException e) {
            // Capturamos el NullPointerException y mostramos un mensaje
            System.out.println("Ocurrió un NullPointerException: " +
                    "" +
                    "l objeto es null.");
        }
    }
}
