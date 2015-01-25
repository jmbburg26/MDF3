/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.MenuItem;

import java.io.IOException;

/**
 * Created by John on 1/16/2015.
 */
public class SongDetailActivity extends Activity implements MediaPlayer.OnPreparedListener, ServiceConnection {
    public static final int STANDARD_NOTIFICATION = 0x01001;
    public static final int EXPANDED_NOTIFICATION = 0x01002;
    private static final int REQUEST_NOTIFY_LAUNCH = 0x02001;
    private static final String SAVE_POSITION = "SongDetailActivity.SAVE_POSITION";

    MediaPlayer mPlayer;
    boolean mActivityResumed;
    boolean mPrepared;
    int mAudioPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            SongDetailFragment fragment = new SongDetailFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit();
        }

        startService();

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
        builder.setSmallIcon(R.drawable.rawlings_baseball);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.spalding_basketball));
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

    //  Returns to the list activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mPlayer == null) {

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);

            try {
                mPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/raw/eye_of_the_tiger"));
            } catch(IOException e) {
                e.printStackTrace();

                mPlayer.release();
                mPlayer = null;
            }
        }

        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
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

        unbindService(this);
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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        AudioService.AudioServiceBinder binder = (AudioService.AudioServiceBinder)service;
        AudioService myService = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
