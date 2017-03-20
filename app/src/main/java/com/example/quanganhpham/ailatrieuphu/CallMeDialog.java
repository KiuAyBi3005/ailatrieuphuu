package com.example.quanganhpham.ailatrieuphu;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CallMeDialog extends Dialog implements View.OnClickListener {
     private int mTrueCase;

    public CallMeDialog(Context context, int trueCase) {
        super(context);
        this.mTrueCase = trueCase;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.call_me_dialog_layout);
        TextView textHelpCall = (TextView) findViewById(R.id.text_answer_call_me);
        switch (mTrueCase) {
            case 1:
                textHelpCall.setText("Theo sự nghiên học đại học bôn ba của tôi thì, đáp án đúng là A");
                break;
            case 2:
                textHelpCall.setText("Theo sự nghiên học đại học bôn ba của tôi thì, đáp án đúng là B");
                break;
            case 3:
                textHelpCall.setText("Theo sự nghiên học đại học bôn ba của tôi thì, đáp án đúng là C");
                break;
            case 4:
                textHelpCall.setText("Theo sự nghiên học đại học bôn ba cảu tôi, đáp án đúng là D");
                break;
        }
        findViewById(R.id.button_ok_call_me).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok_call_me:
                dismiss();
                break;
        }
    }
}
