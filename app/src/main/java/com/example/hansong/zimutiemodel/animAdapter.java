package com.example.hansong.zimutiemodel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hansong on 17-7-3.
 */

public class animAdapter extends RecyclerView.Adapter<animAdapter.ViewHolder> {
    private List<Anim> animList;
    static String animname;
    static String id;
    private static final String TAG = "animAdapter";
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView animName;
        public ViewHolder (View view){
            super(view);
            animName = (TextView) view.findViewById(R.id.items);
        }
    }

    public animAdapter(List<Anim> animList){
        this.animList=animList;
//        cass=cla;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.animName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Anim anim = animList.get(position);

                ArrayList<Season> seasons= new ArrayList<Season>();
                int season = anim.getSeason();
                int[] episode = anim.getEpisode();
                for (int i = 0;i<season;i++){
                    seasons.add(new Season(Integer.toString(i+1),"第 "+(i+1)+" 季",episode[i]));
                }
                id = anim.getId();
                animname= anim.getName();
                SeasonActivity.seasonActionStart(view.getContext(),seasons);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Anim anim = animList.get(position);
        holder.animName.setText(anim.getName());
    }

    public int getItemCount(){
        return animList.size();
    }

}

