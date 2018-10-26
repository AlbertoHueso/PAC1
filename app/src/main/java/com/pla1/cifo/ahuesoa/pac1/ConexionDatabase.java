package com.pla1.cifo.ahuesoa.pac1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.BookContent;
import model.BookItem;

/**
 * Clase que realiza la conexión a la base de datos de Firebase
 * Si es cancelado carga en la variable books los libros de la base de datos local
 * Si lo consigue, carga en la variable books los libros de la base de datos en Firebase
 * Extiende Thread para que sea un hilo propio y así poder retrasarlo independientemente del hilo de la autentificación
 */
public class ConexionDatabase extends Thread{

    /**
     * Base de datos de Firebase
     */
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * Referencia de base de datos, con el path books, es decir, la ruta desde la base de datos hasta los diferentes libros
     */
    private DatabaseReference myRef = database.getReference("books");

    /**
     * El contexto en el que se ejecuta
     */
    private Context context;

    /**
     * La actividad en la que se ejecuta
     */
    private BookListActivity activity;

    /**
     * Los libros en los que se va a guardar los datos
     */
    private BookContent books;

    /**
     * Constructor
     * @param context
     * @param activity
     * @param books
     */
    public ConexionDatabase(Context context,BookListActivity activity,BookContent books) {
        this.context=context;
        this.activity=activity;
        this.books=books;
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
                    activity.loadItemList(books);

                    //Mensaje informativo
                    Toast toast = Toast.makeText(context, "CONNECTED TO EXTERNAL DATABASE\nREADING FROM FIREBASE DATABASE", Toast.LENGTH_LONG);
                    toast.show();

                    //Actualizamos los identificadores, ya que desde Firebase vienen todos a 0
                    activity.updateIdentificatorsAndSaveNewItemsLocally(books);

                    //Hay algún problema
                } catch (Exception e) {
                    //Cargamos datosLocales
                    activity.showLocalData();
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }


            //Conexión a la base de datos fallida
            @Override
            public void onCancelled(DatabaseError error) {
                // Error al leer el valor
                Log.e("lecturaError", "Failed to read value.", error.toException());
                //Cargamos datosLocales
                activity.showLocalData();

            }
        });
    }


}