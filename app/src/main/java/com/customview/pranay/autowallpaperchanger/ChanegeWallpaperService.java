package com.customview.pranay.autowallpaperchanger;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Pranay on 19-11-2016.
 */

public class ChanegeWallpaperService extends Service{

    Timer timer;
    private long currentTime = 0;
    private Handler handler;
    private Integer images[]={R.drawable.image2,R.drawable.image1};
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        timer.schedule(new TimerTaskHelper(),2000,3000);
        currentTime = System.currentTimeMillis();
        Log.d("time",String.valueOf(System.currentTimeMillis()));

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(ChanegeWallpaperService.this,"Wallpaper Changed :)", Toast.LENGTH_SHORT).show();
                Log.d("time",msg.getData().getString("CURRENT_TIME"));
            }
        };
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class TimerTaskHelper extends TimerTask{

        @Override
        public void run() {
            final long time = System.currentTimeMillis() - currentTime;

            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("CURRENT_TIME",String.valueOf(time));
            message.setData(bundle);
            handler.sendMessage(message);

            Random random = new Random();
            int number = random.nextInt(2);

            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(getApplicationContext());
            try {
                myWallpaperManager.setResource(images[number]);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("time","stopped");
    }

    public ChanegeWallpaperService() {
    }
}