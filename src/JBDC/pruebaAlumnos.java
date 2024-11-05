package JBDC;

import java.sql.*;
import java.util.Scanner;

public class pruebaAlumnos {


    private static String url = "jdbc:mysql://localhost:3306/pruebausuarios";
    private static String usuario = "root";
    private static String contraseña = "1234";

    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }


    public static void main(String[] args) {



        try (Connection connection = establecerConexion();
             Statement statement = connection.createStatement()) {


            String consulta = "SELECT * FROM estudiantes";
            ResultSet resultSet = statement.executeQuery(consulta);

            while (resultSet.next()) {
                System.out.println("estudiante: " + resultSet.getString("nombre")+" tiene "+resultSet.getString("edad")+" años");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void aniadirEstudiante() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese la edad: ");
        int edad = sc.nextInt();

        // Consulta SQL con parámetros para evitar inyección SQL
        String consulta = "INSERT INTO estudiantes (nombre, edad) VALUES (?, ?)";

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {

            // Asignación de parámetros en la consulta
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, edad);

            // Ejecutar la consulta
            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Estudiante añadido con éxito.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sc.close();  // Cierra el Scanner después del ingreso de datos
        }
    }


}