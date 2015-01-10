package com.bbgatestudios.mdf3app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 1/9/2015.
 */
public class SongAdapter extends BaseAdapter{

    private static final long ID_CONSTANT = 0x01000000;

    Context mContext;
    ArrayList<Song> mSongs;

    public SongAdapter(Context context, ArrayList<Song> contacts){
        mSongs = contacts;
        mContext = context;
    }

    @Override
    public int getCount(){ return mSongs.size(); }

    @Override
    public Song getItem(int position){ return mSongs.get(position); }

    @Override
    public long getItemId(int position){ return ID_CONSTANT + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_list_item, parent, false);
        }

        Song song = getItem(position);
        TextView contactNameView = (TextView) convertView.findViewById(R.id.contactName);
        contactNameView.setText(song.getArtist());

        return convertView;
    }
}
