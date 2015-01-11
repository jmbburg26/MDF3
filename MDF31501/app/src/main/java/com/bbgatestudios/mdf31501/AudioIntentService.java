/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by John on 1/9/2015.
 */
public class AudioIntentService extends IntentService {

    public AudioIntentService(){
        super("AudioIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
