package JBDC.Transacciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Banco {

    // Configuración de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/transferenciabancaria";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "1234";

    // Método para establecer una conexión a la base de datos
    private static Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        double montoTransferencia = 100.00;
        String cuentaOrigen = "123456";
        String cuentaDestino = "654321";

        try {
            banco.realizarTransferencia(cuentaOrigen, cuentaDestino, montoTransferencia);
            System.out.println("Transacción completada con éxito.");
        } catch (Exception e) {
            System.err.println("Error durante la transacción: " + e.getMessage());
        }
    }

    // Método para realizar la transferencia
    public void realizarTransferencia(String cuentaOrigen, String cuentaDestino, double monto) throws SQLException {
        // Obtener la conexión utilizando el método establecerConexion()
        try (Connection connection = establecerConexion()) {
            // Desactivar el auto-commit para manejar la transacción manualmente
            connection.setAutoCommit(false);

            try {
                // Realizar las operaciones necesarias
                debitarCuenta(connection, cuentaOrigen, monto);
                acreditarCuenta(connection, cuentaDestino, monto);

                // Confirmar la transacción si ambas operaciones son exitosas
                connection.commit();
            } catch (SQLException e) {
                // Revertir la transacción en caso de fallo
                connection.rollback();
                throw new SQLException("Transacción fallida, revertida correctamente. Causa: " + e.getMessage(), e);
            } finally {
                // Restaurar el auto-commit a true para evitar problemas posteriores si se reutiliza la conexión
                connection.setAutoCommit(true);
            }
        }
    }

    // Método para debitar el monto de la cuenta origen
    private void debitarCuenta(Connection connection, String numeroCuenta, double monto) throws SQLException {
        String debitarCuentaSQL = "UPDATE cuenta SET saldo = saldo - ? WHERE numeroCuenta = ?";
        try (PreparedStatement debitarStmt = connection.prepareStatement(debitarCuentaSQL)) {
            debitarStmt.setDouble(1, monto);
            debitarStmt.setString(2, numeroCuenta);

            int filasAfectadas = debitarStmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo debitar de la cuenta origen, verifique los datos.");
            }
        }
    }

    // Método para acreditar el monto a la cuenta destino
    private void acreditarCuenta(Connection connection, String numeroCuenta, double monto) throws SQLException {
        String acreditarCuentaSQL = "UPDATE cuenta SET saldo = saldo + ? WHERE numeroCuenta = ?";
        try (PreparedStatement acreditarStmt = connection.prepareStatement(acreditarCuentaSQL)) {
            acreditarStmt.setDouble(1, monto);
            acreditarStmt.setString(2, numeroCuenta);

            int filasAfectadas = acreditarStmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo acreditar en la cuenta destino, verifique los datos.");
            }
        }
    }
}
