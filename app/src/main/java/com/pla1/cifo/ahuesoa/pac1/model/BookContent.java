package com.pla1.cifo.ahuesoa.pac1.model;

import java.util.ArrayList;

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
    public  boolean exists(com.pla1.cifo.ahuesoa.pac1.model.BookItem bookItem){
        int index=bookItem.getIdentificador();
        com.pla1.cifo.ahuesoa.pac1.model.BookItem myBook;
        try{
           myBook=(com.pla1.cifo.ahuesoa.pac1.model.BookItem) this.get(index);

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
     * @return ArrayList<BookItem>
     */
    public static ArrayList<com.pla1.cifo.ahuesoa.pac1.model.BookItem> getBooks(){
        return (ArrayList<com.pla1.cifo.ahuesoa.pac1.model.BookItem>) com.pla1.cifo.ahuesoa.pac1.model.BookItem.listAll(com.pla1.cifo.ahuesoa.pac1.model.BookItem.class);
    }
}
