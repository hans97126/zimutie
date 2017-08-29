package com.example.hansong.zimutiemodel;

import java.io.Serializable;

/**
 * Created by hansong on 17-7-10.
 */

public class episode implements Serializable {
    private String episodeid;
    private String name;
    public episode(String episodeid,String name){
        this.name = name;
        this.episodeid = episodeid;
    }
    public String getName(){
        return name;
    }

    public String getEpisodeid() {
        return episodeid;
    }
}
