/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 1/11/2015.
 */
public class SongListFragment extends ListFragment{

    List<Song> songs = new SongData().getSongs();

    private Callbacks activity;
    public SongListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SongArrayAdapter adapter = new SongArrayAdapter(getActivity(), R.layout.song_list_item, songs);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    public interface Callbacks {
        public void onItemSelected(Song song);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Song song = songs.get(position);
        activity.onItemSelected(song);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }
}
