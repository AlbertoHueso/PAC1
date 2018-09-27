package model;

import java.util.Date;


/**
 * Clase para guardar los libros
 */
public class BookItem {
    private int identificador;
    private String titulo;
    private String autor;
    private Date publicacionFecha;
    private String descripcion;
    private String urlImagen;


    /**
     * Constructor de BookItem
     * @param identificador Identificador del BookItem
     * @param titulo Titulo del BookItem
     * @param autor  Autor del BookItem
     * @param publicacionFecha  Fecha de publicacion del BookItem
     * @param descripcion  Descripcion del BookItem
     * @param urlImagen     URL de la imagen del BookItem
     */
    public BookItem(int identificador,String titulo, String autor,Date publicacionFecha, String descripcion,String urlImagen){

        this.identificador=identificador;
        this.titulo=titulo;
        this.autor=autor;
        this.publicacionFecha=publicacionFecha;
        this.descripcion=descripcion;
        this.urlImagen=urlImagen;

    }

    /**
     * Obtiene el identificador del BookItem
     * @return identificador
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Obtiene el titulo del BookItem
     * @return titulo
     */
    public String getTitulo(){
        return titulo;
    }

    /**
     * Obtiene el autor del BookItem
     * @return autor
     */
    public String getAutor(){
        return autor;
    }

    /**
     * Obtiene la fecha de publicacion del BookItem
     * @return publicacionFecha
     */
    public Date getPublicacionFecha(){
        return publicacionFecha;
    }

    /**
     * Obtiene la descripción del BookItem
     * @return descripcion
     */
    public String getDescripcion(){
        return descripcion;
    }

    /**
     * Obtiene la dirección URL de la imagen
     * @return urlImagen
     */
    public String getUrlImagen(){
        return urlImagen;
    }

    /**
     * Actualiza el identificador
     * @param identificador int Identificador
     */
    public void setIdentificador(int identificador){
        this.identificador=identificador;
    }

    /**
     * Actualiza el titulo
     * @param titulo String titulo
     */
    public void setTitulo(String titulo){
        this.titulo=titulo;
    }

    /**
     * Actualiza el autor
     * @param autor String autor
     */
    public void setAutor(String autor){
        this.autor=autor;
    }

    /**
     * Asigna la descripción
     * @param descripcion String Descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Asigna la fecha de publicacion
     * @param publicacionFecha Date Fecha de la publicacion
     */
    public void setPublicacionFecha(Date publicacionFecha) {
        this.publicacionFecha = publicacionFecha;
    }

    /**
     * Asigna la URL de la imagen
     * @param urlImagen String URL De la imagen
     */
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
