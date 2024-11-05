package Ficheros.XML;

public class Coche {

    private String nombre;
    private String duenio;
    private int id;

    public Coche(String nombre, String duenio, int id) {
        this.nombre = nombre;
        this.duenio = duenio;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDuenio() {
        return duenio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDuenio(String duenio) {
        this.duenio = duenio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
