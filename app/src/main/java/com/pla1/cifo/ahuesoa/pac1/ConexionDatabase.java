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

public class ConexionDatabase extends Thread{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("books");
    private Context context;
    private BookListActivity activity;
    private BookContent books;
    public ConexionDatabase(Context context,BookListActivity activity,BookContent books) {
        this.context=context;
        this.activity=activity;
        this.books=books;
    }

    @Override
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


                    //Cargamos los libros en la vista
                    activity.loadItemList(books);

                    Toast toast = Toast.makeText(context, "CONNECTED TO EXTERNAL DATABASE\nREADING FROM FIREBASE DATABASE", Toast.LENGTH_LONG);
                    toast.show();

                    //Actualizamos sus identificadores
                    activity.updateIdentificatorsAndSaveNewItemsLocally(books);



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