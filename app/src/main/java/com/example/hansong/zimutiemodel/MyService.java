    package com.example.hansong.zimutiemodel;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    //emoji 动画
    public class MyService extends Service {
    static boolean runing = true;
    private static final String TAG = "MyService";
        private static AudioManager audioManager;
        private WindowManager manager;
        private WindowManager.LayoutParams layoutParams;
        private WindowManager.LayoutParams layoutParams2;
        private TextView emojibody;
        private View Popopview;
        private View emoji;
    private Button open;
        private TextView body;
        private View settings;
        private View bottom;
    private boolean frog= false;
        private TextView tishi;
        private Button subsec;
        private View timesetting;
        private Button addsec;
        private Button home;
        private Button hide;
    private Button font;
    private Button sizeone;
    private Button sizetwo;
    private Button sizethree;
    private Button sizefour;
    private Button sizefive;
    private Button fontcolor;
    private Button moon;
    private Button fire;
    private Button water;
    private Button tree;
    private Button gold;
    private Button minimum;
    boolean isopen = false;

//        private Button skip;
        private EditText skipto;
        private Button textcolor;
        String info;
        static String data = "";
        ArrayList<SRT> srtList = new ArrayList<>();
        public MyService() {
        }

        public static void myServiceStart(Context context, String data,String infor){
            Intent intent = new Intent(context,MyService.class);
            intent.putExtra("data",data);
            intent.putExtra("infor",infor);

            //如果后台有音乐播放的话，出一个提示框，要求用户关闭
            audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            boolean hadaudio =true;
            hadaudio = audioManager.isMusicActive();
            if (hadaudio){
                android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
                dialog.setTitle("　…(⊙_⊙;)… ");
                dialog.setMessage("检测到后台正在播放音乐，会影响软件的使用，是否关闭后台播放？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("好的，去关掉它", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                dialog.setNegativeButton("不，在听一会", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }else {

                context.startService(intent);

                ActivityCollector.finishall();
            }

        }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent,int flags, int startId) {

            Log.d(TAG, String.valueOf(runing));
            info = intent.getStringExtra("infor");
            Intent intents = new Intent(this,MainInterface.class);
            PendingIntent pi = PendingIntent.getActivity(this,0,intents,0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("字幕帖运行中")
                    .setContentText(info)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pi)
                    .setSmallIcon(R.mipmap.serv)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.serv))
                    .build();
            data = intent.getStringExtra("data");
//            Log.d(TAG, data);
            Log.d(TAG, "My Service is Running!");
            Log.d(TAG, info);
            startForeground(1,notification);
            makeSrtlist();
            setupGui();

            return super.onStartCommand(intent, flags, startId);
        }


            public void makeSrtlist(){
            String[] sentences = data.split("\n");
                for (int i = 0;i<sentences.length;i++){
                    if (i%4==1) {

                        String timeTotime = sentences[i];
                        int begin_hour = Integer.parseInt(timeTotime.substring(0, 2));
                        int begin_mintue = Integer.parseInt(timeTotime.substring(3, 5));
                        int begin_scend = Integer.parseInt(timeTotime.substring(6, 8));
                        int begin_milli = Integer.parseInt(timeTotime.substring(9, 12));
                        //--------------------------------------------------------
                        int beginTime = (begin_hour * 3600 + begin_mintue * 60 + begin_scend)
                                * 1000 + begin_milli;
                        int end_hour = Integer.parseInt(timeTotime.substring(17, 19));
                        int end_mintue = Integer.parseInt(timeTotime.substring(20, 22));
                        int end_scend = Integer.parseInt(timeTotime.substring(23, 25));
                        int end_milli = Integer.parseInt(timeTotime.substring(26, 29));
                        //--------------------------------------------
                        int endTime = (end_hour * 3600 + end_mintue * 60 + end_scend)
                                * 1000 + end_milli;
//                        Log.d(TAG, String.valueOf(beginTime));
                        srtList.add(new SRT(sentences[i+1],beginTime,endTime));
                    }
                }

                Log.d(TAG, "makeSrtlist: ");
            }

            Handler handler;

    final int clearbody = 2;
    final int refreshbody = 1;
    final int refrashtime = 3;
    final int stoping = 4;
    final int begaing = 5;
    boolean dog = false;
    final int timeone = 6;
    final int timetwo = 7;
    final int timethree = 8;
    final int timefour = 9;
    final int emojiblink=10;
    final int emojiblinktwo = 11;
    boolean cat = true;
    boolean asdf = false;



            public void setupGui(){

            Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/aotf.otf");


                handler = new Handler(){

                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                Toast.makeText(MyService.this,"start",Toast.LENGTH_SHORT).show();
                                emoji.setVisibility(View.GONE);
                                manager.addView(Popopview,layoutParams2);
                                break;
                            case refreshbody:
                                String bodystring = (String) msg.obj;
                                body.setText(bodystring);
                                break;
                            case clearbody:
                                body.setText("         \n      ");
                                break;
                            case refrashtime:
                                long min = nowmile/60000;
                                long sec = nowmile%60000/1000;
                                String fk = String.valueOf(min)+":"+String.valueOf(sec);
                                SpannableString s = new SpannableString(fk);
                                skipto.setHint(s);
                                break;
                            case stoping:
                                dog=true;
                                tishi.setVisibility(View.VISIBLE);
                                body.setBackgroundResource(R.color.half);
                                timesetting.setBackgroundResource(R.color.half);
                                timesetting.setVisibility(View.VISIBLE);
                                break;
                            case begaing:
                                tishi.setVisibility(View.INVISIBLE);
                                timesetting.setVisibility(View.VISIBLE);
                                timesetting.setBackgroundResource(R.color.half);
                                if (dog&&settings.getVisibility()==View.INVISIBLE){
                                    dog=false;
                                    body.setBackgroundResource(R.color.traspare);
                                }
                                break;
                            case timeone:
                                timesetting.setBackgroundResource(R.color.one);
                                break;
                            case timetwo:
                                timesetting.setBackgroundResource(R.color.two);
                                break;
                            case timethree:
                                timesetting.setBackgroundResource(R.color.three);
                                break;
                            case timefour:
                                timesetting.setVisibility(View.INVISIBLE);
                                break;
                            case emojiblink:
                                emojibody.setText(R.string.emojiblink);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(160);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        handler.sendEmptyMessage(emojiblinktwo);
                                    }
                                }).start();
                                break;
                            case emojiblinktwo:
                                emojibody.setText(R.string.emojinorm);
                                break;
                            default:
                                break;
                        }
                    }

                };

                manager = (WindowManager)MyService.this.getSystemService(Context.WINDOW_SERVICE);
                layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                        PixelFormat.TRANSPARENT);
                layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                layoutParams.gravity = Gravity.CENTER|Gravity.RIGHT;
                layoutParams.y = 150;

                layoutParams2 = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                        PixelFormat.TRANSPARENT);
                layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                layoutParams2.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                layoutParams2.gravity =  Gravity.CENTER;
                layoutParams2.y = 150;

                emoji = LayoutInflater.from(MyService.this).inflate(R.layout.emoji,null);
                emoji.setOnTouchListener(new emojiFloatingListener());
                emojibody = emoji.findViewById(R.id.emojibody);
                open = emoji.findViewById(R.id.open);

                Popopview = LayoutInflater.from(MyService.this).inflate(R.layout.popwindow,null);
                tishi = Popopview.findViewById(R.id.tishi);
                timesetting = Popopview.findViewById(R.id.timesetting);
                body = Popopview.findViewById(R.id.textbody);
                settings = Popopview.findViewById(R.id.setting);

                minimum = Popopview.findViewById(R.id.minimum);

                fontcolor = Popopview.findViewById(R.id.fontcolor);
                moon = Popopview.findViewById(R.id.yue);
                fire = Popopview.findViewById(R.id.huo);
                water = Popopview.findViewById(R.id.shui);
                tree = Popopview.findViewById(R.id.mu);
                gold = Popopview.findViewById(R.id.jin);

                hide = Popopview.findViewById(R.id.hide);
                font = Popopview.findViewById(R.id.font);
                sizeone = Popopview.findViewById(R.id.sizeone);
                sizetwo = Popopview.findViewById(R.id.sizetwo);
                sizethree = Popopview.findViewById(R.id.sizethree);
                sizefour = Popopview.findViewById(R.id.sizefour);
                sizefive = Popopview.findViewById(R.id.sizefive);

                bottom  = Popopview.findViewById(R.id.bottom);
                subsec = Popopview.findViewById(R.id.subsec);
                addsec = Popopview.findViewById(R.id.addsec);

//              skip =Popopview.findViewById(R.id.skip);
                final View colorsel = Popopview.findViewById(R.id.colorselect);
                final View fontsel = Popopview.findViewById(R.id.fontselect);
                skipto = Popopview.findViewById(R.id.skiptarget);
                home = Popopview.findViewById(R.id.home);
                minimum = Popopview.findViewById(R.id.minimum);

                skipto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                        manager.updateViewLayout(Popopview,layoutParams2);
                        asdf = true;
                    }
                });
                skipto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i== EditorInfo.IME_ACTION_DONE){
                            asdf =false;
                            String time = skipto.getText().toString();
                            Log.d(TAG, time);
                            layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            manager.updateViewLayout(Popopview,layoutParams2);
                            if (time.equals("")){
                                time = "0:0";
                                Toast.makeText(MyService.this,"请输入要跳转到的时间",Toast.LENGTH_SHORT).show();
                            }else {
                                if (audioManager.isMusicActive()){
                                    Toast.makeText(MyService.this,"空降失败？？？提示： 暂停播放后空降会更精确#",Toast.LENGTH_SHORT).show();
                                }else {

                                    Toast.makeText(MyService.this,"跳转到"+time,Toast.LENGTH_SHORT).show();
                                }
                                String[] kk= time.split(":");
                                long mileofnow = 0;
                                if (kk.length==1){
                                    if (kk[0].length()<3)
                                        mileofnow =Long.parseLong(kk[0])*1000;
                                    else mileofnow=Long.parseLong(kk[0].substring(0,2))*60000+Long.parseLong(kk[0].substring(2))*1000;
                                }else {
                                    mileofnow = Long.parseLong(kk[0])*60000+Long.parseLong(kk[1])*1000+600;
                                }
                                startmile+=nowmile-mileofnow;
                                nowmile=beginstop- startmile;
                                handler.sendEmptyMessage(refrashtime);
                            }
                            skipto.setText("");


                            layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            manager.updateViewLayout(Popopview,layoutParams2);
                        }
                        return false;
                    }
                });
                skipto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (!b&&asdf){
                            asdf =false;
                            String time = skipto.getText().toString();
                            Log.d(TAG, time);
                            layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            manager.updateViewLayout(Popopview,layoutParams2);
                            if (time.equals("")){
                                time = "0:0";
                                Toast.makeText(view.getContext(),"请输入要跳转到的时间",Toast.LENGTH_SHORT).show();
                            }else {
                                if (audioManager.isMusicActive()){
                                    Toast.makeText(view.getContext(),"空降失败？？？提示： 暂停播放后空降会更精确#",Toast.LENGTH_SHORT).show();
                                }else {

                                    Toast.makeText(view.getContext(),"跳转到"+time,Toast.LENGTH_SHORT).show();
                                }
                                String[] kk= time.split(":");
                                long mileofnow = 0;
                                if (kk.length==1){
                                    if (kk[0].length()<3)
                                        mileofnow =Long.parseLong(kk[0])*1000;
                                    else mileofnow=Long.parseLong(kk[0].substring(0,2))*60000+Long.parseLong(kk[0].substring(2))*1000;
                                }else {
                                    mileofnow = Long.parseLong(kk[0])*60000+Long.parseLong(kk[1])*1000+600;
                                }
                                startmile+=nowmile-mileofnow;
                                nowmile=beginstop- startmile;
                                handler.sendEmptyMessage(refrashtime);
                            }
                            skipto.setText("");


                            layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            manager.updateViewLayout(Popopview,layoutParams2);
                        }
                    }
                });

                subsec.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int event = motionEvent.getAction();
                        switch (event){
                            case MotionEvent.ACTION_DOWN:

                                subsec.setBackgroundResource(R.color.press);
                                break;
                            case MotionEvent.ACTION_UP:
                                startmile+=500;
                                subsec.setBackgroundResource(R.color.traspare);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                addsec.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int event = motionEvent.getAction();
                        switch (event){
                            case MotionEvent.ACTION_DOWN:

                                addsec.setBackgroundResource(R.color.press);
                                break;
                            case MotionEvent.ACTION_UP:
                                startmile-=500;
                                addsec.setBackgroundResource(R.color.traspare);

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                hide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cat){
                            cat = false;
                            bottom.setBackgroundColor(Color.parseColor("#4D4D4D"));
                        }else {
                            cat= true;
                            bottom.setBackgroundResource(R.color.traspare);
                        }
                    }
                });
                minimum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        frog = true;
                        emoji.setVisibility(View.VISIBLE);
                        Popopview.setVisibility(View.GONE);
                    }
                });

//                textcolor.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        int event = motionEvent.getAction();
//                        switch (event){
//                            case MotionEvent.ACTION_DOWN:
////                                textcolor.setBackgroundResource(R.color.press);
//                                break;
//                            case MotionEvent.ACTION_UP:
//                                if (body.getCurrentTextColor()==Color.parseColor("#B23AEE")){
//                                    body.setTextColor(Color.WHITE);
//                                    body.setShadowLayer(4,3,0,Color.BLACK);
//                                }else if (body.getCurrentTextColor()==Color.WHITE){
//                                    body.setTextColor(Color.CYAN);
//                                    body.setShadowLayer(7,2,0,Color.BLUE);
//                                }else if (body.getCurrentTextColor()==Color.CYAN){
//                                    body.setTextColor(Color.BLUE);
//                                    body.setShadowLayer(6,2,0,Color.parseColor("#8DEEEE"));
//                                }else if (body.getCurrentTextColor()==Color.BLUE){
//                                    body.setTextColor(Color.MAGENTA);
//                                    body.setShadowLayer(4,3,0,Color.parseColor("#FFFF00"));
//                                }else if (body.getCurrentTextColor()==Color.MAGENTA){
//                                    body.setTextColor(Color.YELLOW);
//                                    body.setShadowLayer(6,2,0,Color.parseColor("#A0522D"));
//                                }else if (body.getCurrentTextColor()==Color.YELLOW){
//                                    body.setTextColor(Color.RED);
//                                    body.setShadowLayer(5,2,0,Color.WHITE);
//                                }else if (body.getCurrentTextColor()==Color.RED){
//                                    body.setShadowLayer(5,2,0,Color.parseColor("#EECFA1"));
//                                    body.setTextColor(Color.parseColor("#00EE00"));
//                                }else if (body.getCurrentTextColor()==Color.parseColor("#00EE00")){
//                                    body.setTextColor(Color.BLACK);
//                                    body.setShadowLayer(5,0,0,Color.WHITE);
//                                }else if (body.getCurrentTextColor()==Color.BLACK){
//                                    body.setTextColor(Color.parseColor("#B23AEE"));
//                                    body.setShadowLayer(5,1,0,Color.parseColor("#00FA9A"));
//                                }
//
//                                break;
//
//                        }
//                        return false;
//                    }
//                });
                open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Popopview.setVisibility(View.VISIBLE);
                        emoji.setVisibility(View.GONE);
                    }
                });
                font.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int event = motionEvent.getAction();
                        switch (event){
                            case MotionEvent.ACTION_DOWN:
                                font.setBackgroundResource(R.color.press);
                                break;
                            case MotionEvent.ACTION_UP:
                                font.setBackgroundResource(R.color.half);
                                colorsel.setVisibility(View.INVISIBLE);
                                if (fontsel.getVisibility()==View.VISIBLE){
                                    fontsel.setVisibility(View.INVISIBLE);
                                }else {
                                    fontsel.setVisibility(View.VISIBLE);
                                }
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                sizeone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextSize(18);
                        fontsel.setVisibility(View.INVISIBLE);
                    }
                });
                sizetwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextSize(20);
                        fontsel.setVisibility(View.INVISIBLE);
                    }
                });
                sizethree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextSize(22);
                        fontsel.setVisibility(View.INVISIBLE);
                    }
                });
                sizefour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextSize(24);
                        fontsel.setVisibility(View.INVISIBLE);
                    }
                });
                sizefive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextSize(26);
                        fontsel.setVisibility(View.INVISIBLE);
                    }
                });
                fontcolor.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        int event = motionEvent.getAction();
                        switch (event){
                            case MotionEvent.ACTION_DOWN:
                                fontcolor.setBackgroundResource(R.color.press);
                                break;
                            case MotionEvent.ACTION_UP:
                                fontcolor.setBackgroundResource(R.color.half);
                                fontsel.setVisibility(View.INVISIBLE);
                                if (colorsel.getVisibility()==View.INVISIBLE){
                                    colorsel.setVisibility(View.VISIBLE);
                                }else {
                                    colorsel.setVisibility(View.INVISIBLE);
                                }
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                moon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextColor(Color.parseColor("#F0FFFF"));
                        body.setShadowLayer(6,4,4,Color.BLACK);
                        colorsel.setVisibility(View.INVISIBLE);
                    }
                });
                fire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextColor(Color.parseColor("#FFC1C1"));
                        body.setShadowLayer(6,4,4,Color.parseColor("#8B2500"));
                        colorsel.setVisibility(View.INVISIBLE);
                    }
                });
                water.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextColor(Color.parseColor("#AEEEEE"));
                        body.setShadowLayer(6,4,4,Color.parseColor("#483D8B"));
                        colorsel.setVisibility(View.INVISIBLE);
                    }
                });
                tree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextColor(Color.parseColor("#C0FF3E"));
                        body.setShadowLayer(6,4,4,Color.parseColor("#8B4C39"));
                        colorsel.setVisibility(View.INVISIBLE);
                    }
                });
                gold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        body.setTextColor(Color.parseColor("#EEEE00"));
                        body.setShadowLayer(6,4,4,Color.parseColor("#8B5A00"));
                        colorsel.setVisibility(View.INVISIBLE);
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                Thread.sleep(4000);
                            }catch (Exception e){

                            }if (!touching)handler.sendEmptyMessage(emojiblink);
                            try {
                                Thread.sleep(300);
                            }catch (Exception e){

                            }if (!touching)handler.sendEmptyMessage(emojiblink);
                            try {
                                Thread.sleep(2700);
                            }catch (Exception e){

                            }if (!touching)handler.sendEmptyMessage(emojiblink);
                            try {
                                Thread.sleep(300);
                            }catch (Exception e){

                            }   if (!touching)handler.sendEmptyMessage(emojiblink);
                            try {
                                Thread.sleep(500);
                            }catch (Exception e){

                            }if (!touching)handler.sendEmptyMessage(emojiblink);
                        }
                    }
                }).start();
//------------------------------------------------------------------------------------------------------
                home.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        int event = motionEvent.getAction();
                        switch (event){
                            case MotionEvent.ACTION_DOWN:
                                home.setBackgroundResource(R.color.colorPrimaryDark);
                                Intent intent = new Intent(MyService.this,MainInterface.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                            case MotionEvent.ACTION_UP:
                                home.setBackgroundResource(R.color.yellotrans);
                                break;
                            default:
                                break;

                        }
                        return false;
                    }
                });

                body.setOnTouchListener(new FloatingListener());
                body.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (settings.getVisibility()==View.VISIBLE){
                            body.setBackgroundResource(R.color.traspare);
                            settings.setVisibility(View.INVISIBLE);
                            if (cat)
                            bottom.setVisibility(View.INVISIBLE);
                            layoutParams2.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                            manager.updateViewLayout(Popopview,layoutParams2);
                            dog  =true;
                        }else {
                            body.setBackgroundResource(R.color.half);
                            settings.setVisibility(View.VISIBLE);
                            bottom.setVisibility(View.VISIBLE);
                            dog = false;
                        }
                    }
                });
                body.setTypeface(typeFace);
                emoji.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!frog) {
                            Toast.makeText(MyService.this, "打开" + info + "时，自动播放对应字幕," + "此状态下请不要播放其他视频或音乐,因为......", Toast.LENGTH_LONG).show();
                        }else {
                            if (isopen) {
                                open.setVisibility(View.GONE);
                                isopen = false;
                            } else {
                                isopen = true;
                                open.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
                manager.addView(emoji,layoutParams);
                LooperThread tt= new LooperThread();
                tt.start();
            }

        @Override
        public void onDestroy() {
            super.onDestroy();
            runing = false;
            Log.d(TAG, String.valueOf(runing));
            Log.d(TAG, "stop server");
            Popopview.setVisibility(View.GONE);
            emoji.setVisibility(View.GONE);
        }
        private int mTouchStartX,mTouchStartY,mTouchCurrentX,mTouchCurrentY;
        //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
        private int mStartX,mStartY,mStopX,mStopY;
        private boolean isMove = true;//判断悬浮窗是否移动

    int i = 0;
    private int mStopTouchY;
    private class FloatingListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View arg0, MotionEvent event) {
            int action = event.getAction();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    isMove = false;
                    mTouchStartY = (int)event.getRawY();
                    mStartY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTouchCurrentY = (int) event.getRawY();
                    layoutParams2.y += mTouchCurrentY - mTouchStartY;
                    manager.updateViewLayout(Popopview , layoutParams2);
                    i++;
                    mTouchStartY = mTouchCurrentY;
                    break;
                case MotionEvent.ACTION_UP:
                    mStopY = (int)event.getY();
                    mStopTouchY = (int)event.getRawY();
                    Log.d(TAG, String.valueOf(i));
                    if (i>3){
                        isMove = true;
                    }
                    i=0;
                    break;
            }
            return isMove;  //此处必须返回false，否则OnClickListener获取不到监听
        }

    }
    int af = 0;
        private class emojiFloatingListener implements View.OnTouchListener{

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                int action = event.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        touching = true;
                        isMove = false;
                        mTouchStartY = (int)event.getRawY();
                        mStartY = (int)event.getY();
                        emojibody.setText(R.string.emojiblink);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mTouchCurrentY = (int) event.getRawY();
                        layoutParams.y += mTouchCurrentY - mTouchStartY;
                        manager.updateViewLayout(emoji , layoutParams);
                        if (af>4&&af<=100)emojibody.setText(R.string.emojiafford) ;else if (af>20){
                        emojibody.setText(R.string.emojidizzy);
                        }
                        af++;
                        mTouchStartY = mTouchCurrentY;
                        break;
                    case MotionEvent.ACTION_UP:
                        touching = false;
                        mStopY = (int)event.getY();

                        emojibody.setText(R.string.emojinorm);
                        if (af>3){
                            isMove = true;
                        }
                        Log.d(TAG, String.valueOf(af));
                        af=0;
                        break;
                }
                return isMove;  //此处必须返回false，否则OnClickListener获取不到监听
            }

        }
//    Handler progressHandler;
    long skipmile=0;
    long nowmile=0;
    long startmile = 0;
    boolean refrash = false;
    long beginstop = 0;
    boolean touching = false;

    //    boolean a = true;
    class LooperThread extends Thread {

        final int clearbody = 2;
        final int refreshbody = 1;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            runing = true;
            while (!audioManager.isMusicActive()) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
            }

                Log.d(TAG, String.valueOf(runing));

                if (!runing)
            {
                break;
            }
            }
            try {
                Thread.sleep(400);
            }catch (Exception e ){
            }
            //  开始播放视频
            if (runing) {
                handler.sendEmptyMessage(0);
                startmile = System.currentTimeMillis();
//            播放字幕的循环
                Log.d(TAG, "start");
            }

            while(true){

                Log.d(TAG, String.valueOf(runing));

                if (!runing)break;
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                }
                nowmile = System.currentTimeMillis()-startmile;
                boolean ba= true;
                for (SRT srt:srtList){
                    if (srt.getEndMile()>nowmile&&srt.getStartMile()<nowmile){
                        Message message = handler.obtainMessage();
                        message.obj = srt.getBody();
                        message.what = refreshbody;
                        handler.sendMessage(message);
                        ba = false;
                        break;
                    }
                }
                if (ba){
                    ba=false;
                    handler.sendEmptyMessage(clearbody);
                }
                handler.sendEmptyMessage(refrashtime);
                if (!audioManager.isMusicActive()){
                    handler.sendEmptyMessage(stoping);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean fff= true;
                            try {
                                Thread.sleep(2000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (!audioManager.isMusicActive()&&!asdf) {
                                handler.sendEmptyMessage(timeone);
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else fff =false;
                            if (!audioManager.isMusicActive()&&!asdf&&fff) {
                                handler.sendEmptyMessage(timetwo);
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else fff = false;
                            if (!audioManager.isMusicActive()&&!asdf&&fff) {
                                handler.sendEmptyMessage(timethree);

                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else fff = false;
                            if (!audioManager.isMusicActive()&&!asdf&&fff)
                                handler.sendEmptyMessage(timefour);
                        }
                    }).start();
                    beginstop = System.currentTimeMillis();
                    while (runing){
                        if (audioManager.isMusicActive()){
                            handler.sendEmptyMessage(begaing);
                            startmile+= System.currentTimeMillis()-beginstop;
                            break;
                        }
                        try {
                            Thread.sleep(100);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            }
        }
    }
