package com.example.quanganhpham.ailatrieuphu;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

 public class ExitDialog extends Dialog implements View.OnClickListener{
    public ExitDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.exit_dialog_layout);
        findViewById(R.id.button_exit_game).setOnClickListener(this);
        findViewById(R.id.button_no_exit_game).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_exit_game:
                System.exit(0);
                break;
            case R.id.button_no_exit_game:
                dismiss();
                break;
        }
    }
}
