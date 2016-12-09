package lightsensor.sounlam.com.grupo_soa.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import lightsensor.sounlam.com.grupo_soa.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by raulvillca on 8/12/16.
 */

public class MainUtil {
    private static void showNotification(AppCompatActivity activity, String title, String message) {
        PendingIntent pi = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()), 0);
        Resources r = activity.getResources();
        Notification notification = new NotificationCompat.Builder(activity)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_tap_and_play_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private boolean sendNotification(AppCompatActivity activity, List<Integer> arrayNotification, int max) {
        int cant = 0;

        for (int i = 0; i < arrayNotification.size(); i++) {
            if (arrayNotification.get(i) > max) {
                cant++;
            }
        }

        if (cant == arrayNotification.size()) {
            arrayNotification.clear();
            showNotification(
                    activity,
                    activity.getResources().getString(R.string.title_notification),
                    activity.getResources().getString(R.string.message_notification)
            );
            return true;
        }
        return false;
    }

}
