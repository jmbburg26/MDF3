package com.bbgatestudios.mdf31501;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * Created by John on 1/16/2015.
 */
public class SongDetailFragment extends Fragment {
    Song song;

    //    Required no-args constructor
    public SongDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Load the layout
        View view = inflater.inflate(R.layout.song_detail_fragment, container, false);

        if (song != null) {

            //Display values and image
            TextView songName = (TextView) view.findViewById(R.id.artistName);
            songName.setText(song.getSongAuth());

            TextView songTitle = (TextView) view.findViewById(R.id.songName);
            songTitle.setText(song.getSongTitle());
        }

        return view;
    }
}
