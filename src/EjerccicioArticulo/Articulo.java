package EjerccicioArticulo;

public class Articulo {
    private String nombre;
    private double precio;
    private static int IVA = 21;
    private int cuantosQuedan;

    public Articulo(String nombre, double precio, int cuantosQuedan) {
        this.nombre = nombre;
        this.precio = precio;
        this.cuantosQuedan = cuantosQuedan;
    }

    public Articulo() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre!= ""){
        this.nombre = nombre;
            System.out.println("Operacion realizada");
        } else {
            System.out.println("Nombre incorrecto");

        }

    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio<=0){
            System.out.println("Precio incorrecto");
        } else {
            this.precio = precio;
            System.out.println("Operacion realizada");
        }
    }

    public static int getIVA() {
        return IVA;
    }


    public int getCuantosQuedan() {
        return cuantosQuedan;
    }

    public void setCuantosQuedan(int cuantosQuedan) {
        if (cuantosQuedan<0){
            System.out.println("valor incorrecto no puede ser negativo");
        }else {
            this.cuantosQuedan = cuantosQuedan;
            System.out.println("Operacion realizada");
        }
    }

    public double getPVP(){
        return precio + precio*IVA/100;
    }

    public double getPVPDescuento(double descuento){
        return this.getPVP() - this.getPVP()*descuento/100;
    }

    public  void vender(int cantidad ){

        if (cantidad > this.cuantosQuedan){
            System.out.println("Error no quedan suficioentes");
        }else {
            setCuantosQuedan(this.getCuantosQuedan()-cantidad);
            System.out.println("operacion rellizada conexito quedan "+this.getCuantosQuedan());
        }

    }

    public void almacenar(int cantidad){

        if (cantidad<=0){
            System.out.println("cantidad incorrecta debe ser superior a 0");
        }else {
            setCuantosQuedan(this.getCuantosQuedan()+cantidad);
            System.out.println("operacion rellizada conexito quedan "+this.cuantosQuedan);
        }


    }



}
