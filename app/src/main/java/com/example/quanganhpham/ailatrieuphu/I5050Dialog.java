package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class I5050Dialog extends Dialog implements View.OnClickListener {

    public I5050Dialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_50_50);
        findViewById(R.id.button_50_50).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_50_50:
                dismiss();
                break;
        }
    }
}
