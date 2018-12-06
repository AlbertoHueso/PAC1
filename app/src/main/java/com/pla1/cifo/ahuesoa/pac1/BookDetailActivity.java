package com.pla1.cifo.ahuesoa.pac1;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BookListActivity}.
 */
public class BookDetailActivity extends AppCompatActivity {

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        id=getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
                //Añadimos los argumentos que se envían a la nueva actividad
                intent.putExtra(BookDetailFragment.ARG_ITEM_ID, id);

                //Se inicia la nueva actividad
                getApplicationContext().startActivity(intent);


            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            //Se añaden todos los argumentos que se quieren enviar al fragmento
            Bundle arguments = new Bundle();
            arguments.putString(BookDetailFragment.ARG_ITEM_ID,
                    id);
            /*
            arguments.putString(BookDetailFragment.ARG_ITEM_TITLE,
                    getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_TITLE));
            arguments.putString(BookDetailFragment.ARG_ITEM_AUTHOR,
                    getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_AUTHOR));
            arguments.putString(BookDetailFragment.ARG_ITEM_URL_IMAGE,
                    getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_URL_IMAGE));
            arguments.putString(BookDetailFragment.ARG_ITEM_PUBLICATION_DATE,
                    getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_PUBLICATION_DATE));
            arguments.putString(BookDetailFragment.ARG_ITEM_DESCRIPTION,
                    getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_DESCRIPTION));;
                    */
            BookDetailFragment fragment = new BookDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, BookListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
