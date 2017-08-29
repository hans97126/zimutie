package com.example.hansong.zimutiemodel;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.jar.Manifest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainInterface extends AppCompatActivity {
    private String identify;
    private final String url = "http://139.196.143.66/test/";
   private Button selectButton;
   private Button settingButton;
    boolean reading = false;
   private Button quitButton;
   private static Button startButton;
   private ProgressBar progressbar;
    static ArrayList<Anim> anims = new ArrayList<>();
    ArrayList<episode> episodelist = new ArrayList<>();
    private static final String TAG = "MainInterface";

    public static void StartMainActivity(Context context,String infor){
        //用来启动这个活动
        Intent intent = new Intent(context,MainInterface.class);
        intent.putExtra("infor",infor);
        context.startActivity(intent);
        MyService.runing=false;

        Log.d(TAG, String.valueOf(MyService.runing));
    }

    private String information = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGUI();

        
        setupButton();
        ActivityCollector.addactivity(this);
        ///
//        String ttt = "hh/";
//        String[] gg = ttt.split("/");
//        Log.d(TAG,String.valueOf(gg.length)+"------------------");
    }//end onCreateMethod

    private void setupButton() {
        selectButton = (Button) findViewById(R.id.select);
        settingButton = (Button) findViewById(R.id.setting);
        quitButton = (Button) findViewById(R.id.quit);
        startButton = (Button) findViewById(R.id.start);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainInterface.this,Menuactivity.class);
                startActivity(intent);
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final ProgressDialog progressDialog = new ProgressDialog(MainInterface.this);
                progressDialog.setTitle("正在加载。。。");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                final Handler mHandler = new Handler() {

                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                //完成主界面更新,拿到数据
                            Toast.makeText(view.getContext(),"阿勒 ？没有网？看看以前缓存的资源吧！",Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                progressDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }

                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String responsedata ="";
                        try {
                            //从网上解析 存储 方便以后没网的时候使用
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(url+"index.json").build();
                            Response response = client.newCall(request).execute();
                            responsedata = response.body().string();
                            if (anims.isEmpty()) {
                                parseJSONwitiJSONObject(responsedata);
                            }
                            //启动recyclerview
                            AnimentActivity.StartAnimentActivity(MainInterface.this,anims);
                            //存储 方便以后没网的时候使用
                            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                            editor.putString("index",responsedata);
                            editor.apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                            SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                            String sdf = pref.getString("index","");
                            parseJSONwitiJSONObject(sdf);
                            //传递出信号 让发送一个toast
                            mHandler.sendEmptyMessage(0);
                            //修正以前缓存的资源
                            String FG = pref.getString("anims","");
                            String[] o = FG.split("/");
                            ArrayList<Anim> truelist = new ArrayList<Anim>();
                            for (int i = 0;i<o.length;i++){
                                if (o[i]!="") {
                                    Log.d(TAG, o[i]+"---------------------");
                                    Anim ry = anims.get(Integer.parseInt(o[i]));
                                    //防止列表中多次出现某一个动漫
                                    if (!truelist.contains(ry)) {
                                        truelist.add(ry);
                                    }
                                }
                            }
                            anims.clear();
                            AnimentActivity.StartAnimentActivity(MainInterface.this,truelist);


                        }finally {

                            //ifnoInternet is false
                    mHandler.sendEmptyMessage(1);
//                            AnimentActivity.StartAnimentActivity(MainInterface.this,anims);
                        }
                    }
                }).start();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyService.runing=false;

                Log.d(TAG, String.valueOf(MyService.runing));
                Intent intent2 = new Intent(view.getContext(),MyService.class);
                stopService(intent2);
                try {
                    Thread.sleep(230);
                }catch (Exception e){
                    e.printStackTrace();
                }
                    if (information == null){
                        Toast.makeText(MainInterface.this,"  (..•˘_˘•..)\n大佬,先选个字幕吧^",Toast.LENGTH_SHORT).show();
                    }else if (!reading){
                            identify = animAdapter.id+"/"+seasonAdapter.seasonid+"/"+EpisodeAdapter.episodeid+".txt";

                                try {
                                //从本地读取出来后 把文档传入解析

//                                    Log.d(TAG, url+identify);
                                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);

                                    String resource = pref.getString(identify,"");
                                    if (resource!="") {
                                        MyService.myServiceStart(MainInterface.this, resource, information);
                                    }else if (resource=="") {
                                        //如果本地没有
                                        progressbar.setVisibility(View.VISIBLE);
                                        Toast.makeText(MainInterface.this,"正在下载该番剧的字幕（约几百ｋｂ），请稍等...",Toast.LENGTH_SHORT).show();
                                        readall(animAdapter.id);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //尝试读取该season中所有的文件，如果没有就从网上下载，存到data中的方法
                                    //从本地读取出来后 把文档传入解析
                                }

                           }
                    }

        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishall();
                Intent intent = new Intent(view.getContext(),MyService.class);
                stopService(intent);
            }
        });

       if (information!=null){startButton.setBackgroundResource(R.drawable.button);}else {
           selectButton.setBackgroundResource(R.drawable.button);
       }
    }

    private void readall(String id){
    final String ids = id;
        final int a = Integer.parseInt(ids)-1;
        //处理解析过程中需要的ui操作
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                progressbar.setProgress(msg.what);
                if (msg.what == 300){
                    SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
                    String resp  = preferences.getString(identify,"");
                    if (resp!="") {
                        MyService.myServiceStart(MainInterface.this, resp, information);
                    }else if (resp==""){
                        Toast.makeText(MainInterface.this,"电波传不到啊，网络有些不稳定呢",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                reading = true;
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                OkHttpClient client = new OkHttpClient();
                Anim anim =  anims.get(a);
                boolean hadInternet = true;
                int season = anim.getSeason();
                int[] episode = anim.getEpisode();
                int total = 0;
                for (int q =0;q<episode.length;q++){
                    total+=episode[q];
                }
                int member = 0;
                for (int e = 1;e<=season;e++){
                    //从本地读取，如果本地没有就从网上获取
                    for (int f = 1;f<=episode[e-1];f++){
                        String g = ids+"/"+String.valueOf(e)+"/"+String.valueOf(f)+".txt";
                        String resource = pref.getString(g,"");
                        if (resource.equals("")) {
                            Request request = new Request.Builder().url(url+g).build();
                                try {
                                    Response response = client.newCall(request).execute();
                                    String responsedata = response.body().string();
                                    editor.putString(g,responsedata);
                                }catch (Exception h){
                                    h.printStackTrace();
                                    hadInternet = false;
                                }
                        }

                        member++;
                        Message message = new Message();
                        message.what = 100*member/total;
                        mHandler.sendMessage(message);
                    }
                }
                mHandler.sendEmptyMessage(300);

                //把 本地 已经有的动漫标记出来
                if (hadInternet){
                    String EE = pref.getString("anims","");
                    EE+=String.valueOf(a)+"/";
                    editor.putString("anims",EE);
                }

                editor.apply();
                reading = false;
            }
        }).start();

    }
    private void setupGUI() {
        setContentView(R.layout.main_interface);

        ActionBar actionBar = getSupportActionBar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            SharedPreferences shar = getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = shar.edit();
            boolean isfirst = shar.getBoolean("isfirst",true);
            if (isfirst){
                editor.putBoolean("isfirst",false);
                editor.apply();
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
                dialog.setTitle("　 (＝^ω^＝) --");
                dialog.setMessage("Hi 欢迎您使用字幕贴，软件需要开启悬浮窗权限才能运行。开启方法：请到设置中找到权限管理，然后找到本应用，开启悬浮窗权限\n\n如果上述方法找不到,请根据您的机型百度开启悬浮窗的方法");
                dialog.setCancelable(true);
                dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }

        }



        if (actionBar != null) {
            actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.hide();
        }


        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);



        for (int i = 0;i<seasonAdapter.epis;i++){
            episodelist.add(new episode(Integer.toString(i+1),"第 "+(i+1)+" 话"));
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        EpisodeAdapter adapter = new EpisodeAdapter(episodelist);
        recyclerView.setAdapter(adapter);



        WebView gif = (WebView)findViewById(R.id.gif);
        gif.loadUrl(url+"hello.html");
//        gif.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
//        gif.getSettings().setSupportZoom(true);
//// 设置出现缩放工具
//        gif.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        gif.getSettings().setUseWideViewPort(true);
//自适应屏幕

        gif.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        gif.getSettings().setLoadWithOverviewMode(true);
        gif.getSettings().setDefaultTextEncodingName("utf-8");

        Intent getintent = getIntent();
        information = getintent.getStringExtra("infor");
        if (information!=null)Snackbar.make(gif,"已选中"+information,Snackbar.LENGTH_INDEFINITE).show();


    }

        private void parseJSONwitiJSONObject(String jsondata){
            try{
                JSONArray jsonArray = new JSONArray(jsondata);
                for (int i = 0;i <jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String id = jsonObject.getString("id");
                    String season = jsonObject.getString("season");
                    String episode = jsonObject.getString("episode");
                    String[] episodes = episode.split("-");
//                    Log.d(TAG, "name ="+name);
//                    Log.d(TAG, "id ="+id);
//                    Log.d(TAG, "season = "+season);
                    //构建动漫对象方法
                    int[] episos = new int[episodes.length];
                    for (int a = 0;a<episodes.length;a++){
                        Log.d(TAG, "epi"+episodes[a]);
                        episos[a] = Integer.parseInt(episodes[a]);
                    }
                    int seas = Integer.parseInt(season);
                    anims.add(new Anim(id,name,seas,episos));
                }
                for (int b = 0;b<anims.size();b++){
                    Anim a = anims.get(b);
                    Log.d(TAG, a.getId());
                    Log.d(TAG, Integer.toString(b));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeactivity(this);
    }
}
