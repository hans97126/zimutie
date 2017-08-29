package com.example.hansong.zimutiemodel;

import android.view.View;

import java.io.Serializable;

/**
 * Created by hansong on 17-7-10.
 */

public class Season implements Serializable{
    String name;
    int episode;
    String seasonid;
    public Season(String seasonid,String name,int episode){
        this.episode = episode;
        this.name = name;
        this.seasonid = seasonid;
    }
    public int getEpisode(){return episode;}
    public String getName(){return name;}

    public String getSeasonid() {
        return seasonid;
    }
}
