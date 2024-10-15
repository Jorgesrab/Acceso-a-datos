package Ejercicio3;

public class CuentaBancaria {
    private String numreCuenta;
    private int dinero;

    public CuentaBancaria(String numreCuenta, int dinre) {
        this.numreCuenta = numreCuenta;
        this.dinero = dinre;
    }

    public CuentaBancaria() {
    }

    public String getNumreCuenta() {
        return numreCuenta;
    }

    public void setNumreCuenta(String numreCuenta) {
        this.numreCuenta = numreCuenta;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public void retirarDinero(int cantidad) throws Exception {
        if (cantidad > dinero){
            throw new Exception("No tienes dinero suficiente ");
        }else {
            dinero-=cantidad;
        }
    }

}
