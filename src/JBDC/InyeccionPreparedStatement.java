package JBDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InyeccionPreparedStatement {

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
}
