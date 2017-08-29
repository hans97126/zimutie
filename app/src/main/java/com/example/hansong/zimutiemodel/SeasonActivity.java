package com.example.hansong.zimutiemodel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;

public class SeasonActivity extends AppCompatActivity {
    public static void seasonActionStart(Context context, ArrayList<Season> Seasonlist){
        Intent intent = new Intent(context,SeasonActivity.class);
        intent.putExtra("season",(Serializable)Seasonlist);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.hide();
        }
        Intent getintent = getIntent();
        ArrayList<Season> seasons = (ArrayList<Season>)getintent.getSerializableExtra("season");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        seasonAdapter adapter = new seasonAdapter(seasons);
        recyclerView.setAdapter(adapter);
        ActivityCollector.addactivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeactivity(this);
    }
}
