package JBDC;

import java.sql.*;
import java.util.Scanner;
import java.io.IOException;

public class GestionLibreria {


    private static String url = "jdbc:mysql://localhost:3306/libreria";
    private static String usuario = "root";
    private static String contraseña = "1234";


    public static void main(String[] args) {
        mostrarMenu();
    }

    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }


    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Menú Genérico ===");
            System.out.println("1. Comprar libro");
            System.out.println("2. Vender libro");
            System.out.println("3. Mostrar libros");
            System.out.println("4. Acción 4");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();
            System.out.println();


            switch (opcion) {
                case 1 -> comprarLibro();
                case 2 -> venderLibro();
                case 3 ->  mostrarLibro(0);
                case 4 -> comprarLibro();
                case 5 -> System.out.println("Gracias por usar la aplicación");
                default -> System.out.println("Opcion no valida vuelve a intentarlo") ;
            }

        } while (opcion != 5);

        scanner.close();
    }


    public static void comprarLibro() {
        Scanner scanner = new Scanner(System.in);
        String titulo;
        String autor;
        String genero;
        int stock;
        double precio;

        System.out.println("Introduce el titulo de libro");
        titulo = scanner.nextLine();
        System.out.println("Introduce el autor de libro");
        autor = scanner.nextLine();
        System.out.println("Introduce el genero de libro");
        genero = scanner.nextLine();
        do {
            System.out.println("Introduce el stock de libro");
            stock = scanner.nextInt();
            if (stock <= 0 ) System.out.println("El estock tiene que ser mayor que 0 \n ");
        }while (stock <= 0);

        do {

            System.out.println("Introduce el precio de libro");
            precio = scanner.nextDouble();
            if (precio <= 0) System.out.println("El precio tiene que ser mayor que 0 \n");
        }while (precio <= 0);

        insertarLibro(titulo, autor, genero, stock, precio);
    }

    public static void insertarLibro(String titulo, String autor, String genero, int stock, double precio) {

        String query = "INSERT INTO libros (titulo, autor, genero, stock, precio) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = establecerConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, autor);
            preparedStatement.setString(3, genero);
            preparedStatement.setInt(4, stock);
            preparedStatement.setDouble(5, precio);
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("El libro se ha agregado exitosamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el libro");
            throw new RuntimeException(e);
        }

    }

    public static void venderLibro(){
        Scanner scanner = new Scanner(System.in);


        try(Connection connection = establecerConexion()){

            mostrarLibro(0);

            String queryComprobarID= "SELECT * FROM libros WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryComprobarID)){
                boolean repetir =true;
                do {

                    System.out.print("Seleccione el id del libro que desea comprar: ");
                    int id = scanner.nextInt();
                    preparedStatement.setInt(1, id);
                    ResultSet resultado = preparedStatement.executeQuery();
                    if (resultado.next()) {
                        repetir = false;
                        System.out.println("Libro seleccionado:");
                        mostrarLibro(id);
                    }else {
                        repetir = true;


                    }
                }while (repetir);
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void mostrarLibro(int idSeleccionado) {
        try(Connection connection = establecerConexion()){
            String queryConsultaLibros ="";
            if (idSeleccionado==0){
                 queryConsultaLibros = "SELECT * FROM libros";
            }else {
                 queryConsultaLibros = "SELECT * FROM libros WHERE id = "+idSeleccionado;
            }


            try (PreparedStatement preparedStatement = connection.prepareStatement(queryConsultaLibros);
                 ResultSet resultado = preparedStatement.executeQuery()){

                System.out.printf("%-10s %-20s %-20s %-15s %-10s %-10s%n","id" ,"Título", "Autor", "Género", "Stock", "Precio");
                System.out.println("--------------------------------------------------------------------------------------");


                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String genero = resultado.getString("genero");
                    int stock = resultado.getInt("stock");
                    double precio = resultado.getDouble("precio");

                    System.out.printf("%-10d %-20s %-20s %-15s %-10d %-10.2f%n", id, titulo, autor, genero, stock, precio);
                }
                System.out.println("\n");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}