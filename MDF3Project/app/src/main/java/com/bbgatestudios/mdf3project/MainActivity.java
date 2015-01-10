//////John Brandenburg////
//////MDF3 1501///////////

package com.bbgatestudios.mdf3project;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements MediaPlayer.OnPreparedListener{
    public static final int STANDARD_NOTIFICATION = 0x01001;
    public static final int EXPANDED_NOTIFICATION = 0x01002;
    private static final int REQUEST_NOTIFY_LAUNCH = 0x02001;
    private static final String SAVE_POSITION = "MainActivity.SAVE_POSITION";

    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button noti_button = (Button) findViewById(R.id.button_notify);
        noti_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotification();
            }
        });

        final Button start_service_button = (Button) findViewById(R.id.button_service_start);
        start_service_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startService();
            }
        });

        final Button stop_service_button = (Button) findViewById(R.id.button_service_stop);
        stop_service_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stopService();
            }
        });
    }

    public void startService(){
        Intent intent = new Intent(this, AudioService.class);
        startService(intent);
        mPlayer.start();
    };

    public void stopService(){
        Intent intent = new Intent(this, AudioService.class);
        stopService(intent);
        mPlayer.stop();
    };

    public void startNotification(){
        // Assuming we're in a Context such as an Activity or Service.
        NotificationManager mgr =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_announcement_white_48dp);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_launcher));
        builder.setContentTitle("Little Notification Title");
        builder.setContentText("Little Notification Message");
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText("This would be the place where I tell you about some cool stuff going on in the app");
        bigStyle.setBigContentTitle("Big Important Title");
        bigStyle.setSummaryText("Expanded Summary");
        builder.setStyle(bigStyle);
        mgr.notify(EXPANDED_NOTIFICATION, builder.build());

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, REQUEST_NOTIFY_LAUNCH, intent, 0);

        builder.setContentIntent(pIntent);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mPlayer == null) {
            // Easy way: mPlayer = MediaPlayer.create(this, R.raw.something_elated);
            // Easy way doesn't require a call to prepare or prepareAsync. Only works for resources.

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);

            try {
                mPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/raw/waterfromthewell"));
            } catch(IOException e) {
                e.printStackTrace();

                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mPlayer != null) {
            outState.putInt(SAVE_POSITION, mPlayer.getCurrentPosition());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mActivityResumed = true;
        if(mPlayer != null && !mPrepared) {
            mPlayer.prepareAsync();
        } else if(mPlayer != null && mPrepared) {
            mPlayer.seekTo(mAudioPosition);
            mPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityResumed = false;

        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPrepared = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPlayer != null) {
            mPlayer.release();
        }
    }

    //@Override
    public void onPrepared(MediaPlayer mp) {
        mPrepared = true;

        if(mPlayer != null && mActivityResumed) {
            mPlayer.seekTo(mAudioPosition);
        }
    }
}
