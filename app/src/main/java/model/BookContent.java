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
     * Retorna una lista con los diferentes libros
     * @return
     */
    public  List<BookItem> getBooks(){
        List<BookItem> list=(List<BookItem>)  this;
        return list;
    }

    /**
     * Retorna si el libro bookItem está en BookContent
     * @param bookItem
     * @return Boolean que dice si está o no
     */
    public  boolean exists(BookItem bookItem){
        return this.contains(bookItem);
    }

}
