package com.example.startactivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public  class MainActivity2 extends Activity {//MainActivity2继承Activity类包
    static boolean isPlay = true; //设置音乐播放状态变量
    MediaPlayer mediaPlayer;//定义音乐播放器对象
    Button music_btn;//定义控制音乐播放按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate()头部方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        music_btn = (Button) findViewById(R.id.btn_music);//获取背景音乐按钮
        PlayMusic();//调用播放背景音乐的方法


        onStop();          //用于实现跳转界面时，停止当前背景音乐
        onDestroy();       //实现当游戏主界面清空所占内存资源时，停止背景音乐,并释放音乐资源所占的内存
        onRestart();
    }  //onCreate()尾部方法


    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.main_music);   //获取到main_music音乐按钮
        mediaPlayer.setLooping(true);   //设置音乐循环播放
        mediaPlayer.start();   //启动播放音乐
    }

    public void OnPlay(View view) {//中间按钮单击事件
        startActivity(new Intent(MainActivity2.this, SelectActivity.class));
    }

    public void OnAbout(View view) {//右下角按钮单击事件
        startActivity(new Intent(MainActivity2.this, AboutActivity.class));
    }

    public void OnMusic(View view) {//单击事件 音乐播放时单击按钮停止音乐播放，音乐停止时单击按钮播放音乐
        if (isPlay == true) {    //如果音乐处于播放状态
            if (mediaPlayer != null) {//音乐播放器不为空时
                mediaPlayer.stop();//停止音乐播放
                //设置按钮为停止状态背景
                music_btn.setBackgroundResource(R.drawable.btn_music2);
                isPlay = false;//音乐处于停止状态
            }
        } else {//如果音乐处于停止状态
            PlayMusic();//调用播放背景音乐方法，播放音乐
            //设置按钮为播放状态背景
            music_btn.setBackgroundResource(R.drawable.btn_music1);
            isPlay = true;//设置音乐处于播放状态

        }
    }
    protected void onStop() {           //该方法实现游戏主界面停止时，背景音乐停止

        super.onStop();
        if (mediaPlayer != null){      //音乐播放器不为空时
            mediaPlayer.stop();         //停止音乐播放
        }
    }


    protected  void onDestroy() {       //该方法实现游戏主界面清空所占内存资源时，背景音乐停止并清空音乐资源

        super.onDestroy();
        if (mediaPlayer != null) {      //音乐播放器不等于空时
            mediaPlayer.stop();         //音乐停止
            mediaPlayer.release();      //清空音乐资源
            mediaPlayer = null;         //设置音乐播放器等于(为)空


        }
    }

    protected void  onRestart() {       //该方法实现从另一界面返回游戏主界面时，根据音乐播放状态播放音乐

        super.onRestart();
        if (isPlay ==true){             //如果音乐处于播放背景音乐方法,播放音乐
            PlayMusic();                //调用播放背景音乐方法,播放音乐
        }
    }
}//MainActivity2尾部
