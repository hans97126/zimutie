package com.example.hansong.zimutiemodel;

/**
 * Created by hansong on 17-7-13.
 */

public class SRT {
    String body;
    private long startMile;
    private long endMile;
    public SRT(String body,long startMile,long endMile){
        this.body = body;
        this.startMile = startMile;
        this.endMile = endMile;
    }
    public String getBody(){
        return body;
    }

    public long getEndMile() {
        return endMile;
    }

    public long getStartMile() {
        return startMile;
    }
}
