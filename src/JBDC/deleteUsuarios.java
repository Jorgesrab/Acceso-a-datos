package JBDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class deleteUsuarios {

    private static String url = "jdbc:mysql://localhost:3306/pruebausuarios";
    private static String usuario = "root";
    private static String contraseña = "1234";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        boolean validateLogin = validateLogin(username, password);
        if (validateLogin) {
            System.out.println("Inicio de sesión exitoso");

            enseniarUsuarios();

            System.out.println("Se han modificado "+eliminarUsuarios()+" usuarios");

        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }

        scanner.close();
    }

    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    private static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM usuarios WHERE usuarios = ? AND contraseña = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void enseniarUsuarios(){
        try (Connection connection = establecerConexion();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM estudiantes");) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Estudiante: " + resultSet.getString("id") +"->"+ resultSet.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int eliminarUsuarios(){
        String query = "DELETE FROM estudiantes WHERE id = ?";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce el id del alumno que deseas eliminar ");
        String id = scanner.nextLine();

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            int resultSet = preparedStatement.executeUpdate();

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
           return 0;
        }
    }
}
