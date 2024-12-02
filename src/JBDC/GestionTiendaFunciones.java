package JBDC;

import java.sql.*;
import java.util.Scanner;

public class GestionTiendaFunciones {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Método principal para ejecutar el menú
    public static void main(String[] args) {
        mostrarMenu();
    }

    // Establecer conexión con la base de datos
    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Mostrar el menú de opciones
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("=== Menú de Gestión de Tienda ===");
            System.out.println("1. Insertar Producto");
            System.out.println("2. Actualizar Precio de Producto");
            System.out.println("3. Eliminar Cliente");
            System.out.println("4. Mostrar Todos los Productos");
            System.out.println("5. Contar Productos en Stock");
            System.out.println("6. Mostrar Productos más Caros");
            System.out.println("7. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea restante

            switch (opcion) {
                case 1 -> insertarProducto();
                case 2 -> actualizarPrecioProducto();
                case 3 -> eliminarCliente();
                case 4 -> mostrarTodosLosProductos();
                case 5 -> contarProductosEnStock();
                case 6 -> mostrarProductosMasCaros();
                case 7 -> System.out.println("Gracias por usar la aplicación");
                default -> System.out.println("Opción no válida, vuelve a intentarlo.");
            }

        } while (opcion != 7);
    }

    // 1. Función para insertar un producto nuevo
    public static void insertarProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del producto:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el precio del producto:");
        double precio = scanner.nextDouble();
        System.out.println("Introduce la cantidad en stock:");
        int stock = scanner.nextInt();

        String query = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Producto insertado correctamente.");
            } else {
                System.out.println("Error al insertar el producto.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
        }
    }

    // 2. Función para actualizar el precio de un producto por su ID
    public static void actualizarPrecioProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del producto a actualizar:");
        int idProducto = scanner.nextInt();
        System.out.println("Introduce el nuevo precio:");
        double nuevoPrecio = scanner.nextDouble();

        String query = "UPDATE productos SET precio = ? WHERE id = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setInt(2, idProducto);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Precio actualizado correctamente.");
            } else {
                System.out.println("Producto no encontrado o no se pudo actualizar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el precio: " + e.getMessage());
        }
    }

    // 3. Función para eliminar un cliente por su ID
    public static void eliminarCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del cliente a eliminar:");
        int idCliente = scanner.nextInt();

        String query = "DELETE FROM clientes WHERE id = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idCliente);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    // 4. Función para mostrar todos los productos en la tienda
    public static void mostrarTodosLosProductos() {
        String query = "SELECT * FROM productos";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Nombre", "Precio", "Stock");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10.2f %-10d%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los productos: " + e.getMessage());
        }
    }

    // 5. Función para contar los productos que tienen stock
    public static void contarProductosEnStock() {
        String query = "SELECT COUNT(*) AS cantidad FROM productos WHERE stock > 0";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                System.out.println("Número de productos con stock disponible: " + cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar productos en stock: " + e.getMessage());
        }
    }

    // 6. Función para mostrar los productos más caros
    public static void mostrarProductosMasCaros() {
        String query = "SELECT * FROM productos ORDER BY precio DESC LIMIT 5";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Nombre", "Precio", "Stock");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10.2f %-10d%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar los productos más caros: " + e.getMessage());
        }
    }
}
