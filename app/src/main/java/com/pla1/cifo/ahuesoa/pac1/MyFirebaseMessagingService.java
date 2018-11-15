package com.pla1.cifo.ahuesoa.pac1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pla1.cifo.ahuesoa.pac1.model.BookContent;
import com.pla1.cifo.ahuesoa.pac1.model.BookItem;

import java.util.Map;
import java.util.Set;

/**
 * Servicio de mensajes remotos de Firebase
 *
 * Created by User on 19/06/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL1 = "CHANNEL1";

    private int position;
    /**
     * Método llamado cuando se recibe un mensaje remoto
     *
     * @param remoteMessage Mensaje recibido de Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
// Mostrar una notificación al recibir un mensaje de Firebase

        try {
            Map<String, String> a = remoteMessage.getData();
            Set<String> z = a.keySet();
            String w=a.get("position");
            position = Integer.parseInt(a.get("position"));
            Log.d("posicion1",a.get("position"));

        }catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            //Se asigna una posición igual al tamaño del array con lo que nunca se podrá acceder a esa posición
            position=BookContent.getBooks().size();
        }
        if (position>=0&&position<BookContent.getBooks().size())
        sendNotification(remoteMessage.getNotification().getBody());
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
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Now you can delete one book from Local Database " +
                                "\nor you can show a book added to Local Database"))


                        //El icono lo ponemos como 0 para que no se despliegue, de todos modos, desde
                        //Nugat la mayoría de dispositivos no muestran el icono
                        .addAction(new NotificationCompat.Action(0,"Delete book", deleteIntent))
                        .addAction(new NotificationCompat.Action(0, "Display detail book", detailIntent))
                        .setAutoCancel(true) //Borrar notificación después de pulsar sobre ella
                        .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Seleccionamos el icono
        mBuilder.setSmallIcon(R.drawable.notification);
// Mostrar la notificación
        mNotificationManager.notify(0, mBuilder.build());
    }
}