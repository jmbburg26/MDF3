package com.bbgatestudios.mdf31501;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 1/16/2015.
 */
public class SongData {
    private List<Song> songs = new ArrayList<Song>();
    public List<Song> getSongs() {
        return songs;
    }

    public SongData() {
        songs.add(new Song("John Brandenburg", "He Saw It All", "/raw/he_saw_it_all" ));
        songs.add(new Song("John Brandenburg", "Eye of the Tiger", "/raw/eye_of_the_tiger" ));
        songs.add(new Song("John Brandenburg", "He Saw It All", "/raw/he_saw_it_all" ));
    }
}
