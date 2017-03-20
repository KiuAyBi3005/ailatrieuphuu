package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoadingActivity extends Activity {

    private static final int UPDATE_UI = 1;
    private LinearLayout loadingLayout;
    private Animation mAnimation;
    private ImageView mImageRotateLoading;
    private MediaPlayer mMediaPlayer;
    private Thread mThread;
    private Handler mHandler;
    private long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        initViews();
        initComponents();
    }

    private void initComponents() {
        mThread.start();

    }

    private void initViews() {

        loadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_to_right_bg);
        loadingLayout.setAnimation(mAnimation);
        mAnimation.start();

        mImageRotateLoading = (ImageView) findViewById(R.id.image_loading);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);
        mImageRotateLoading.setAnimation(mAnimation);
        mAnimation.start();

        mMediaPlayer = MediaPlayer.create(this, R.raw.gofind);
        mMediaPlayer.start();

        mStartTime = System.currentTimeMillis();
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (System.currentTimeMillis() - mStartTime == 5500) {
                        Intent mIntent = new Intent(LoadingActivity.this, PlayActivity.class);
                        startActivity(mIntent);
                        LoadingActivity.this.finish();
                    }
                }
            }
        });
    }
}
