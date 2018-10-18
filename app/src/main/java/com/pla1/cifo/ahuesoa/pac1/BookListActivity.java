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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pla1.cifo.ahuesoa.pac1.dummy.DummyContent;

import java.util.List;

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
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();  //Creamos una instancia FirebaseAuth para hacer la autentificación
    private boolean conexion=false; //Variable para guardar si la conexión se ha realizado o no.

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);



        //Tratamos de conectar

        String email="who1@car.es";
        String password="whoreallycares";

        connection(email, password);





        //Conexión a la base de datos y creación de la referencia 
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("books");

        // Leemos de la base de datos
                    //Abrimos escuchador de eventos
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Bucle que recorre la base de datos y muestra los datos de cada libro
                           for(DataSnapshot ds: dataSnapshot.getChildren()){
                               Log.d("titulo", ds.child("title").toString());
                               Log.d("autor", ds.child("author").toString());
                               Log.d("description", ds.child("description").toString());
                               Log.d("date", ds.child("publication_date").toString());
                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.e("lecturaError", "Failed to read value.", error.toException());
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

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final BookListActivity mParentActivity;
        private final List<DummyContent.DummyBook> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyBook item = (DummyContent.DummyBook) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(BookDetailFragment.ARG_ITEM_ID, item.identificador);
                    BookDetailFragment fragment = new BookDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, BookDetailActivity.class);
                    intent.putExtra(BookDetailFragment.ARG_ITEM_ID, item.identificador);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(BookListActivity parent,
                                      List<DummyContent.DummyBook> items,
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
            holder.mIdView.setText(mValues.get(position).titulo);
            //Mostramos el autor en el content
            holder.mContentView.setText(mValues.get(position).autor);

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
     * Método para realizar la conexión a Firebase con un email y password. Actualiza el valor booleano de conexion según se haya conseguido o no
     * @param email   email del usuario
     * @param password clave del usuario
     */
    private void connection(String email, String password){


        //Registro con correo y clave
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    conexion=true;//Actualizamos el valor de la conexion
                    Log.d("LoginSuccess", "signInWithEmail:success :"+mAuth.getCurrentUser().getUid());

                }

                else
                {
                    Log.e("failureLogin", "signInWithEmail:failure", task.getException());
                    Log.e("noSuccessfullogin","no success");
                    mAuth.signOut();//Desconectamos el usuario
                }


            }
        });

    }
}
