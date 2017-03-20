package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ReadyActivity extends Activity implements IReadyDialog {

    private static final int UPDATE_BACKGROUND = 0;
    private static final int SHOW_DIALOG = 1;
    private TextView text5;
    private TextView text10;
    private long startTime;
    private boolean isSkip = false;
    private TextView text15;
    private ReadyDialog mReadyDialog;
    private MediaPlayer mMediaPlayer;
    private LinearLayout mReadyLayout;
    private Animation mAnimation;
    private Thread mThread;
    private Handler mHandler;
    private Random mRandom;
    private MediaPlayer mTouchSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ready_layout);
        init();
        initComponents();
        mReadyDialog = new ReadyDialog(this);
        mReadyDialog.setiReadyDialog(this);
        mReadyDialog.setCanceledOnTouchOutside(false);

    }

    private void initComponents() {
        mMediaPlayer.start();
        mThread.start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_BACKGROUND:
                        int arg = msg.arg1;
                        switch (arg) {
                            case 5:
                                text5.setBackgroundResource(R.drawable.money_2);
                                break;
                            case 10:
                                text10.setBackgroundResource(R.drawable.money_2);
                                text5.setBackgroundResource(R.drawable.money_3);
                                break;
                            case 15:
                                text15.setBackgroundResource(R.drawable.money_2);
                                text10.setBackgroundResource(R.drawable.money_3);
                                text5.setBackgroundResource(R.drawable.money_3);
                                break;
                        }
                        break;
                    case SHOW_DIALOG:
                        mReadyDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        mReadyDialog.show();
                        mRandom = new Random();
                        int audio = mRandom.nextInt(2);
                        mMediaPlayer.release();
                        if (audio % 2 == 0) {
                            mMediaPlayer = MediaPlayer.create(ReadyActivity.this, R.raw.ready_b);
                        } else {
                            mMediaPlayer = MediaPlayer.create(ReadyActivity.this, R.raw.ready_c);
                        }
                        mMediaPlayer.start();

                        break;
                }
            }
        };
    }

    public void init() {
        text5 = (TextView) findViewById(R.id.text_money05);
        text10 = (TextView) findViewById(R.id.text_money10);
        text15 = (TextView) findViewById(R.id.text_money15);
        startTime = System.currentTimeMillis();
        mReadyLayout = (LinearLayout) findViewById(R.id.ready_layout);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_to_right_bg);
        mAnimation.start();
        mReadyLayout.setAnimation(mAnimation);
        mMediaPlayer = MediaPlayer.create(this, R.raw.luatchoi_c);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() - startTime <= 6000 && isSkip == false) {
                    if (System.currentTimeMillis() - startTime == 4500) {
                        Message message = new Message();
                        message.what = UPDATE_BACKGROUND;
                        message.arg1 = 5;
                        message.setTarget(mHandler);
                        message.sendToTarget();
                    }
                    if (System.currentTimeMillis() - startTime == 4800) {
                        Message message = new Message();
                        message.what = UPDATE_BACKGROUND;
                        message.arg1 = 10;
                        message.setTarget(mHandler);
                        message.sendToTarget();
                    }
                    if (System.currentTimeMillis() - startTime == 5200) {
                        Message message = new Message();
                        message.what = UPDATE_BACKGROUND;
                        message.arg1 = 15;
                        message.setTarget(mHandler);
                        message.sendToTarget();
                    }
                }
                Message message = new Message();
                message.what = SHOW_DIALOG;
                message.setTarget(mHandler);
                message.sendToTarget();
            }
        });
    }

    @Override
    public void setClickBtnNo() {
        mMediaPlayer.release();
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
        this.finish();
    }

    @Override
    public void setClickBtnYes() {
        if (mTouchSound != null) {
            mTouchSound.release();
            mTouchSound = MediaPlayer.create(ReadyActivity.this, R.raw.touch_sound);
        } else {
            mTouchSound = MediaPlayer.create(ReadyActivity.this, R.raw.touch_sound);
        }
        mTouchSound.start();
        mMediaPlayer.release();
        Intent mIntent = new Intent(this, LoadingActivity.class);
        startActivity(mIntent);
        this.finish();
    }
}