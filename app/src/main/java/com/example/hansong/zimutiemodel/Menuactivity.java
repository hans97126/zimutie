package com.example.hansong.zimutiemodel;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Menuactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);
        Button button = (Button)findViewById(R.id.clearall);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               File file =new File("/data/data/" + view.getContext().getPackageName() + "/shared_prefs");

                if (file != null && file.exists() && file.isDirectory()) {
                    for (File item : file.listFiles()) {
                        item.delete();
                    }
                }
                Toast.makeText(view.getContext(),"已清除",Toast.LENGTH_SHORT).show();
            }

        });
    }
}
