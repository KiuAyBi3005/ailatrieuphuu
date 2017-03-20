package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class HelpFromViewersDialog extends Dialog implements View.OnClickListener {

     private AudienceFeedbackDialog mAudience;
     private int mTrueCase;
     private Context mContext;

    public HelpFromViewersDialog(Context context, int trueCase) {
        super(context);
        this.mContext = context;
        this.mTrueCase = trueCase;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.help_audience_dialog);
        findViewById(R.id.button_ok_help_from_audience).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok_help_from_audience:
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mAudience = new AudienceFeedbackDialog(mContext, mTrueCase);
                mAudience.setCanceledOnTouchOutside(false);
                mAudience.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                mAudience.show();
                HelpFromViewersDialog.this.dismiss();
                break;
        }
    }
}
