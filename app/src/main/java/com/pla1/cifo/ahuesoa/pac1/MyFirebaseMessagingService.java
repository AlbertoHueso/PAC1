package com.pla1.cifo.ahuesoa.pac1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pla1.cifo.ahuesoa.pac1.model.BookContent;

import java.util.Map;


/**
 * Servicio de mensajes remotos de Firebase
 *
 * Created by User on 19/06/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL1 = "CHANNEL1";
    private static final String KEY_EXPECTED = "book_position";
    private int position;
    /**
     * Método llamado cuando se recibe un mensaje remoto
     *
     * @param remoteMessage Mensaje recibido de Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
// Mostrar una notificación al recibir un mensaje de Firebase


        //Recuperamos el par clave valor enviado desde la notifiación
        try {
            //Recuperamos el mensaje enviado
            Map<String, String> a = remoteMessage.getData();
            //Recuperamos el valor con la clave que se espera en los mensajes, en este caso "book_position"
            position = Integer.parseInt(a.get(KEY_EXPECTED));


        }catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            //Se asigna una posición igual al tamaño del array con lo que nunca se podrá acceder a esa posición
            //No se mostrará la notificación
            position=BookContent.getBooks().size();
        }

        //La notificación solo se muestra si se obtiene un valor válido para position
        if (position>=0&&position<BookContent.getBooks().size()) {

            //Se muestra la notificación
            sendNotification(remoteMessage.getNotification().getBody());


        }

    }

    /**
     * Crea y muestra una notificación al recibir un mensaje de Firebase
     *
     * @param messageBody Texto a mostrar en la notificación
     */
    private void sendNotification(String messageBody) {


// Intent que se mostrará al pulsar en la acción de la notificación

        //Creamos un intent para abrir la actividad principal BookListActivity
        Intent intentDelete = new Intent(this, BookListActivity.class);
        intentDelete.setAction(Intent.ACTION_DELETE);
        Log.d("posicion2",Integer.toString(position));
        //Añadimos los datos que se pasan a la nueva actividad
        intentDelete.putExtra("position", position);
        PendingIntent deleteIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentDelete, 0);

        //Creamos un intent para abrir la actividad de BookDetailActivity
        Intent intentView = new Intent(this,BookListActivity.class);
        intentView.setAction(Intent.ACTION_VIEW);
        //Añadimos los datos que se pasan a la nueva actividad
        intentView.putExtra("position", position);
        PendingIntent detailIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentView, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL1)


                        .setContentTitle("Action required")
                        .setContentText(messageBody)//Recuperamos el texto enviado en a notificacion
                        //Asignamos el texto del botón
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Now you can delete one book from Local Database " +
                                "\nor you can show a book added to Local Database"))
                        .setLights(0xFFff0000, 1000, 1000)
                        //El icono del botón lo ponemos como 0 para que no se despliegue, de todos modos, desde
                        //Nugat la mayoría de dispositivos no muestran el icono del botón
                        .addAction(new NotificationCompat.Action(0,"Delete book", deleteIntent))
                        .addAction(new NotificationCompat.Action(0, "Display detail book", detailIntent))
                        .setAutoCancel(true) //Borrar notificación después de pulsar sobre ella
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Seleccionamos el icono personalizado de la notificación
        //Obtenido de https://romannurik.github.io/AndroidAssetStudio/icons-notification.html
        mBuilder.setSmallIcon(R.drawable.ic_stat_chrome_reader_mode);

// Mostrar la notificación
         //Creamos la notificación
         Notification notif=mBuilder.build();
         //Hacemos que se ilumine el led
        notif.ledARGB = 0xFFff0000;
        notif.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_ONLY_ALERT_ONCE;
        notif.ledOnMS = 1000;
        notif.ledOffMS = 1000;

        //Desplegamos la notificación
        mNotificationManager.notify(0, notif);
        //Hacemos que vibre
         vibrar();

    }

    /**
     * Método que hace que el móvil vibre
     */
    private void vibrar(){
        Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);

        //Creamos el patrón de vibración
        long[] pattern={2000,1000};//sleep 2s y vibra un s

        //Ejecutamos la vibración
        vibrator.vibrate(pattern, -1); //-1 indica sin repetición
    }


}