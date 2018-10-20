package model;

import com.pla1.cifo.ahuesoa.pac1.dummy.Funciones;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Clase para representar cada libro
 */
public class BookItem {

    private int identificador;
    private String title;
    private String author;
    private Date publication_date;
    private String description;
    private String url_image;


    /**
     * Constructor de BookItem
     * @param identificador Identificador del BookItem
     * @param titulo Titulo del BookItem
     * @param autor  Autor del BookItem
     * @param publication_date  Fecha de publicacion del BookItem
     * @param descripcion  Descripcion del BookItem
     * @param urlImagen     URL de la imagen del BookItem
     */
    public BookItem(int identificador,String titulo, String autor,Date publication_date, String descripcion,String urlImagen){

        this.identificador=identificador;
        this.title=titulo;
        this.author=autor;
        this.publication_date=publication_date;
        this.description=descripcion;
        this.url_image=urlImagen;

    }

    /**Constructor vacío, necesario para que se puedan crear objetos BookItem desde Firebase
     *
     */
    public BookItem(){

    }

    /**
     * Obtiene el identificador
     * @return int identificador
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Asigna el identificador
     * @param identificador
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    /**
     * Obtiene el título
     * @return String con el título
     */
    public String getTitle() {
        return title;
    }

    /**
     * Asigna el título
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene el autor
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     *Asigna el autor
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Obtiene la fecha de publicacion
     * @return String la fecha de publicación
     */
    public String getPublication_date() {
        String dateFormated= Funciones.getDateFormated(publication_date);
        return dateFormated;
    }

    /**
     * Asigna la fecha de publicacion a partir de un String
     * @param publication_date
     * @see Funciones#getDateFromString(String)
     */
    public void setPublication_date(String publication_date) {
        this.publication_date = Funciones.getDateFromString(publication_date);
    }

    /**
     * Obtiene la descripción del libro
     * @return String con la descripcion
     */
    public String getDescription() {
        return description;
    }

    /**
     * Asigna la descripcion del libro
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la url de la imagen del libro
     * @return String con url de la imagen
     */
    public String getUrl_image() {
        return url_image;
    }

    /**
     * Asigna la url de la imagen del libro
     * @param url_image
     */
    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }


}
