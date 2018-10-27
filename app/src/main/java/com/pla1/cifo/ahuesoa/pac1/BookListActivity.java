package com.pla1.cifo.ahuesoa.pac1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.pla1.cifo.ahuesoa.pac1.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.BookContent;
import model.BookItem;

/**
 * Actividad que intenta conectar con la base de datos de Firebase,
 * si lo consigue muestra los libros, si no, muestra los libros en la memoria local SugarOrm
 * Los libros se pueden clickar y abrirán la actividad BookDetailActivity
 * @see ConexionDatabase
 * @see MyAuthoritation
 * @see BookDetailFragment
 * @see BookDetailFragment
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

    /**
     * Variable que guarda los libros en local, si existen
     */
    BookContent bookLocals;




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //Actualizamos bookLocals con los libros de la base de datos local
        bookLocals=Funciones.toBookContent(BookItem.listAll(BookItem.class));


        //Comprobamos si hay conexión a la red
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //Si está conectado se trata de autorizar y leer la base de datos
        if(isConnected) {


            //Tratamos de autorizar con un email y passwords
            String email = "who1@car.es";
            String password = "whoreallycares";

            //Autorizamos
            MyAuthoritation co = new MyAuthoritation(email, password, this);
            co.start();

            //Conectamos a la base de datos
            ConexionDatabase conexionDatabase=new ConexionDatabase();
            conexionDatabase.start();
        }


        //No conectado a la red, se muestran datos locales
        else {
            showLocalData();

        }



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

                //Caso dos paneles
                if (mTwoPane) {


                    //Añadimos los argumentos que se envían al panel lateral
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

                    //Caso un panel
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
    public void loadItemList(List<BookItem> books){
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView,books);
    }


    /**
     * Método que a cada libro de una lista le asigna un identificador a partir de una funcion Hash
     *
     * @param books
     * @see BookItem#getIdHash() ;
     */
    public void updateIdentificators(List<BookItem> books){


        Iterator<BookItem> it=books.iterator();

        //Recorremos la lista de books
        while (it.hasNext()){
            BookItem book= it.next();
            //Se actualizan los identificadores
            book.setIdentificador(book.getIdHash());
            book.setId((long)book.getIdentificador());


        }

    }

    /**
     * Método si el libro no está en bookLocals lo añade a este y a la base de datos local     *
     * @param books
     */
    public void SaveNewItemsLocally(List<BookItem> books){


        Iterator<BookItem> it=books.iterator();

        //Recorremos la lista de books
        while (it.hasNext()){
            BookItem book= it.next();

            //Si el libro no está en bookLocals se añade y se guarda en la base de datos local
            if (!bookLocals.exists(book)){

                BookItem.save(book);
                bookLocals.add(book);

            }
        }

    }

    /**
     * Método que muestra los datos locales.Si no hay datos locales muestra el contenido Dummy
     */
    public  void showLocalData(){

        //Recuperamos la view de la actividad
        View view=findViewById(android.R.id.content);
        //Cambiamos el color a los dos elementos del fondo, de esta manera se hace más evidente que estamos en localdata
        view.findViewById(R.id.frameLayout).setBackgroundColor(Color.LTGRAY);
        view.findViewById(R.id.item_list).setBackgroundColor(Color.LTGRAY);

        //No hay datos locales, bookLocals es nulo
        if (bookLocals==null){
            loadItemList(DummyContent.ITEMS);
            Toast toast = Toast.makeText(getApplicationContext(), "NO CONNEXION TO EXTERNAL DATABASE\nNO LOCAL DATA\nPLEASE TRY AGAIN", Toast.LENGTH_LONG);
            toast.show();

        }else {
          //Hay datos locales y hay libros cargados en bookLocals, se muestran
          if(bookLocals.size()>0) {
              Toast toast = Toast.makeText(getApplicationContext(), "NO CONNEXION TO EXTERNAL DATABASE\nREADING LOCAL DATA", Toast.LENGTH_LONG);
              toast.show();
              loadItemList(bookLocals);

           //No hay libros cargados en bookLocals, se muestra el contenido Dummy
          }else {
              loadItemList(DummyContent.ITEMS);
              Toast toast = Toast.makeText(getApplicationContext(), "NO CONNEXION TO EXTERNAL DATABASE\nNO LOCAL DATA\nPLEASE TRY AGAIN", Toast.LENGTH_LONG);
              toast.show();
          }
        }
    }

    /**
     * Clase que realiza la conexión a la base de datos de Firebase
     * Si es cancelado carga en la variable books los libros de la base de datos local
     * Si lo consigue, carga en la variable books los libros de la base de datos en Firebase
     * Extiende Thread para que sea un hilo propio y así poder retrasarlo independientemente del hilo de la autentificación
     */
    private class ConexionDatabase extends Thread{

        /**
         * Base de datos de Firebase
         */
        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        /**
         * Referencia de base de datos, con el path books, es decir, la ruta desde la base de datos hasta los diferentes libros
         */
        private DatabaseReference myRef = database.getReference("books");



        /**
         * Constructor
         *
         */
        public ConexionDatabase() {

        }

        @Override
        /**
         * En primer lugar ejecuta una espera de 1500ms
         * Después abre un escuchador para eventos de cambio de datos
         * Si se reciben nuevos datos los carga en books
         * Si hay algún problema carga desde la base de datos local
         */
        public void run() {
            try {
                Thread.sleep(1500);
                Log.d("loginEspera","espera");
            }catch (InterruptedException e){
                System.err.print(e.getMessage());
                e.printStackTrace();
            }

            // Leemos de la base de datos
            //Abrimos escuchador de eventos
            myRef.addValueEventListener(new ValueEventListener() {


                //Caso conexión a la base de datos exitosa
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    //Cargamos los libros leídos de la base de datos de Firebase en books
                    try {

                        GenericTypeIndicator<BookContent<BookItem>> t = new GenericTypeIndicator<BookContent<BookItem>>() {
                        };

                        //Cargamos los libros del snapshot en un ArrayList
                        //No podemos hacerlo en un BookContent directamente porque el casting no funciona
                        ArrayList<BookItem> booksArray = dataSnapshot.getValue(t);


                        //Guardamos el booksArray en el BookContent
                        books = Funciones.toBookContent(booksArray);


                        //Cargamos los libros en la actividad
                        loadItemList(books);

                        //Mensaje informativo
                        Toast toast = Toast.makeText(getApplicationContext(), "CONNECTED TO EXTERNAL DATABASE\nREADING FROM FIREBASE DATABASE", Toast.LENGTH_LONG);
                        toast.show();

                        //Actualizamos los identificadores, ya que desde Firebase vienen todos a 0
                        updateIdentificators(books);

                        //Guardamos los nuevos libros en la base de datos local
                        SaveNewItemsLocally(books);

                        //Retiramos el escuchador para evitar interferencias al rotar
                        myRef.removeEventListener(this);

                        //Hay algún problema
                    } catch (Exception e) {
                        //Cargamos datosLocales
                        showLocalData();
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                    }
                }


                //Conexión a la base de datos fallida
                @Override
                public void onCancelled(DatabaseError error) {
                    // Error al leer el valor
                    Log.e("readError", "Failed to read value.", error.toException());
                    //Cargamos datosLocales
                    showLocalData();

                    //Retiramos el escuchador para evitar interferencias al rotar
                    myRef.removeEventListener(this);

                }
            });
        }


    }
}
