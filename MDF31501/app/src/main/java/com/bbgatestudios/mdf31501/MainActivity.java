/////John Brandenburg/////MDF 1501/////

package com.bbgatestudios.mdf31501;


import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements SongListFragment.Callbacks{


    public static final String SONG_BUNDLE = "SONG_BUNDLE";
    private static final int REQUEST_CODE = 1001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

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
    public void onItemSelected(Song song) {
        Bundle b = song.toBundle();
        Intent intent = new Intent(this, SongDetailActivity.class);
        intent.putExtra(SONG_BUNDLE, b);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
