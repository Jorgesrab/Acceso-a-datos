package EjerccicioArticulo;

public class Main {
    public static void main(String[] args) {
        Articulo Fanta = new Articulo("fanta",1.25,10);

        System.out.println("Fanta: "+Fanta.getNombre()+" Precio: "+Fanta.getPVP()+" IVA: "+Fanta.getIVA());
        Fanta.vender(5);
        Fanta.almacenar(1);


    }
}