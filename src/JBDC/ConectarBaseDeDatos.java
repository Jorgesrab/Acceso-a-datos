package JBDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectarBaseDeDatos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/miBaseDeDatos";
        String usuario = "root";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
             Statement statement = connection.createStatement()) {

            String consulta = "SELECT * FROM productos";
            ResultSet resultSet = statement.executeQuery(consulta);

            while (resultSet.next()) {
                System.out.println("Producto: " + resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}