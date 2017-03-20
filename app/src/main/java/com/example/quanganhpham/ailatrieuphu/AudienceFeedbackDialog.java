package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class AudienceFeedbackDialog extends Dialog implements View.OnClickListener {
    ImageView image;
   private int mTrueCase;

    public AudienceFeedbackDialog(Context context, int trueCase) {
        super(context);
        this.mTrueCase = trueCase;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.adience_feedback_dialog);
        findViewById(R.id.button_ok_feedback).setOnClickListener(this);
        image = (ImageView) findViewById(R.id.image_feedback);
        switch (mTrueCase) {
            case 1:
                image.setImageResource(R.drawable.audience_a);
                break;
            case 2:
                image.setImageResource(R.drawable.audience_b);
                break;
            case 3:
                image.setImageResource(R.drawable.audience_c);
                break;
            case 4:
                image.setImageResource(R.drawable.audience_d);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok_feedback:
                dismiss();
                break;
        }
    }
}

