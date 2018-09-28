package com.pla1.cifo.ahuesoa.pac1.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.valueOf;

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
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();



    private static final int COUNT = 10;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.identificador, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(valueOf(position).toString(), "Title " + position, "autor "+position,"Fecha Publicacion " +position, "Descripcion "+position,
                "urlImagen "+position);
    }


    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String identificador;
        public String titulo;
        public String autor;
        public String publicacionFecha;
        public String descripcion;
        public String urlImagen;

        public DummyItem(String identificador,String titulo, String autor,String publicacionFecha, String descripcion,String urlImagen) {
            this.identificador=identificador;
            this.titulo=titulo;
            this.autor=autor;
            this.publicacionFecha=publicacionFecha;
            this.descripcion=descripcion;
            this.urlImagen=urlImagen;
        }

        /**
         * Convierte un DummyBook en un String con el título, autor, publicacionFecha, descripción y url de la imagen
         * @return String con los datos del libro
         */
        public String dummyBookToString() {
            return titulo+"\n"+autor+"\n"+publicacionFecha+"\n"+descripcion+"\n"+urlImagen+"\n";
        }
    }
}
