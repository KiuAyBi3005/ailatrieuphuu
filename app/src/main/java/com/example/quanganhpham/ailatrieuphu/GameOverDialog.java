package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class GameOverDialog extends Dialog implements View.OnClickListener{
    private Button buttonGameOver;
    private IGameOverDialog mIGameOverDialog;

    public GameOverDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_over_dialog_layout);
        buttonGameOver = (Button) findViewById(R.id.button_ok_game_over);
       buttonGameOver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mIGameOverDialog.setFinishGameOver();
    }

    public void setiGameOverDialog(IGameOverDialog iGameOverDialog) {
        this.mIGameOverDialog = iGameOverDialog;
    }
}
