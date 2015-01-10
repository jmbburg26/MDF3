package com.bbgatestudios.mdf3app;

import java.io.Serializable;

/**
 * Created by John on 1/9/2015.
 */
public class Song implements Serializable{
    private String songTitle;
    private String songArtist;
    private String songResource;

    public Song(){
        songTitle = "";
        songArtist = "";
        songResource = "";
    }

    public Song(String title, String artist, String resource){
       songTitle = title;
       songArtist = artist;
       songResource = resource;
    }

    public String getTitle(){ return songTitle; }
    public String getArtist(){ return songArtist; }
    public String getResource(){ return songResource; }
}
