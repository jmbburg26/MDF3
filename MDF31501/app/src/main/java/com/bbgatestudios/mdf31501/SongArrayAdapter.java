/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 1/11/2015.
 */
public class SongArrayAdapter extends ArrayAdapter<Song> {
    private static final long ID_CONSTANT = 0x01000000;

    private Context mContext;
    private List<Song> song;

    public SongArrayAdapter(Context context, int resource, List<Song> song){
        super(context, resource, song);

        this.song = song;
        this.mContext = context;
    }

    @Override
    public int getCount(){ return song.size(); }

    @Override
    public Song getItem(int position){ return song.get(position); }

    @Override
    public long getItemId(int position){ return ID_CONSTANT + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.song_list_item, parent, false);
        }

        Song song = getItem(position);
        TextView contactNameView = (TextView) convertView.findViewById(R.id.song_list_title);
        contactNameView.setText(song.getSongTitle());

        return convertView;
    }
}
