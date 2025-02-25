package com.example.startactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import util.mCustomProgressDialog;

public class OneActivity extends Activity {// OneActivity头部
    public mCustomProgressDialog mdialog;//定义自定义对话框对象
    MediaPlayer mediaPlayer;  //定义音乐播放器对象
    private ImageView iv_frame;         //显示定数字的 ImageView控件
    int i = 1;        //图片展示到第几张标记
    float x1;   //屏幕按下时的x值
    float y1;     //屏幕按下时的y值
    float x2;    //屏幕离开时的x值
    float y2;   //屏幕离开时的y值
    float x3;   //移动坐标中的x值
    float y3;    //移动坐标中的y值
    int igvx;       //图片x坐标
    int igvy;    //图片y坐标
    int type = 0;     //是否可以书写标识，开关:1开启：0关闭
    int widthPixels;    //屏幕宽度
    int heightPixels;   //屏幕高度
    float scaleWidht;   //宽度的缩放比例
    float scaleHeight;  //高度的缩放比例

    Timer touchTimer = null;    //单击在虚拟按钮上后用于连续动作的计时器
    Bitmap arrdown; // Bitmap图像处理
    boolean typedialog = true;  //dialog对话框状态
    private LinearLayout linearLayout = null;   //LinearLayout线性布局


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//创建的onCreate()方法头部
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_number);   //设置数字1功能界面的布局文件
        if(MainActivity2.isPlay == true){
            PlayMusic();
        }
        initView();   //创建并调用initView()方法
    }//创建的onCreate()方法尾部
    public  void  OnYS(View view){/**创建演示按钮,单击事件方法头部**/
        if (mdialog == null){//如果自定义对话框为空
            //实例化自定义对话框,设置显示文字和动画文件
            mdialog = new mCustomProgressDialog(this,"演示中单击边缘取消",R.drawable.frame1);
        }
        mdialog.show();//显示对话框
    }/**创建演示按钮,单击事件方法尾部**/


    private void PlayMusic() {//播放背景音乐
        //创建音乐播放对象并加载播放音乐文件
        mediaPlayer = MediaPlayer.create(this,R.raw.number_music);
        mediaPlayer .setLooping(true);//设置音乐循环播放
        mediaPlayer.start();//启动音乐播放
    }
    //该方法实现数字1书写界面停止时，背景音乐停止
    protected void  onStop(){
        super.onStop();
        if(mediaPlayer !=null){//音乐播放时不为空
            mediaPlayer.stop();//停止音乐播放
        }
    }
    //该方法实现数字1书写界面销毁时，背景音乐停止并释放音乐资源
    protected  void onDestroy(){
        super.onDestroy();
        if (mediaPlayer !=null){//播放音乐不等于空时
            mediaPlayer.stop();//音乐停止播放
            mediaPlayer.release();//释放音乐资源
            mediaPlayer = null;//音乐播放器等于空时

        }
    }
    private void initView() {   //initView()头部方法
        //获取显示写数字的ImageView控件
        iv_frame = (ImageView) findViewById(R.id.iv_frame);
        //获取写数字区域的布局
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
        //获取书写界面布局
        LinearLayout write_layout = (LinearLayout) findViewById(R.id.LinearLayout_number);
        //设置书写界面布局背景
        write_layout.setBackgroundResource(R.drawable.bg1);
        //获取屏幕宽度
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        //因为图片等资源是按1280*720来准备的，如果是其他分辨率，适应屏幕做准备
        scaleWidht = ((float) widthPixels / 720);
        scaleHeight = ((float) heightPixels / 1280);
        try {
            //通过输入流打开第一张图片
            InputStream is = getResources().getAssets().open("on1_1.png");
            //使用Bitmap解析第一张图片
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.getLayoutParams();
        //获取图片缩放后宽度
        layoutParams.width = (int) (arrdown.getWidth() * scaleHeight);
        //获取图片缩放后高度
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        //根据图片缩放后的宽高,设置iv_frame的宽高
        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {   //获取行动方式头部
                    case MotionEvent.ACTION_DOWN://手指按下事件
                        //获取手指按下时坐标
                        x1 = motionEvent.getX();    //获取手指按下的x坐标
                        y1 = motionEvent.getY();    //获取手指按下的y坐标
                        igvx = iv_frame.getLeft();  //获取手指按下图片的x坐标
                        igvy = iv_frame.getTop();   //获取手指按下的图片的Y坐标
                        //判断当手指按下的坐标大于图片位置的坐标时，证明手指按住移动，此时开启书写
                        if (x1 >= igvx && x1 <= igvx + (int) (arrdown.getWidth() * scaleWidht)
                                && y1 >= igvy & y1 <= igvy + (int) (arrdown.getWidth() * scaleWidht)
                        ) {
                            type = 1;       //开始书写
                        } else {
                            type = 0;       //否则关闭书写
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:       //手势移动中判断
                        igvx = iv_frame.getLeft();      //获取图片中X坐标
                        igvy = iv_frame.getTop();       //获取图片中的Y坐标
                        x2 = motionEvent.getX();        //获取移动中手指在屏幕X坐标的位置
                        y2 = motionEvent.getY();        //获取移动中手指在屏幕Y坐标的位置
                        //根据笔画以及手势做图片的处理，滑动到不同位置，加载不同的图片
                        if (type == 1) {  //如果书写开启
                            //如果手指按下的X坐标大于等于图片的X坐标，或者小于等于缩放图片的X坐标时
                            if (x2 >= igvx && x2 <= igvx + (int) (arrdown.getWidth() * scaleWidht)) {
                                //如果当前手指按下的Y坐标小于等于缩放图片的Y坐标，或者大于等于图片的Y坐标时
                                if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 && y2 >= igvy) {
                                    lodimagep(1);   //调用lodimagep()方法，加载第一张显示图片
                                }
                                //如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                    lodimagep(2);   // 调用lodimagep()方法，加载第二张图片
                                }
                                //如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                                else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight / 24 * 3)) {
                                    lodimagep(3);   // 调用lodimagep()方法，加载第3张图片

                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight / 24 * 4)) {
                                    lodimagep(4);

                                } else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight / 24 * 5)) {
                                    lodimagep(5);

                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 6) {
                                    lodimagep(6);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 7) {
                                    lodimagep(7);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 8) {
                                    lodimagep(8);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 9) {
                                    lodimagep(9);

                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 10) {
                                    lodimagep(10);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 11) {
                                    lodimagep(11);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 12) {
                                    lodimagep(12);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 13) {
                                    lodimagep(13);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 14) {
                                    lodimagep(14);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 15) {
                                    lodimagep(15);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 16) {
                                    lodimagep(16);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 17) {
                                    lodimagep(17);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 18) {
                                    lodimagep(18);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 19) {
                                    lodimagep(19);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 20) {
                                    lodimagep(20);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 21) {
                                    lodimagep(21);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 22) {
                                    lodimagep(22);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 23) {
                                    lodimagep(23);
                                } else if (y2 <= igvy + (int) arrdown.getHeight() * scaleHeight / 24 * 24) {
                                    lodimagep(24);
                                } else {
                                    type = 0;     //手指离开，设置书写关闭
                                }

                            }
                        }

                        break;
                        /*判断手势抬起时,子线程每秒向Hander中发送一次消息,用于实现图片递减显示帧图片*/
                    case MotionEvent.ACTION_UP:  //手势抬起判断
                        type = 0;     //手势关闭
                        //手指离开的时候
                        if (touchTimer != null) {      //判断计时器是否为空
                            touchTimer.cancel();     //中断计时器
                            touchTimer = null;      //设置计时器为空
                        }
                        touchTimer = new Timer();     //初始化计时器
                        touchTimer.schedule(new TimerTask() {   //初始化计时器
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() { //创建子线程
                                    @Override
                                    public void run() {
                                        //创建Message用于发送消息
                                        Message message = new Message();
                                        message.what = 2;   //message消息为2
                                        //发送消息给handler实现倒退显示图片
                                        mHandler.sendMessage(message);
                                    }
                                });
                                thread.start();
                            }
                        }, 300, 200);
                }//获取行动方式尾部
                return true;
            }
        });
    }//initView()方法尾部



    /**创建一个公有的Handler实例化对象,并重写handleMessage()方法,用于接收手势抬起时子线程中传递的消息**/
    public Handler mHandler = new Handler() {    //递减显示帧图片的handler消息头部
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2: //当接收到手势抬起子线程消息时
                    jlodimage();      //调用资源图片倒退显示方法
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };  //递减显示帧图片的handler消息尾部

    private void jlodimage() {  //当手势抬起时数字资源图片倒退显示 jlodimage() 头部
        if (i == 25) {        //如果当前图片位置等于25
        } else if (i < 25) {      //否则当前图片小于25
            if (i > 1) {     //如果当前图片大于1
                i--;
            } else if ( i == 1) {
                i = 1;
                if (touchTimer !=null){  //判断计时器是否为空
                    touchTimer.cancel();    //中断计时器
                    touchTimer=null;        //设置计时器为空
                }

            }
            String name = "on1_"+i; //图片的名称
            //获取图片资源
            int imgid = getResources().getIdentifier(name,"drawable","com.example.startactivity");
            //给imageview设置图片
            iv_frame.setBackgroundResource(imgid);
        }
    }//当手势抬起时数字资源图片倒退显示 jlodimage() 尾部

    private synchronized void lodimagep(int j) {    // lodimagep()头部方法
        i = j;

        if (i < 25) {
            String name = "on1_" + i;
            //获取图片资源id
            int imgid = getResources().getIdentifier(name, "drawable", "com.example.startactivity");
            iv_frame.setBackgroundResource(imgid);//设置图片
            i++;
        }
        if (j == 24) {
            if (typedialog) {
                dialog();
            }
        }
    }// lodimagep()尾部方法

    protected void dialog() {
        System.out.print("110");
        typedialog = false;
        //实例化对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(OneActivity.this);
        //设置对话框文本信息
        builder.setMessage("太棒了！恭喜小仙女完成书写！");
        builder.setTitle("提示");
        //设置对话框完成按钮单击事件头部
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typedialog = true;
                finish();
            }
        });
        builder.setNegativeButton("加油，请再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                typedialog=true;
                i = 1;
                lodimagep(i);

            }
        });
        builder.create().show();
    }


   }//OneActivity尾部
