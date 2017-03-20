package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

public class ReadyDialog extends Dialog implements View.OnClickListener {

    private IReadyDialog mIReadyDialog;

    public ReadyDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ready_dialog_layout);
        findViewById(R.id.button_yes_ready).setOnClickListener(this);
        findViewById(R.id.button_no_ready).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_yes_ready:
                mIReadyDialog.setClickBtnYes();
                break;
            case R.id.button_no_ready:
                mIReadyDialog.setClickBtnNo();
                break;

        }
        dismiss();
    }

    public void setiReadyDialog(IReadyDialog iReadyDialog) {
        this.mIReadyDialog = iReadyDialog;
    }
}
