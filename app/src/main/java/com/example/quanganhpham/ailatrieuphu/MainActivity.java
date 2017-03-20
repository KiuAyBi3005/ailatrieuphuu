package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements View.OnClickListener {
    private MediaPlayer mMediaPlayer;
    private MediaPlayer mTouchSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mMediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
        mMediaPlayer.start();
        initView();
    }

    private void initView() {
        findViewById(R.id.button_start_game).setOnClickListener(this);
        findViewById(R.id.button_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_game:
                clickStart();
                break;

            case R.id.button_exit:
                clickExit();
                break;
        }
    }

    private void clickStart() {
        if (mTouchSound != null) {
            mTouchSound.release();
            mTouchSound = MediaPlayer.create(MainActivity.this, R.raw.touch_sound);
        } else {
            mTouchSound = MediaPlayer.create(MainActivity.this, R.raw.touch_sound);
        }
        mTouchSound.start();
        Intent mIntent = new Intent(MainActivity.this, ReadyActivity.class);
        startActivity(mIntent);
        MainActivity.this.finish();
    }

    private void clickInfo() {

    }

    private void clickExit() {
        ExitDialog exitDialog = new ExitDialog(MainActivity.this);
        exitDialog.setCanceledOnTouchOutside(true);
        exitDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        exitDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        mMediaPlayer.release();
    }
}
