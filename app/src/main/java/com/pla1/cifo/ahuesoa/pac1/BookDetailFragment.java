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

import com.pla1.cifo.ahuesoa.pac1.dummy.DummyContent;
import com.pla1.cifo.ahuesoa.pac1.model.BookContent;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pla1.cifo.ahuesoa.pac1.model.BookItem;


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

            //Asignamos el id por defecto del primer libro añadido en BookContent
            int id=0;
            try {
                //Obtenemos los argumentos pasados al fragmento desde otra actividad, el id del libro
                 id = Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
                //Obtenemos el libro
                mItem=BookContent.BOOKS_MAP.get(Integer.toString(id));
            }
            catch (RuntimeException e){
                e.printStackTrace();
                //Asignamos el primer libro del BookContent
                try {
                    mItem = BookContent.getBooks().get(0);
                }catch (RuntimeException e2){
                    e2.printStackTrace();
                    mItem=null;

                }
            }


             //Si es nulo tratamos de obtenerlo en el DummyContent
             if (mItem==null){
                 mItem=DummyContent.ITEM_MAP.get(Integer.toString(id));
             }
            
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

        // Mostramos el contenido del fragmento
        if (mItem != null) {
            //Obtenemos cada elemento del layout que contendrá datos
            ((TextView) rootView.findViewById(R.id.author)).setText(mItem.getAuthor());
            ((TextView) rootView.findViewById(R.id.publish_date)).setText(mItem.getPublication_date());
            ((TextView) rootView.findViewById(R.id.description)).setText(mItem.getDescription());
            //Obtenemos elcontenedor de la imagen imagen
           ImageView imagen=rootView.findViewById(R.id.portada);

            //Asignamos la url de la imagen al contenedor de la misma utilizando la librería Picasso
            Picasso.with(this.getContext()).load(mItem.getUrl_image()).into(imagen);

        }

        else {
            try {
                //Hacemos que sea el primer libro del BookContent
                mItem = BookContent.getBooks().get(0);
            }
            catch (RuntimeException e){
                e.printStackTrace();
            }
        }

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getTitle());
        }
        return rootView;
    }
}
