//////John Brandenburg////
//////MDF3 1501///////////
package com.bbgatestudios.mdf3project;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AudioService extends Service {

    private static final int FOREGROUND_NOTIFICATION = 0x01001;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_announcement_grey600_48dp);
        builder.setContentTitle("Playing Music");
        builder.setContentText("Blah Blah Song Playing");
        builder.setAutoCancel(false);
        builder.setOngoing(true);

        startForeground(FOREGROUND_NOTIFICATION, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}