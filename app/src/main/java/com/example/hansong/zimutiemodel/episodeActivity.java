package com.example.hansong.zimutiemodel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;

public class episodeActivity extends AppCompatActivity {
    public static void episodeActivityStart(Context context, ArrayList<episode> episodes){
        Intent intent = new Intent(context,episodeActivity.class);
        intent.putExtra("episode",(Serializable)episodes);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_episode);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.hide();
        }
        Intent intent = getIntent();
        ArrayList<episode> episodes = (ArrayList<episode>)intent.getSerializableExtra("episode");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        EpisodeAdapter adapter = new EpisodeAdapter(episodes);
        recyclerView.setAdapter(adapter);
        ActivityCollector.addactivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollector.removeactivity(this);
    }
}
