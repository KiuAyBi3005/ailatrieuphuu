package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.util.Random;

public class ListQestionActivity extends Activity {

    private LinearLayout mLinearLayout;
    private Animation mAnimation;
    private MediaPlayer mMedia;
    private Random random;
    private MediaPlayer mBackgroundMusic;
    private long mStartTime;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_question_layout);
        initView();
    }

    private void initView() {
        mStartTime = System.currentTimeMillis();

    }


    private void mediaOption(int level){
        if (level == 0){
           mBackgroundMusic = MediaPlayer.create(this, R.raw.background_music);
            mBackgroundMusic.setLooping(true);
            mMedia =  MediaPlayer.create(this, R.raw.ques1);
            mBackgroundMusic.start();
            mMedia.start();
        }else {
            mBackgroundMusic.release();
            mMedia.release();
        }
        if(level < 5){
           mBackgroundMusic = MediaPlayer.create(this, R.raw.background_music);
           mBackgroundMusic.setLooping(true);
           mBackgroundMusic.start();
        }
        random = new Random();
        int randomNumber = random.nextInt(2);
        switch (level+1){
            case 1:
                if(randomNumber%2==0){
                    mMedia = MediaPlayer.create(this, R.raw.ques1);
                }else {
                   mMedia = MediaPlayer.create(this, R.raw.ques1_b);
                }
                break;
            case 2:
                if(randomNumber%2==0){
                   mMedia= MediaPlayer.create(this, R.raw.ques2);
                }else {
                    mMedia= MediaPlayer.create(this, R.raw.ques2_b);
                }
                break;
            case 3:
                if(randomNumber%2==0){
                    mMedia = MediaPlayer.create(this, R.raw.ques3);
                }else {
                    mMedia = MediaPlayer.create(this, R.raw.ques3_b);
                }
                break;
            case 4:
                if(randomNumber%2==0){
                   mMedia = MediaPlayer.create(this, R.raw.ques4);
                }else {
                    mMedia= MediaPlayer.create(this, R.raw.ques4_b);
                }
                break;
            case 5:
                if(randomNumber%2==0){
                   mMedia = MediaPlayer.create(this, R.raw.ques5);
                }else {
                    mMedia = MediaPlayer.create(this, R.raw.ques5_b);
                }
                break;
            case 6:
                if(randomNumber%2==0) {
                   mMedia= MediaPlayer.create(this, R.raw.ques6);
                }
                break;
            case 7:
                if(randomNumber%2==0){
                    mMedia = MediaPlayer.create(this, R.raw.ques7);
                }else {
                   mMedia= MediaPlayer.create(this, R.raw.ques7_b);
                }
                break;
            case 8:
                if(randomNumber%2==0){
                    mMedia = MediaPlayer.create(this, R.raw.ques8);
                }else {
                    mMedia = MediaPlayer.create(this, R.raw.ques8_b);
                }
                break;
            case 9:
                if(randomNumber%2==0){
                   mMedia = MediaPlayer.create(this, R.raw.ques9);
                }else {
                   mMedia = MediaPlayer.create(this, R.raw.ques9_b);
                }
                break;
            case 10:
                mMedia = MediaPlayer.create(this, R.raw.ques10);
                break;
            case 11:
                mMedia = MediaPlayer.create(this, R.raw.ques11);
                break;
            case 12:
                mMedia = MediaPlayer.create(this, R.raw.ques12);
                break;
            case 13:
                mMedia = MediaPlayer.create(this, R.raw.ques13);
                break;
            case 14:
                mMedia = MediaPlayer.create(this, R.raw.ques14);
                break;
            case 15:
                mMedia = MediaPlayer.create(this, R.raw.ques15);
                break;
        }
        mMedia.start();
    }
}

