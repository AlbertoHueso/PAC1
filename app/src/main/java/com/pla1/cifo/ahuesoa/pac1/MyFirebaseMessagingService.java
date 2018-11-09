package com.pla1.cifo.ahuesoa.pac1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Servicio de mensajes remotos de Firebase
 *
 * Created by User on 19/06/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL1 = "CHANNEL1";

    /**
     * Método llamado cuando se recibe un mensaje remoto
     *
     * @param remoteMessage Mensaje recibido de Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
// Mostrar una notificación al recibir un mensaje de Firebase
        sendNotification(remoteMessage.getNotification().getBody());
    }

    /**
     * Crea y muestra una notificación al recibir un mensaje de Firebase
     *
     * @param messageBody Texto a mostrar en la notificación
     */
    private void sendNotification(String messageBody) {


// Intent que se mostrará al pulsar en la acción de la notificación

        Intent intentDelete = new Intent(this, BookListActivity.class);
        intentDelete.setAction(Intent.ACTION_DELETE);
        PendingIntent deleteIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentDelete, 0);
        Intent intentView = new Intent(this, BookDetailActivity.class);
        intentView.setAction(Intent.ACTION_VIEW);
        PendingIntent detailIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentView, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL1)
                        

                        .setContentTitle("Action required")
                        .setContentText(messageBody)//Recuperamos el texto enviado en a notificacion
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Now you can delete one book from Local Database " +
                                "\nor you can show a book added to Local Database"))

                        //El icono lo ponemos como 0 para que no se despliegue, de todos modos, desde
                        //Nugat la mayoría de dispositivos no muestran el icono
                        .addAction(new NotificationCompat.Action(0,"Delete last book", deleteIntent))
                        .addAction(new NotificationCompat.Action(0, "Display detail last book", detailIntent))
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