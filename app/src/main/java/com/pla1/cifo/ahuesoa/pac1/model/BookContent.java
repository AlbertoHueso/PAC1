package com.pla1.cifo.ahuesoa.pac1.model;

import com.pla1.cifo.ahuesoa.pac1.Funciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa el conjunto de libros
 */
public class BookContent {


    /**
     * Array de Libros.
     */
    public static final List<BookItem> BOOKS = new ArrayList<BookItem>();

    /**
     * Array de libros en un HashMap.
     */
    public static final Map<String, BookItem> BOOKS_MAP = new HashMap<String, BookItem>();

    private static final BookContent uniqueBooks=new BookContent();
    /**
     * Constructor privado, así solo habrá una instancia de BookContent, siguiendo el patrón Singleton
     */
    private BookContent() {
    }

    /**
     * Añadimos el libro book al mapa
     * @param book
     */
    public static void addBook(BookItem book) {
        BOOKS.add(book);
        BOOKS_MAP.put(Integer.toString(book.getIdentificador()), book);
    }

    /**
     * Método que devuelve si BookContent contiene un bookItem
     * @param bookItem
     * @return true si lo contiene y false en caso contrario.
     */
    public static boolean exists(BookItem bookItem){
        boolean exist=false;
        int identificador=bookItem.getIdentificador();
        if (BOOKS_MAP.containsKey(identificador)){
            exist=true;
        }
        return exist;
    }

    /**
     * Método que retorna una lista con todos los BookItem en la base de datos
     *@return List<BookItem>
     */
    public static List<BookItem> getBooks(){

    return BOOKS;
    }

}