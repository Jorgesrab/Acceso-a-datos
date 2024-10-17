package ModificarFicheros;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CambiarNombreFoto {
    public static void main(String[] args) {


        //Ruta del archivo original
        File rutaArchivoOriginal = new File("C:\\Users\\Alumno\\Desktop\\descargar.jpg");
        //Declaracion de la variable
        String fechaCreacion = "";

        //Llamada al metodo para obtener la fecha del archivo
        try{
            fechaCreacion=obtenerFecha(rutaArchivoOriginal.toPath());
        }catch (IOException e){
            e.printStackTrace();
        }




        //Directorios para el metodo cambiar nombre
        String directorioPadre = rutaArchivoOriginal.getParent();
        File nuevoArchivo = new File(directorioPadre,fechaCreacion+".jpg");


        cambiarNombre(rutaArchivoOriginal, nuevoArchivo);
    }


    //Metodo para cambiar el nombre
    private static void cambiarNombre(File rutaArchivoOriginal, File nuevoArchivo) {
        if (rutaArchivoOriginal.renameTo(nuevoArchivo)) {
            System.out.println("El archivo ha sido renombrado con éxito a: " + nuevoArchivo.getName());
        } else {
            System.out.println("Error al cambiar el nombre del archivo.");
        }
    }

    //Metodo para opbtener la fecha
    public static String obtenerFecha(Path rutaArchivo) throws IOException {
        BasicFileAttributes atributos = Files.readAttributes(rutaArchivo,BasicFileAttributes.class);

        FileTime fechaDeCreacion = atributos.creationTime();

        Date fecha = new Date(fechaDeCreacion.toMillis());

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

        return formatoFecha.format(fecha);
    }
}
