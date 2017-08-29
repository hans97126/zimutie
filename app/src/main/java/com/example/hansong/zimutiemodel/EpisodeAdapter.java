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
 * Created by hansong on 17-7-10.
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private List<episode> episodes;
    static String Episodename;
    static String episodeid;
    private static final String TAG = "Episodeadapter";
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView episodename;
        public ViewHolder (View view){
            super(view);
            episodename = view.findViewById(R.id.items);
        }
    }

    public  EpisodeAdapter(List<episode> episodes){
        this.episodes = episodes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.episodename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                episode epi = episodes.get(position);
                Episodename = epi.getName();
                episodeid = epi.getEpisodeid();
            String inform = "番剧《"+animAdapter.animname+"》"+seasonAdapter.seasonname+"的"+Episodename;
                MainInterface.StartMainActivity(view.getContext(),inform);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        episode episode = episodes.get(position);
        holder.episodename.setText(episode.getName());

    }

    public int getItemCount(){
        return episodes.size();
    }


}