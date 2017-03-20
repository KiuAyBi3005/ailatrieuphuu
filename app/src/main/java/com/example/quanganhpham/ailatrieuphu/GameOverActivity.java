package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity implements View.OnClickListener {

    private static final String SCORE = "your_score";
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);
        Intent intent = getIntent();
        int score = intent.getIntExtra(SCORE,0);
        TextView textMessageGameOver = (TextView) findViewById(R.id.text_message_game_over);
        textMessageGameOver.setText("Bạn ra về với số tiền " + score + "VND");

        if(mMediaPlayer!= null){
            mMediaPlayer.release();
            mMediaPlayer = MediaPlayer.create(this, R.raw.lose);
        }else {
            mMediaPlayer= MediaPlayer.create(this, R.raw.lose);
        }
        mMediaPlayer.start();
        Button buttonOutGame = (Button) findViewById(R.id.button_out_game);
        Button buttonRestart = (Button) findViewById(R.id.button_restart);
        buttonRestart.setOnClickListener(this);
      buttonOutGame.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_out_game:
                finish();
                break;
            case R.id.button_restart:
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                GameOverActivity.this.finish();
                mMediaPlayer.release();
                break;
        }
    }
}
