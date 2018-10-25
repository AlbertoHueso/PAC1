package com.pla1.cifo.ahuesoa.pac1.dummy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.BookContent;
import model.BookItem;

/**
 * Clase para guardar algunas funciones que se utilizarán en diferentes clases
 *
 */
public class Funciones {

    /**
     * A partir de una fecha en formato "dd/MM/yyyy" se obtiene la fecha en formato string
     * @param date
     * @return
     */
    public  static String getDateFormated(Date date){
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha=sdf.format(date);
        return fecha;

    }

    /**
     * A partir de un string formato "dd/MM/yyyy" lo convierte en una fecha
     * @param s
     * @return
     */
    public static Date getDateFromString(String s){
        Date date=null;

        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Método para convertir un ArrayList de BookItem en un BookContent
     * @param booksArrayList
     * @return BookContent con los libros BookItem del ArrayList
     */
    public static BookContent toBookContent(List<BookItem> booksArrayList){
        BookContent books=new BookContent();
        Iterator it=booksArrayList.iterator();
        while (it.hasNext()){
           books.add( it.next());
        }
        return books;
    }
}
