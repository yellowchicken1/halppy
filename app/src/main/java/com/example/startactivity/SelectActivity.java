package com.example.startactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends Activity {//SelectActivity头部
     MediaPlayer mediaPlayer;//定义音乐播放器对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if(MainActivity2.isPlay==true){     //如果MainActivity2界面设置背景音乐为播放音乐状态
            PlayMusic();                   //调用播放音乐的方法

        }

    }//onCreate()尾部
     public  void OnOne(View view){//实现从选择数字界面向数字1书写界面的跳转
        startActivity(new Intent(SelectActivity.this,OneActivity.class));
     }
   private void PlayMusic(){  //播放背景音乐的方法
        //创建音乐播放器对象并加载播放音乐文件
       mediaPlayer = MediaPlayer.create(this,R.raw.number_music);
       mediaPlayer.setLooping(true);        //设置音乐循环播放
       mediaPlayer.start();                 //启动音乐播放
   }

   //该方法实现选择数字界面停止时，背景音乐停止
  protected void onStop() {
      super.onStop();
      if (mediaPlayer !=null){      //音乐播放器不为空时
          mediaPlayer.stop();       //停止 mediaPlayer音乐播放
      }
  }

  //该方法实现选择数字界面销毁时，背景音乐停止释放音乐资源
    protected void onDestroy() {

        super.onDestroy();
        if (mediaPlayer !=null){  //音乐播放器不等于空时
            mediaPlayer.stop();     //音乐播放器停止
            mediaPlayer.release();     //释放音乐资源
            mediaPlayer=null;       //设置音乐播放器等于空
        }
    }
  //该方法实现从另一界面返回选择数字界面时，根据音乐播放状态播放音乐
    protected void onRestart() {

        super.onRestart();
        if (MainActivity2.isPlay==true){       //如果音乐处于播放状态
            PlayMusic();       //调用播放音乐背景音乐方法，播放音乐
        }
    }
}//SelectActivity尾部