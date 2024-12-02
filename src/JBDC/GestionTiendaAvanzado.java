package JBDC;

import java.sql.*;
import java.util.Scanner;

public class GestionTiendaAvanzado {

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
            System.out.println("1. Agregar Producto");
            System.out.println("2. Vender Producto");
            System.out.println("3. Mostrar Productos");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Consultar Total de Ventas");
            System.out.println("6. Actualizar Stock (Procedimiento Almacenado)");
            System.out.println("7. Procesar Ventas en Lote");
            System.out.println("8. Ver Información de Empleados");
            System.out.println("9. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            System.out.println();

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> venderProducto();
                case 3 -> mostrarProductos();
                case 4 -> eliminarProducto();
                case 5 -> consultarTotalVentas();
                case 6 -> actualizarStock();
                case 7 -> procesarVentasEnLote();
                case 8 -> verEmpleados();
                case 9 -> System.out.println("Gracias por usar la aplicación");
                default -> System.out.println("Opción no válida, vuelve a intentarlo.");
            }

        } while (opcion != 9);
    }

    // Función para agregar un producto
    public static void agregarProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del producto:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el precio del producto:");
        double precio = scanner.nextDouble();
        System.out.println("Introduce el stock del producto:");
        int stock = scanner.nextInt();

        String query = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto agregado exitosamente.");
            } else {
                System.out.println("Error al agregar el producto.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Función para vender un producto (disminuir el stock)
    public static void venderProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del producto a vender:");
        int idProducto = scanner.nextInt();
        System.out.println("Introduce la cantidad a vender:");
        int cantidad = scanner.nextInt();

        try (Connection connection = establecerConexion()) {
            connection.setAutoCommit(false); // Desactivar autocommit para manejar transacciones

            // Verificar el stock disponible
            String queryStock = "SELECT stock FROM productos WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(queryStock)) {
                ps.setInt(1, idProducto);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int stockActual = rs.getInt("stock");

                    // Si hay suficiente stock, procedemos con la venta
                    if (stockActual >= cantidad) {
                        // Actualizar el stock del producto
                        String queryActualizarStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";
                        try (PreparedStatement psUpdate = connection.prepareStatement(queryActualizarStock)) {
                            psUpdate.setInt(1, cantidad);
                            psUpdate.setInt(2, idProducto);
                            psUpdate.executeUpdate();

                            // Registrar la venta
                            String queryInsertVenta = "INSERT INTO ventas (id_producto, cantidad) VALUES (?, ?)";
                            try (PreparedStatement psVenta = connection.prepareStatement(queryInsertVenta)) {
                                psVenta.setInt(1, idProducto);
                                psVenta.setInt(2, cantidad);
                                psVenta.executeUpdate();

                                // Confirmar transacción
                                connection.commit();
                                System.out.println("Venta realizada con éxito.");
                            }
                        }
                    } else {
                        System.out.println("No hay suficiente stock para realizar la venta.");
                    }
                } else {
                    System.out.println("Producto no encontrado.");
                }
            } catch (SQLException e) {
                connection.rollback(); // Deshacer cambios en caso de error
                System.out.println("Error durante la venta: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión o transacción: " + e.getMessage());
        }
    }

    // Función para mostrar todos los productos disponibles
    public static void mostrarProductos() {
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
            System.out.println("Error al consultar los productos: " + e.getMessage());
        }
    }

    // Función para eliminar un producto
    public static void eliminarProducto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del producto a eliminar:");
        int idProducto = scanner.nextInt();

        String query = "DELETE FROM productos WHERE id = ?";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el producto.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    // Función para consultar el total de ventas por producto
    public static void consultarTotalVentas() {
        try (Connection connection = establecerConexion();
             CallableStatement cs = connection.prepareCall("{CALL total_ventas()}");
             ResultSet rs = cs.executeQuery()) {

            System.out.printf("%-20s %-10s%n", "Producto", "Ventas");
            while (rs.next()) {
                System.out.printf("%-20s %-10d%n", rs.getString("nombre"), rs.getInt("total_ventas"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar las ventas: " + e.getMessage());
        }
    }

    // Función para actualizar el stock de un producto usando un procedimiento almacenado
    public static void actualizarStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el ID del producto:");
        int idProducto = scanner.nextInt();
        System.out.println("Introduce la cantidad a agregar al stock:");
        int cantidad = scanner.nextInt();

        try (Connection connection = establecerConexion()) {
            CallableStatement cs = connection.prepareCall("{CALL actualizar_stock(?, ?)}");
            cs.setInt(1, idProducto);
            cs.setInt(2, cantidad);
            cs.executeUpdate();

            System.out.println("Stock actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el stock: " + e.getMessage());
        }
    }

    // Función para procesar ventas en lote (Batch Processing)
    public static void procesarVentasEnLote() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el número de ventas a procesar:");
        int cantidadVentas = scanner.nextInt();

        try (Connection connection = establecerConexion()) {
            connection.setAutoCommit(false); // Desactivar autocommit para el procesamiento en lote

            String query = "INSERT INTO ventas (id_producto, cantidad) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                for (int i = 0; i < cantidadVentas; i++) {
                    System.out.println("Introduce el ID del producto:");
                    int idProducto = scanner.nextInt();
                    System.out.println("Introduce la cantidad a vender:");
                    int cantidad = scanner.nextInt();

                    ps.setInt(1, idProducto);
                    ps.setInt(2, cantidad);
                    ps.addBatch(); // Agregar a lote
                }

                ps.executeBatch(); // Ejecutar lote
                connection.commit(); // Confirmar la transacción

                System.out.println("Ventas procesadas exitosamente.");
            } catch (SQLException e) {
                connection.rollback(); // Revertir cambios en caso de error
                System.out.println("Error durante el procesamiento de ventas: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    // Función para ver todos los empleados
    public static void verEmpleados() {
        String query = "SELECT * FROM empleados";

        try (Connection connection = establecerConexion();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-5s %-20s %-10s%n", "ID", "Nombre", "Salario");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("salario"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los empleados: " + e.getMessage());
        }
    }
}
