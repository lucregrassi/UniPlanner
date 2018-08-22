package com.lucreziagrassi.androidapp.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.splash.SplashActivity;

import java.util.List;

public class FutureExamBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Notification", "onReceive()");

        DatabaseManager.initializeDatabase(context);
        List<FutureExam> esamiFuturi = null;

        if(DatabaseManager.getDatabase() != null)
            esamiFuturi = DatabaseManager.getDatabase().getFutureExamDao().getAll();

        if(esamiFuturi != null && esamiFuturi.size() > 0)
        {
            FutureExam prossimoEsame = esamiFuturi.get(0);

            Long delta = prossimoEsame.getDate() - System.currentTimeMillis();
            Integer deltaDays = (int)Math.ceil(delta / (24.0 * 60 * 60 * 1000));

            Log.d("Notification", "" + deltaDays);

            if(deltaDays == 7 || deltaDays == 3 || deltaDays == 1)
                showNotification(context, prossimoEsame, deltaDays);
        }
    }

    public void showNotification(Context context, FutureExam exam, Integer deltaDays) {
        String giorn = deltaDays != 1 ? "giorni" : "giorno";

        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_credits)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app_icon))
                .setContentTitle("Prossimo esame")
                .setContentText(exam.getSubject() + " fra " + deltaDays + " " + giorn);
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}