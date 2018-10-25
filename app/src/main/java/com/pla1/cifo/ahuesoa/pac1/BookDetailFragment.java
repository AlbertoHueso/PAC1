package com.pla1.cifo.ahuesoa.pac1;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import model.BookItem;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {
    /**
     * El argumento del fragmento representando el identificador del libro del fragmento
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * El argumento del fragmento representando el título del libro del fragmento
     */
    public static final String ARG_ITEM_TITLE = "item_title";
    /**
     * El argumento del fragmento representando el autor del libro del fragmento
     */
    public static final String ARG_ITEM_AUTHOR = "item_author";
    /**
     * El argumento del fragmento representando la descripción del libro del fragmento
     */
    public static final String ARG_ITEM_DESCRIPTION = "item_description";
    /**
     * El argumento del fragmento representando la url de la imagen del libro del fragmento
     */
    public static final String ARG_ITEM_URL_IMAGE = "item_url_image";
    /**
     * El argumento del fragmento representando la fecha de publicación del libro del fragmento
     */
    public static final String ARG_ITEM_PUBLICATION_DATE = "item_publication_date";

    /**
     * El libro que se va a representar
     */
    private BookItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            //Obtenemos los argumentos pasados al fragmento desde otra actividad, los datos de cada libro
            int id=Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
            String title=getArguments().getString(ARG_ITEM_TITLE);
            String description=getArguments().getString(ARG_ITEM_DESCRIPTION);
            Date publication_date= Funciones.getDateFromString(getArguments().getString(ARG_ITEM_PUBLICATION_DATE));
            String author= getArguments().getString(ARG_ITEM_AUTHOR);
            String url_image=getArguments().getString(ARG_ITEM_URL_IMAGE);
         ;
            //Creamos el nuevo elemento BookItem
            mItem=new BookItem(title,author,publication_date,description,url_image);
        }
    }


    /**
     * Retorna una vista con el contenido Dummy en el layout book_detail
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View con el contenido dummy
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_detail, container, false);

        // Mostramos el contenido Dummy
        if (mItem != null) {
            //Obtenemos cada elemento del layout que contendrá datos
            ((TextView) rootView.findViewById(R.id.author)).setText(mItem.getAuthor());
            ((TextView) rootView.findViewById(R.id.publish_date)).setText(mItem.getPublication_date());
            ((TextView) rootView.findViewById(R.id.description)).setText(mItem.getDescription());
            //Obtenemos elcontenedor de la imagen imagen
           ImageView imagen=rootView.findViewById(R.id.portada);

            //Asignamos la url de la imagenal contenedor de la imagen utilizando la librería Picasso
            Picasso.with(this.getContext()).load(mItem.getUrl_image()).into(imagen);



            /*
            *ANTIGUO CÓDIGO, SE CONSERVA POR RAZONES DE APRENDIZAJE
                    *
                    //obtenemos la url de la imagen, en funcion del identificador de cada dummyitem
                    int img = getResources().getIdentifier("@drawable/juego_ender"+ mItem.getIdentificador(), "drawable",getContext().getPackageName());


                    //Asignamos al contenedor de la imagen la imagen del archivo directorio drawable
                    imagen.setImageResource(img);

                    //imagen.setImageResource(R.drawable.juego_ender); Sería el método para añadir directamente una imagen, sin parametrizar
            */




        }
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getTitle());
        }
        return rootView;
    }
}
