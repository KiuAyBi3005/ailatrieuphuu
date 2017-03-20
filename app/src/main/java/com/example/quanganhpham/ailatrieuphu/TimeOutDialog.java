package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class TimeOutDialog extends Dialog implements View.OnClickListener {

    private ITimeOutDialog mITimeOutDialog;

    private Button buttonOkTimeOut;

    public TimeOutDialog(Context context) {
        super(context);

        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.time_out_dialog_layout);
        buttonOkTimeOut = (Button) findViewById(R.id.button_ok_time_out);
        buttonOkTimeOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mITimeOutDialog.setFinish();
    }

    public void setiTimeOutDialog(ITimeOutDialog iTimeOutDialog) {
        this.mITimeOutDialog = iTimeOutDialog;
    }
}

