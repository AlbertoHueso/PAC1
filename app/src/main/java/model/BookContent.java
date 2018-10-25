package model;

import com.pla1.cifo.ahuesoa.pac1.BookListActivity;

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
    public  boolean exists(model.BookItem bookItem){
        int index=bookItem.getIdentificador();
        model.BookItem myBook;
        try{
           myBook=(model.BookItem) this.get(index);

           if (myBook.getIdentificador()==bookItem.getIdentificador()){
               return true;
           }else{
               return false;
           }

        }
        catch (IndexOutOfBoundsException e){
            return false;
        }

    }


    /**
     * Método que retorna una lista con todos los BookItem en la base de datos
     * @return ArrayList<model.BookItem>
     */
    public static ArrayList<model.BookItem> getBooks(){
        return (ArrayList<model.BookItem>)model.BookItem.listAll(model.BookItem.class);
    }
}
