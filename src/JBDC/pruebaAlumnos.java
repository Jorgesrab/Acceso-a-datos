package JBDC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pruebaAlumnos {


    private static String url = "jdbc:mysql://localhost:3306/pruebausuarios";
    private static String usuario = "root";
    private static String contraseña = "1234";


    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
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
}