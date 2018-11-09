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

        Intent intent = new Intent(this, BookListActivity.class);
        intent.setAction("android.intent.action.DELETE");
        PendingIntent borrarIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        Intent intent2 = new Intent(this, BookListActivity.class);
        intent2.setAction("android.intent.action.RESEND");
        PendingIntent resendIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent2, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL1)

                        .setContentTitle("Mi primera notificación")
                        .setContentText("Hola mundo")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Estos son los detalles expandidos " +
                                "de la notificacion anterior, aquí se puede escribir más texto"))

                        .addAction(new NotificationCompat.Action(R.drawable.notification,"Borrar", borrarIntent))
                        .addAction(new NotificationCompat.Action(R.drawable.notification, "Reenviar", resendIntent));
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setSmallIcon(R.drawable.notification);
// Mostrar la notificación
        mNotificationManager.notify(0, mBuilder.build());
    }
}