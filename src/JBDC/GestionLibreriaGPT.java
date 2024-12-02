package JBDC;// Package: JBDC
import java.sql.*;
import java.util.Scanner;

public class GestionLibreriaGPT {

    // Configuración de la conexión a la base de datos
    private static final String url = "jdbc:mysql://localhost:3306/libreria";
    private static final String usuario = "root";
    private static final String contraseña = "1234";

    public static void main(String[] args) {
        mostrarMenu();
    }

    // Establece la conexión a la base de datos
    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    // Muestra el menú principal al usuario
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Comprar libro");
            System.out.println("2. Vender libro");
            System.out.println("3. Mostrar libros");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Registrar devolución");
            System.out.println("6. Consultar ventas por género");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpieza del buffer

            switch (opcion) {
                case 1 -> comprarLibro();
                case 2 -> venderLibro();
                case 3 -> mostrarLibro(0);
                case 4 -> eliminarLibro();
                case 5 -> registrarDevolucion();
                case 6 -> consultarVentasPorGenero();
                case 7 -> System.out.println("Gracias por usar la aplicación");
                default -> System.out.println("Opción no válida, inténtalo de nuevo");
            }
        } while (opcion != 7);

        scanner.close();
    }

    // Permite agregar un libro al inventario
    public static void comprarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Introduce el autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("Introduce el género del libro: ");
        String genero = scanner.nextLine();
        System.out.print("Introduce el stock del libro (número positivo): ");
        int stock = scanner.nextInt();
        System.out.print("Introduce el precio del libro (número positivo): ");
        double precio = scanner.nextDouble();

        // Validaciones básicas
        if (stock <= 0 || precio <= 0) {
            System.out.println("El stock y el precio deben ser mayores a 0.");
            return;
        }

        // Verificar si el libro ya existe en el inventario
        if (libroYaExiste(titulo, autor)) {
            System.out.println("El libro ya existe en el inventario.");
            return;
        }

        // Inserta el libro en la base de datos
        String query = "INSERT INTO libros (titulo, autor, genero, stock, precio) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, autor);
            preparedStatement.setString(3, genero);
            preparedStatement.setInt(4, stock);
            preparedStatement.setDouble(5, precio);
            preparedStatement.executeUpdate();
            System.out.println("El libro se ha agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el libro: " + e.getMessage());
        }
    }

    // Valida si un libro con el mismo título y autor ya existe en el inventario
    private static boolean libroYaExiste(String titulo, String autor) {
        String query = "SELECT COUNT(*) AS total FROM libros WHERE titulo = ? AND autor = ?";
        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, autor);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del libro: " + e.getMessage());
        }
        return false;
    }

    // Permite realizar la venta de un libro
    public static void venderLibro() {
        Scanner scanner = new Scanner(System.in);
        mostrarLibro(0);

        System.out.print("Introduce el ID del libro a vender: ");
        int id = scanner.nextInt();

        String querySelect = "SELECT stock FROM libros WHERE id = ?";
        String queryUpdate = "UPDATE libros SET stock = stock - 1 WHERE id = ?";

        try (Connection connection = establecerConexion()) {
            connection.setAutoCommit(false); // Inicia una transacción

            try (PreparedStatement selectStmt = connection.prepareStatement(querySelect);
                 PreparedStatement updateStmt = connection.prepareStatement(queryUpdate)) {
                selectStmt.setInt(1, id);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int stock = rs.getInt("stock");
                    if (stock > 0) {
                        updateStmt.setInt(1, id);
                        updateStmt.executeUpdate();
                        connection.commit(); // Confirma la transacción
                        System.out.println("Venta realizada con éxito.");
                    } else {
                        throw new SQLException("Stock insuficiente para realizar la venta.");
                    }
                } else {
                    throw new SQLException("Libro no encontrado.");
                }
            } catch (SQLException e) {
                connection.rollback(); // Revertir cambios en caso de error
                System.out.println("Error en la venta: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Muestra los libros del inventario
    public static void mostrarLibro(int idSeleccionado) {
        String query = (idSeleccionado == 0) ? "SELECT * FROM libros" : "SELECT * FROM libros WHERE id = ?";
        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (idSeleccionado != 0) {
                preparedStatement.setInt(1, idSeleccionado);
            }
            ResultSet resultado = preparedStatement.executeQuery();

            System.out.printf("%-10s %-20s %-20s %-15s %-10s %-10s%n", "ID", "Título", "Autor", "Género", "Stock", "Precio");
            System.out.println("----------------------------------------------------------------------------------");
            while (resultado.next()) {
                System.out.printf("%-10d %-20s %-20s %-15s %-10d %-10.2f%n",
                        resultado.getInt("id"),
                        resultado.getString("titulo"),
                        resultado.getString("autor"),
                        resultado.getString("genero"),
                        resultado.getInt("stock"),
                        resultado.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los libros: " + e.getMessage());
        }
    }

    // Elimina un libro del inventario
    public static void eliminarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del libro a eliminar: ");
        int id = scanner.nextInt();
        String query = "DELETE FROM libros WHERE id = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Libro eliminado correctamente.");
            } else {
                System.out.println("No se encontró el libro con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el libro: " + e.getMessage());
        }
    }

    // Permite registrar una devolución de un libro
    public static void registrarDevolucion() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del libro a devolver: ");
        int id = scanner.nextInt();
        String query = "UPDATE libros SET stock = stock + 1 WHERE id = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Devolución registrada correctamente.");
            } else {
                System.out.println("No se encontró el libro con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar la devolución: " + e.getMessage());
        }
    }

    // Consulta el total de ventas por género
    public static void consultarVentasPorGenero() {
        try (Connection connection = establecerConexion();
             CallableStatement callableStatement = connection.prepareCall("{CALL calcular_total_ventas_por_genero()}")) {
            ResultSet resultSet = callableStatement.executeQuery();

            System.out.printf("%-15s %-10s%n", "Género", "Ventas");
            System.out.println("-------------------------------");
            while (resultSet.next()) {
                String genero = resultSet.getString("genero");
                double totalVentas = resultSet.getDouble("total_ventas");
                System.out.printf("%-15s %-10.2f%n", genero, totalVentas);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las ventas por género: " + e.getMessage());
        }
    }
}
