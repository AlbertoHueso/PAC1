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
     * Si no existe un elemento de BookContent en el índice igual al identificador del libro devuelve false
     * Si existe y sus identificadores son iguales devuelve true
     * @param bookItem
     * @return Boolean que dice si está o no
     */
    public  boolean exists(model.BookItem bookItem){
        int index=bookItem.getIdentificador();
        model.BookItem myBook;
        try{
           myBook=(model.BookItem) this.get(index);

           //Coinciden los identificadores, devuelve true, en el resto de casos false
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
