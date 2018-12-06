package com.pla1.cifo.ahuesoa.pac1;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pla1.cifo.ahuesoa.pac1.model.BookContent;
import com.pla1.cifo.ahuesoa.pac1.model.BookItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWebView);
        setSupportActionBar(toolbar);
        String id=getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID);
        BookItem book=BookContent.BOOKS_MAP.get(id);
        getSupportActionBar().setTitle(book.getTitle());

        WebView myWebView = (WebView) findViewById(R.id.id_webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient());
        // Get the Android assets folder path
        String folderPath = "file:android_asset/";

        // Get the HTML file name
        String fileName = "form.html";

        // Get the exact file location
        String file = folderPath + fileName;

         /*
                    loadUrl(String url)
                        Loads the given URL.
                 */

        // Render the HTML file on WebView
        myWebView.loadUrl(file);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });



    }

}
