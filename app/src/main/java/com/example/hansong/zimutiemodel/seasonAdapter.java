package com.example.hansong.zimutiemodel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hansong on 17-7-10.
 */
public class seasonAdapter extends RecyclerView.Adapter<seasonAdapter.ViewHolder> {
    private List<Season> seasonList;
    static String seasonname;
    static String seasonid;
    static int epis = 0;
    private static final String TAG = "animAdapter";
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView seasonName;
        public ViewHolder (View view){
            super(view);
            seasonName = (TextView) view.findViewById(R.id.items);
        }
    }

    public  seasonAdapter(List<Season> seasons){
        this.seasonList = seasons;
    }

//    public seasonAdapter(List<Season> animList){
//        this.seasonList=animList;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.seasonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Season season = seasonList.get(position);
                int episode = season.getEpisode();
                epis=episode;
                ArrayList<episode> episodes = new ArrayList<>();
                for (int i = 0;i<episode;i++){
                    episodes.add(new episode(Integer.toString(i+1),"第 "+(i+1)+" 话"));
                }
                seasonname = season.getName();
                seasonid = season.getSeasonid();
                episodeActivity.episodeActivityStart(view.getContext(),episodes);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Season season = seasonList.get(position);
        holder.seasonName.setText(season.getName());
    }

    public int getItemCount(){
        return seasonList.size();
    }

}