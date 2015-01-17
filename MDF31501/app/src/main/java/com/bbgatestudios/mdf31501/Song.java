/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

import android.os.Bundle;

/**
 * Created by John on 1/11/2015.
 */
public class Song {

    public static final String SONG_NAME = "songTitle";
    public static final String SONG_ARTIST= "songAuth";
    public static final String SONG_RESOURCE = "songResource";

    private String songAuth;
    private String songTitle;
    private String songResource;

    public Song(){
        songAuth = "";
        songTitle = "";
        songResource = "";
    }

    public Song(String author, String title, String sResource){
        songAuth = author;
        songTitle = title;
        songResource = sResource;
    }

    public String getSongAuth(){return songAuth; }

    public String getSongTitle(){ return songTitle; }

    public String getSongResource(){ return songResource; }

    //	Create from a bundle
    public Song(Bundle b) {
        if (b != null) {
            this.songTitle = b.getString(SONG_NAME);
            this.songResource = b.getString(SONG_RESOURCE);
            this.songAuth = b.getString(SONG_ARTIST);
        }
    }

    //	Package data for transfer between activities
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(SONG_NAME, this.songTitle);
        b.putString(SONG_RESOURCE, this.songResource);
        b.putString(SONG_ARTIST, this.songAuth);
        return b;
    }

    //	Output song data
    @Override
    public String toString() {
        return songTitle;
    }
}
