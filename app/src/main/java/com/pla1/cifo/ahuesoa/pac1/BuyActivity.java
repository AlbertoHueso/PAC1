package com.pla1.cifo.ahuesoa.pac1;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pla1.cifo.ahuesoa.pac1.model.BookContent;
import com.pla1.cifo.ahuesoa.pac1.model.BookItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyActivity extends AppCompatActivity {

    private BookItem book;//Libro que se va a comprar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWebView);
        setSupportActionBar(toolbar);

        //Recuperamos los argumentos enviados desde la actividad origen
        String id=getIntent().getStringExtra(BookDetailFragment.ARG_ITEM_ID);
        //Recuperamos el libro indicado por el id
         book=BookContent.BOOKS_MAP.get(id);
        //Mostramos en la barra el título del libro
        getSupportActionBar().setTitle(book.getTitle());


        //Creamos una instancia de Webview
        WebView myWebView = (WebView) findViewById(R.id.id_webView);
        WebSettings webSettings = myWebView.getSettings();//Obtenemos los settings por defecto
        webSettings.setJavaScriptEnabled(true);//Activamos javascript

        //Abrimos el navegador
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    Log.d("thisurl1", getName(url));
                Log.d("thisurl2", getNumber(url));
                Log.d("thisurl3", getDate(url));
                Log.d("thisurl","aquí");

                String name=getName(url);
                String number=getNumber(url);
                String date=getDate(url);

                Toast toast;

                //Si todos los campos están rellenados se muestra un mensaje y se vuelve a la actividad anterior
                //Esta validación de campos es simplemente de prueba, en realidad habría que hacer una validación en el html con javascript y comprobar que los campos fueran correctos
                if (!name.equals("")&&!number.equals("")&&!date.equals("")){

                    toast = Toast.makeText(getApplicationContext(), "Congratulations!\nYou have bought the book:\n"+book.getTitle(), Toast.LENGTH_LONG);
                    toast.show();
                   //Cerramos la actividad y volvemos a la anterior
                    finish();
                }else {

                    toast = Toast.makeText(getApplicationContext(), "You need to fullfill alll fields in order to buy the book", Toast.LENGTH_LONG);
                    toast.show();
                }




                return false;
            }
        });
        // Obtenemos la ruta del formulario
        String folderPath = "file:android_asset/";

        // Obtenemos el archivo del formulario
        String fileName = "form.html";

        // Obtenemos la ruta completa del archivo
        String file = folderPath + fileName;


        // Cargamos el formulario en el webview
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

    /**
     * Obtenemos el valor del parametro name de la url
     * @param url
     * @return String
     */
    private  String getName(String url){
         String name= stringInBetween(url,"name=","&");
        return name;
    }

    /**
     * Obtenemos el valor del parámetro num de la url
     * @param url
     * @return String
     */
    private  String getNumber(String url){
        String number= stringInBetween(url,"num=","&");
        return number;
    }

    /**
     * Obtenemos el valor del parámetro date de la url
     * @param url
     * @return String
     */
    private  String getDate(String url){
        String date= stringInBetween(url,"date=","&");
        return date;
    }


    /**
     * Método que devuelve el texto que se encuentra en la cadena text entre los patrones pattern1 y pattern2
     * @param text
     * @param pattern1
     * @param pattern2
     * @return String
     */
    private  String stringInBetween(String text, String pattern1, String pattern2){
        String cadena="";
        Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
        Matcher m = p.matcher(text);
        while (m.find()) {
            //Si se ha encontrado un texto entre los dos patrones se asigna a cadena
            cadena=m.group(1);
        }
        return cadena;

    }
}
