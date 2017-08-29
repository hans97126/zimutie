package com.example.hansong.zimutiemodel;
import java.io.Serializable;
/**
 * Created by hansong on 17-6-24.
 */

public class Anim implements Serializable{

    private String id;
    private String name;
    private int season;
    private int[] episode;

   public Anim(String id,String name,int season,int[] episode){
       this.name=name;
       this.id=id;
       this.season = season;
       this.episode = episode;

   }

   public String getId(){
       return  id;
   }

    public String getName(){
        return name;
    }

    public int getSeason(){
        return season;
    }

    public int[] getEpisode(){
        return episode;
    }
}
