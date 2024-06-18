package com.example.startactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends Activity { /*让MainActivity继承Activity类包  */ //MainActivity类头部

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //创建Timer对象，用于设置启动界面显示的时间
     Timer timer=new Timer();
       //创建timerTask对象，用于实现启动界面向游戏界面主界面的跳转
    TimerTask  timerTask = new TimerTask() {
           @Override
          public void run() {
//                //从当前MainActivity界面跳转到MainActivity2界面
               startActivity(new Intent(MainActivity.this,MainActivity2.class));
                finish();//关闭启动界面
//
           }
       };//这里必须加分号，不然报红
        timer.schedule(timerTask, 2000);//设置启动界面2秒后，跳转到主界面MainActivity2
    }
}