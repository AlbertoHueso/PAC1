package com.pla1.cifo.ahuesoa.pac1.dummy;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pla1.cifo.ahuesoa.pac1.model.BookItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 *
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<BookItem> ITEMS = new ArrayList<BookItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, BookItem> ITEM_MAP = new HashMap<String, BookItem>();


    /**
     * Número de elementos de muestra
     */
    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyBookItem(i));
        }
    }

    /**
     * Añadimos el libro book al mapa
     * @param book
     */
    private static void addItem(BookItem book) {
        ITEMS.add(book);
        ITEM_MAP.put(Integer.toString(book.getIdentificador()), book);
    }

    /**
     * Se crea un Dummy Book de muestra
     * @param position el número que distinguirá cada libro, es la posición en la lista
     * @return BookItem el libro muestra
     */
    private static BookItem createDummyBookItem(int position) {
        return new BookItem("Title " + position, "author " + position, new Date(), "Descripcion " + position,
                "urlImagen " + position);
    }

}