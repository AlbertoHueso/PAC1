package com.pla1.cifo.ahuesoa.pac1.dummy;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Integer.valueOf;
import java.text.SimpleDateFormat;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyBook> ITEMS = new ArrayList<DummyBook>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyBook> ITEM_MAP = new HashMap<String, DummyBook>();



    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyBook item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.identificador, item);
    }

    private static DummyBook createDummyItem(int position) {
        return new DummyBook(valueOf(position).toString(), "Title " + position, "author "+position, "Descripcion "+position,
                "urlImagen "+position);
    }


    /**
     *Clase que representa cada libro de la lista Dummy para rellenar por defecto
     */
    public static class DummyBook {
        public String identificador;
        public String titulo;
        public String autor;
        public String publicacionFecha;
        public String descripcion;
        public String urlImagen;

        public DummyBook(String identificador, String titulo, String autor, String descripcion, String urlImagen) {
            this.identificador=identificador;
            this.titulo=titulo;
            this.autor=autor;
            this.publicacionFecha=getDate(); //Como solo es una muestra asignamos la fecha actual
            this.descripcion=descripcion;
            this.urlImagen=urlImagen;
        }



        /**
         * Obtiene un String con la fecha y hora en el formato dd/MM/yyyy
         * @return String con fecha y hora
         */
        public  static String getDate(){
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date=new Date();
            String fecha=sdf.format(date);
            return fecha;

        }
    }
}
