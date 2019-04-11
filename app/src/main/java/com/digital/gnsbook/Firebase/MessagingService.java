package com.digital.gnsbook.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.digital.gnsbook.Activity.ChatAcivity;
import com.digital.gnsbook.Activity.MainActivity;
import com.digital.gnsbook.Global;
import com.digital.gnsbook.GnsChat.Chat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.httpgnsbook.gnsbook.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Shaikh Aquib on 07-May-18.
 */

public class MessagingService extends FirebaseMessagingService {

   // DbHelper dbHelper = new DbHelper(this);
    String MyPREFERENCES = "MyPREFERENCES";
    int i = 0;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
        JSONObject jsonObject =new JSONObject(remoteMessage.getData());


            i++ ;
            Log.d("notification", String.valueOf(jsonObject));

            SharedPreferences prfs = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
            Global.notificationcount = prfs.getInt("notivalue",0);
            Global.notificationcount++;

            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("notivalue", Global.notificationcount);
            editor.apply();
            System.out.println(Global.notificationcount);
          //  dbHelper.addNotification(jsonObject.getString("Title"),jsonObject.getString("Message"),jsonObject.getString("Date"));

            if (jsonObject.has("sender_id")) {
                 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                 showNotification(jsonObject.getString("Message"), jsonObject.getString("Date"), jsonObject.getString("Title"),intent);
            }else {
                Intent intent = new Intent(getApplicationContext(), ChatAcivity.class);
                showNotification(jsonObject.getString("Message"), jsonObject.getString("Date"), jsonObject.getString("Title"),intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(String Message , String Date , String Title,Intent intent) {

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "GnsBook";
        String channelName = "GnsBook Notification";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), channelId)
                .setSmallIcon(R.drawable.gnsbooklogo)
                .setContentTitle(Title)
                .setContentText(Message)
                .setLights(Color.RED, 3000, 3000)
                .setSound(alarmSound);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(m, mBuilder.build());
    }
}
