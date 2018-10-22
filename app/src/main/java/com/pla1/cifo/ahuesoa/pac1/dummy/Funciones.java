package com.pla1.cifo.ahuesoa.pac1.dummy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
