package Ejercicio3;

public class Main {
    public static void main(String[] args) {
        CuentaBancaria cuenta1 = new CuentaBancaria("ES298",500);

        try {
            cuenta1.retirarDinero(5000);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        try {
            cuenta1.retirarDinero(40);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            System.out.println("el codigo se ha terminado de ejecutar ");
            try {
                cuenta1.retirarDinero(5000);

            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            finally {
                System.out.println("finally 2");
            }
        }



        System.out.println(cuenta1.getDinero());
    }
}
