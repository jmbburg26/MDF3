/////John Brandenburg/////MDF 1501/////
package com.bbgatestudios.mdf31501;

/**
 * Created by John on 1/11/2015.
 */
public class Song {
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
}
