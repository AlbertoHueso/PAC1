package com.pla1.cifo.ahuesoa.pac1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.orm.SugarDb;
import com.pla1.cifo.ahuesoa.pac1.dummy.DummyContent;
import com.pla1.cifo.ahuesoa.pac1.dummy.Funciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.BookContent;
import model.BookItem;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /**
     * Variable FirebaseAuth para hacer la autenticacion
     */
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    /*
     * Guarda si se ha producido la conexión o no
     */
    private boolean conexion=false;
    /**
     * Variable que guarda los libros cargados en memoria para mostrar
     */
    BookContent books=null;


    @Override
    public void onStart() {
        super.onStart();

      long a=BookItem.count(BookItem.class);
      Log.d("contar",Long.toString(a));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        SugarDb db = new SugarDb(this);
        db.onCreate(db.getDB());

        //Tratamos de autorizar con un email y passwords

        String email="who1@car.es";
        String password="whoreallycares";
        MyAuthoritation co=new MyAuthoritation(email,password,this);


        //Ponemos dos conexiones porque si no,en ocasiones, falla al reconectar después de una ocasión fallida
        co.connection();
        co.connection();






        //Conexión a la base de datos y creación de la referencia. books es el nodo de los libros en la base de datos, de ahí el path
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("books");

        // Leemos de la base de datos
                    //Abrimos escuchador de eventos

                        Log.d("logindDespierto","despierto");
                        myRef.addValueEventListener(new ValueEventListener() {


                            //Caso conexión a la base de datos exitosa
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                            /*
                                            *SE CONSERVA ESTE FRAGMENTO CON PROPÓSITOS DE APRENDIZAJE***
                                            *
                                            //Bucle que recorre la base de datos, muestra los datos de cada libro y  guarda cada unoen objetos BookItem
                                           for(DataSnapshot ds: dataSnapshot.getChildren()){
                                               Log.d("titulo", ds.child("title").toString());
                                               Log.d("autor", ds.child("author").toString());
                                               Log.d("description", ds.child("description").toString());
                                               Log.d("date", ds.child("publication_date").toString());
                                               Log.d("key",ds.getKey().toString());

                                               try {
                                                   //Obtenemos un libro
                                                   BookItem book = ds.getValue(BookItem.class);
                                               }
                                               catch (EnumConstantNotPresentException e){
                                                   System.err.println(e.getMessage());
                                               }
                                           }
                                            */


                                //Cargamos los libros leídos de la base de datos de Firebase en books
                                try {

                                    GenericTypeIndicator<BookContent<BookItem>> t = new GenericTypeIndicator<BookContent<BookItem>>() {
                                    };

                                    //Cargamos los libros del snapshot en un ArrayList
                                    //No podemos hacerlo en un BookContent directamente porque el casting no funciona
                                    ArrayList<BookItem> booksArray = dataSnapshot.getValue(t);



                                    //Guardamos el booksArray en el BookContent
                                    books = Funciones.toBookContent(booksArray);


                                    //Cargamos los libros en la vista
                                    loadItemList(books);

                                    //Actualizamos sus identificadores
                                    updateIdentificators(books);

                                    //Actualizamos la conexión
                                    conexion=true;

                                } catch (Exception e) {
                                    //Cargamos la lista Dummy
                                    loadItemList(DummyContent.ITEMS);
                                    Log.d("dummy","dummy2");
                                    System.err.println(e.getMessage());
                                    e.printStackTrace();
                                }
                            }


                            //Conexión a la base de datos fallida
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Error al leer el valor
                                Log.e("lecturaError", "Failed to read value.", error.toException());
                                //Cargamos la lista Dummy
                                loadItemList(DummyContent.ITEMS);
                                Log.d("loginDummy","dummy3");
                            }
                        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<BookItem> items){

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, items, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final BookListActivity mParentActivity;
        private final List<BookItem> mValues;

        private final boolean mTwoPane;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                BookItem  item= (BookItem) view.getTag();

                if (mTwoPane) {


                    //Añadimos los argumentos que se envían al nuevo panel
                    Bundle arguments = new Bundle();
                    //Pasamos la POSICION como item_ID
                    arguments.putString(BookDetailFragment.ARG_ITEM_ID, Integer.toString(item.getIdentificador()));
                    arguments.putString(BookDetailFragment.ARG_ITEM_TITLE, item.getTitle());
                    arguments.putString(BookDetailFragment.ARG_ITEM_AUTHOR, item.getAuthor());
                    arguments.putString(BookDetailFragment.ARG_ITEM_DESCRIPTION, item.getDescription());
                    arguments.putString(BookDetailFragment.ARG_ITEM_URL_IMAGE, item.getUrl_image());
                    arguments.putString(BookDetailFragment.ARG_ITEM_PUBLICATION_DATE, item.getPublication_date());

                    BookDetailFragment fragment = new BookDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, BookDetailActivity.class);

                    //Añadimos los argumentos que se envían a la nueva actividad
                    intent.putExtra(BookDetailFragment.ARG_ITEM_ID, Integer.toString(item.getIdentificador()));
                    intent.putExtra(BookDetailFragment.ARG_ITEM_TITLE, item.getTitle());
                    intent.putExtra(BookDetailFragment.ARG_ITEM_AUTHOR, item.getAuthor());
                    intent.putExtra(BookDetailFragment.ARG_ITEM_DESCRIPTION, item.getDescription());
                    intent.putExtra(BookDetailFragment.ARG_ITEM_URL_IMAGE, item.getUrl_image());
                    intent.putExtra(BookDetailFragment.ARG_ITEM_PUBLICATION_DATE, item.getPublication_date());

                    //Se inicia la nueva actividad
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(BookListActivity parent,
                                      List<BookItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }




        /**
         * Método que al crear el ViewHolder asigna un layout book_list_content_odd si viewType es 0 y book_list_content_even en cualquier otro caso
         * @param parent
         * @param viewType
         * @return Viewholder con el layout asignado en función de su viewType
         * @see #getItemViewType(int)
         */
        @Override

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {

                case 0:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.book_list_content_odd, parent, false);
                    break;


                default:
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.book_list_content_even, parent, false);


            }

            ViewHolder holder = new ViewHolder(view);


            return holder;
        }



        /**
         * Sobreescribimos el método getItemType para distinguir filas pares e impares
         * @param position
         * @return 0 si la posicion es par
         */
        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            //Mostramos el título en el id
            holder.mIdView.setText(mValues.get(position).getTitle());
            //Mostramos el autor en el content
            holder.mContentView.setText(mValues.get(position).getAuthor());


            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    /**
     * Método que carga una lista de libros en la vista de la página
     * @param books
     */
    private void loadItemList(List<BookItem> books){
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView,books);
    }


    /**
     * Método que a cada libro de una lista le asigna como identificador su posición en la lista
     * @param books
     */
    private void updateIdentificators(List<BookItem> books){

        int identificador=0;
        Iterator<BookItem> it=books.iterator();
        while (it.hasNext()){
            BookItem book= it.next();
            book.setIdentificador(identificador);
            book.setId((long)identificador);
            identificador++;

            if (BookItem.count(BookItem.class)==-1){
                BookItem.save(book);

            }
        }

    }


}
