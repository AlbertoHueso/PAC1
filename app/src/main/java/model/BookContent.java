package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el conjunto de libros
 * @param <BookItem>
 */
public class BookContent<BookItem> extends ArrayList {

    /*
    Constructor
     */
    public BookContent() {
        super();
    }



    /**
     * Retorna si el libro bookItem está en BookContent
     * @param bookItem
     * @return Boolean que dice si está o no
     */
    public  boolean exists(BookItem bookItem){
        return this.contains(bookItem);
    }


    /**
     * Método que retorna una lista con todos los BookItem en la base de datos
     * @return ArrayList<model.BookItem>
     */
    public static ArrayList<model.BookItem> getBooks(){
        return (ArrayList<model.BookItem>)model.BookItem.listAll(model.BookItem.class);
    }
}
