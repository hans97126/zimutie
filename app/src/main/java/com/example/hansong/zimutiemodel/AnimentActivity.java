package com.example.hansong.zimutiemodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnimentActivity extends AppCompatActivity {

    public static void StartAnimentActivity(Context context, List<Anim> animList){
        Intent intent = new Intent(context,AnimentActivity.class);
        intent.putExtra("anim",(Serializable)animList);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animent);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.hide();
        }
        Intent intent = getIntent();
        ArrayList<Anim> anims = (ArrayList<Anim>)intent.getSerializableExtra("anim");
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        animAdapter adapter = new animAdapter(anims);
        recyclerView.setAdapter(adapter);

        ActivityCollector.addactivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeactivity(this);
    }
}
