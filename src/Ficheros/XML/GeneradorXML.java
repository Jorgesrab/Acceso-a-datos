package Ficheros.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class GeneradorXML {
    public static void main(String[] args) {
        try {

            ArrayList<Coche> coches = new ArrayList<>();

            coches.add(new Coche("Ford Fiesta", "Diego", 1));
            coches.add(new Coche("Citroen C4", "Jorge", 2));
            coches.add(new Coche("Seat Leon", "David", 3));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();

            Element raiz = document.createElement("Coches");
            document.appendChild(raiz);

            for (Coche cocheElement : coches) {
                Element coche = document.createElement("Coche");
                raiz.appendChild(coche);

                Element nombre = document.createElement("Nombre");
                nombre.appendChild(document.createTextNode(cocheElement.getNombre()));
                coche.appendChild(nombre);

                Element duenio = document.createElement("Dueño");
                duenio.appendChild(document.createTextNode(cocheElement.getDuenio()));
                coche.appendChild(duenio);

                Element identificador = document.createElement("ID");
                identificador.appendChild(document.createTextNode(String.valueOf(cocheElement.getId())));
                coche.appendChild(identificador);

            }

            // Núcleo del transformador.
            TransformerFactory tf = TransformerFactory.newInstance();
            // Creación del transformador usando el núcleo.
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Buscamos el origen.
            DOMSource source = new DOMSource(document);
            // Establecemos dónde se creará el destino.
            StreamResult resultado = new StreamResult(new File("C:\\Users\\Alumno\\Documents\\Projectos\\acceso a datos\\src\\XML\\Personas.xml"));

            // Transformamos el documento en un Ficheros.XML.
            transformer.transform(source, resultado);
            // Indicamos por pantalla que el archivo ha sido creado correctamente.
            System.out.println("Archivo creado con éxito");

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
