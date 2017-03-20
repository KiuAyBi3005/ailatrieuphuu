package com.example.quanganhpham.ailatrieuphu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class PlayActivity extends Activity implements View.OnClickListener, ITimeOutDialog, IGameOverDialog {

    private static final String TAG = "PlayActivity";
    private static final int ANSWER_TRUE = 1;
    private static final int ANSWER_WRONG = 2;
    private static final int UPDATE_TIME_PLAY = 3;
    private static final int SHOW_DIALOG_TIME_OUT = 4;
    private static final String SCORE = "your_score";

    private TextView textLevel;
    private TextView textQuestion;
    private TextView textCaseA;
    private TextView textCaseB;
    private TextView textCaseC;
    private TextView textcaseD;
    private TextView textTime;
    private Button buttonPhone;
    private Button button5050;
    private Button buttonPeople;

    private MediaPlayer mBackgroundMusic;
    private MediaPlayer mMedia;
    private MediaPlayer mChoiceMedia;
    private MediaPlayer mAnswerMedia;
    private MediaPlayer mHelpMedia;
    private MediaPlayer mOutOfTime;

    private int mTrueCase;
    private int mLevel = 0;
    private int mYourChoice;
    private int mTimePlay;
    private boolean mIsPause;
    private int score;
    private Score mScore;
    private TimeOutDialog mTimeOutDialog;
    private GameOverDialog mGameOverDialog;
    private Random mRandom;

    private Animation mBlinkAnswer;
    private Animation mBlinkAnswerFalse;
    private Animation mBlinkWaitAnswer;
    private Handler mHandler;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);
        initView();
        setNewQuestion(mLevel);
        textCaseA.setOnClickListener(this);
        textCaseB.setOnClickListener(this);
        textCaseC.setOnClickListener(this);
        textcaseD.setOnClickListener(this);
        initComponent();
    }

    //Ánh xạ và tạo cơ sở dữ liệu cho Game
    private void initView() {
        textLevel = (TextView) findViewById(R.id.text_level);
        textQuestion = (TextView) findViewById(R.id.text_question);
        textCaseA = (TextView) findViewById(R.id.text_caseA);
        textCaseB = (TextView) findViewById(R.id.text_caseB);
        textCaseC = (TextView) findViewById(R.id.text_caseC);
        textcaseD = (TextView) findViewById(R.id.text_caseD);
        textTime = (TextView) findViewById(R.id.text_clock);
        buttonPhone = (Button) findViewById(R.id.button_phone);
        button5050 = (Button) findViewById(R.id.button_50_50);
        buttonPeople = (Button) findViewById(R.id.button_people);
        mTimeOutDialog = new TimeOutDialog(this);
        mGameOverDialog = new GameOverDialog(this);

        mBlinkAnswer = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        mBlinkAnswerFalse = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_false);
        mBlinkWaitAnswer = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_wait_answer);
        mBlinkAnswer.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                setNewQuestion(mLevel);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBlinkAnswerFalse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mOutOfTime != null) {
                    mOutOfTime.release();
                    mOutOfTime = MediaPlayer.create(PlayActivity.this, R.raw.out_of_time);
                } else {
                    mOutOfTime = MediaPlayer.create(PlayActivity.this, R.raw.out_of_time);
                }
                mOutOfTime.start();
                mGameOverDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                mGameOverDialog.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        buttonPhone.setOnClickListener(this);
        button5050.setOnClickListener(this);
        buttonPeople.setOnClickListener(this);

        /*
        Connect to database
         */
        DatabaseManager databaseManager = new DatabaseManager(this);
        try {
            databaseManager.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionList = databaseManager.get15Question();
    }

    private void initComponent() {
        mTimeOutDialog.setCanceledOnTouchOutside(false);
        mTimeOutDialog.setiTimeOutDialog(this);
        mGameOverDialog.setiGameOverDialog(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case ANSWER_TRUE:
                        mLevel += 1;
                        sleepTime(3000);
                        animationTrueAnswer(mTrueCase);
                        break;
                    case ANSWER_WRONG:
                        sleepTime(3000);
                        animationFalseAnswer(mTrueCase, mYourChoice);
                        break;
                    case UPDATE_TIME_PLAY:
                        textTime.setText(message.arg1 + "");
                        break;
                    case SHOW_DIALOG_TIME_OUT:
                        if (mOutOfTime != null) {
                            mOutOfTime.release();
                            mOutOfTime = MediaPlayer.create(PlayActivity.this, R.raw.out_of_time);
                        } else {
                            mOutOfTime = MediaPlayer.create(PlayActivity.this, R.raw.out_of_time);
                        }
                        mOutOfTime.start();
                        mTimeOutDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        mTimeOutDialog.show();
                        break;
                }
            }
        };
    }

    public void setNewQuestion(int level) {
        //Level trong khoảng 0-14 nên hiển thị tên câu hỏi phải cộng thêm 1;
        int levels = level + 1;
        //Chuyển đổi sang dạng String để setText cho textView hiển thị câu hỏi
        String levelsContent = Integer.toString(levels);

        mediaOption(level);
        mIsPause = false;
        textLevel.setText("Câu " + levelsContent);
        textTime.setText("30");
        textQuestion.setText(questionList.get(level).getContent());
        textCaseA.setText("A. " + questionList.get(level).getCaseA());
        textCaseB.setText("B. " + questionList.get(level).getCaseB());
        textCaseC.setText("C. " + questionList.get(level).getCaseC());
        textcaseD.setText("D. " + questionList.get(level).getCaseD());
        textTime.setText("30");
        setTimePlay();
        mTrueCase = questionList.get(level).getTrueCase();

        textCaseA.setBackgroundResource(R.drawable.answer);
        textCaseB.setBackgroundResource(R.drawable.answer);
        textCaseC.setBackgroundResource(R.drawable.answer);
        textcaseD.setBackgroundResource(R.drawable.answer);
        textCaseA.setClickable(true);
        textCaseB.setClickable(true);
        textCaseC.setClickable(true);
        textcaseD.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.text_caseA:
                clickCaseA();
                processAnswer();
                break;
            case R.id.text_caseB:
                clickCaseB();
                processAnswer();
                break;
            case R.id.text_caseC:
                clickCaseC();
                processAnswer();
                break;
            case R.id.text_caseD:
                clickCaseD();
                processAnswer();
                break;
            case R.id.button_phone:
                clickButtonPhone();
                break;
            case R.id.button_50_50:
                clickButton5050();
                help5050(mTrueCase);
                break;
            case R.id.button_people:
                clickButtonPepple();
                break;
        }
    }

    private void clickCaseA() {
        if (mChoiceMedia != null) {
            mChoiceMedia.release();
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_a);
        } else {
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_a);
        }
        mChoiceMedia.start();
        mIsPause = true;
        textCaseA.setBackgroundResource(R.drawable.your_answer);
        textCaseA.setAnimation(mBlinkWaitAnswer);
        mYourChoice = 1;
        textCaseB.setClickable(false);
        textCaseC.setClickable(false);
        textcaseD.setClickable(false);
    }

    private void clickCaseB() {
        if (mChoiceMedia != null) {
            mChoiceMedia.release();
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_b);
        } else {
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_b);
        }
        mChoiceMedia.start();
        mIsPause = true;
        textCaseB.setBackgroundResource(R.drawable.your_answer);
        mYourChoice = 2;
        textCaseA.setClickable(false);
        textCaseC.setClickable(false);
        textcaseD.setClickable(false);
    }

    private void clickCaseC() {
        if (mChoiceMedia != null) {
            mChoiceMedia.release();
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_c);
        } else {
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_c);
        }
        mChoiceMedia.start();
        mIsPause = true;
        textCaseC.setBackgroundResource(R.drawable.your_answer);
        mYourChoice = 3;
        textCaseB.setClickable(false);
        textCaseA.setClickable(false);
        textcaseD.setClickable(false);
    }

    private void clickCaseD() {
        if (mChoiceMedia != null) {
            mChoiceMedia.release();
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_d);
        } else {
            mChoiceMedia = MediaPlayer.create(PlayActivity.this, R.raw.ans_d);
        }
        mChoiceMedia.start();
        mIsPause = true;
        textcaseD.setBackgroundResource(R.drawable.your_answer);
        mYourChoice = 4;
        textCaseB.setClickable(false);
        textCaseC.setClickable(false);
        textCaseA.setClickable(false);
        textcaseD.setAnimation(mBlinkWaitAnswer);
    }

    private void clickButtonPhone() {
        if (mHelpMedia != null) {
            mHelpMedia.release();
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.help_callb);
        } else {
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.help_callb);
        }
        mHelpMedia.start();

        CallMeDialog callMe = new CallMeDialog(PlayActivity.this, mTrueCase);
        callMe.setCanceledOnTouchOutside(false);
        callMe.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        callMe.show();
        buttonPhone.setBackgroundResource(R.drawable.help_call_disable);
        buttonPhone.setClickable(false);
    }

    private void clickButton5050() {
        if (mHelpMedia != null) {
            mHelpMedia.release();
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.sound5050);
        } else {
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.sound5050);
        }
        mHelpMedia.start();
        I5050Dialog i5050dialog = new I5050Dialog(PlayActivity.this);
        i5050dialog.setCanceledOnTouchOutside(false);
        i5050dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        i5050dialog.show();
        button5050.setBackgroundResource(R.drawable.help_50_50_disable);
        button5050.setClickable(false);
    }

    private void clickButtonPepple() {
        if (mHelpMedia != null) {
            mHelpMedia.release();
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.khan_gia);
        } else {
            mHelpMedia = MediaPlayer.create(PlayActivity.this, R.raw.khan_gia);
        }
        mHelpMedia.start();
        HelpFromViewersDialog helpFromViewersDialog = new HelpFromViewersDialog(PlayActivity.this, mTrueCase);
        helpFromViewersDialog.setCanceledOnTouchOutside(false);
        helpFromViewersDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        helpFromViewersDialog.show();
        buttonPeople.setClickable(false);
        buttonPeople.setBackgroundResource(R.drawable.help_people_disable);
    }

    private void help5050(int trueCase) {
        switch (trueCase) {
            case 1:
                textCaseC.setText("");
                textcaseD.setText("");
                textCaseC.setClickable(false);
                textcaseD.setClickable(false);
                break;
            case 2:
                textCaseA.setText("");
                textcaseD.setText("");
                textCaseA.setClickable(false);
                textcaseD.setClickable(false);
                break;
            case 3:
                textCaseA.setText("");
                textcaseD.setText("");
                textCaseA.setClickable(false);
                textcaseD.setClickable(false);
                break;
            case 4:
                textCaseA.setText("");
                textCaseB.setText("");
                textCaseA.setClickable(false);
                textCaseB.setClickable(false);
                break;
        }

    }

    private void animationTrueAnswer(int trueAnswer) {
        switch (trueAnswer) {
            case 1:
                textCaseA.setBackgroundResource(R.drawable.correct_answer);
                textCaseA.setAnimation(mBlinkAnswer);
                textCaseA.startAnimation(mBlinkAnswer);
                break;
            case 2:
                textCaseB.setBackgroundResource(R.drawable.correct_answer);
                textCaseB.setAnimation(mBlinkAnswer);
                textCaseB.startAnimation(mBlinkAnswer);
                break;
            case 3:
                textCaseC.setBackgroundResource(R.drawable.correct_answer);
                textCaseC.setAnimation(mBlinkAnswer);
                textCaseC.startAnimation(mBlinkAnswer);
                break;
            case 4:
                textcaseD.setBackgroundResource(R.drawable.correct_answer);
                textcaseD.setAnimation(mBlinkAnswer);
                textcaseD.startAnimation(mBlinkAnswer);
                break;
        }
        if (mAnswerMedia != null) {
            mAnswerMedia.release();
            switch (trueAnswer) {
                case 1:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_a);
                    break;
                case 2:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_b);
                    break;
                case 3:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_c);
                    break;
                case 4:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_d2);
                    break;
            }
        } else {
            switch (trueAnswer) {
                case 1:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_a);
                    break;
                case 2:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_b);
                    break;
                case 3:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_c);
                    break;
                case 4:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.true_d2);
                    break;
            }
        }
        mAnswerMedia.start();
    }

    private void animationFalseAnswer(int trueAnswer, int yourAnswer) {
        switch (trueAnswer) {
            case 1:
                textCaseA.setBackgroundResource(R.drawable.correct_answer);
                textCaseA.setAnimation(mBlinkAnswerFalse);
                textCaseA.startAnimation(mBlinkAnswerFalse);
                break;
            case 2:
                textCaseB.setBackgroundResource(R.drawable.correct_answer);
                textCaseB.setAnimation(mBlinkAnswerFalse);
                textCaseB.startAnimation(mBlinkAnswerFalse);
                break;
            case 3:
                textCaseC.setBackgroundResource(R.drawable.correct_answer);
                textCaseC.setAnimation(mBlinkAnswerFalse);
                textCaseC.startAnimation(mBlinkAnswerFalse);
                break;
            case 4:
                textcaseD.setBackgroundResource(R.drawable.correct_answer);
                textcaseD.setAnimation(mBlinkAnswerFalse);
                textcaseD.startAnimation(mBlinkAnswerFalse);
                break;
        }
        switch (yourAnswer) {
            case 1:
                textCaseA.setBackgroundResource(R.drawable.wrong_answer);
                break;
            case 2:
                textCaseB.setBackgroundResource(R.drawable.wrong_answer);
                break;
            case 3:
                textCaseC.setBackgroundResource(R.drawable.wrong_answer);
                break;
            case 4:
                textcaseD.setBackgroundResource(R.drawable.wrong_answer);
                break;
        }
        if (mAnswerMedia != null) {
            mAnswerMedia.release();
            switch (trueAnswer) {
                case 1:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_a2);
                    break;
                case 2:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_b);
                    break;
                case 3:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_c2);
                    break;
                case 4:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_d);
                    break;
            }
        } else {
            switch (trueAnswer) {
                case 1:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_a2);
                    break;
                case 2:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_b);
                    break;
                case 3:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_c2);
                    break;
                case 4:
                    mAnswerMedia = MediaPlayer.create(PlayActivity.this, R.raw.lose_d);
                    break;
            }
        }
        mAnswerMedia.start();
    }

    private void processAnswer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mYourChoice == mTrueCase) {
                    Message msg = new Message();
                    msg.what = ANSWER_TRUE;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                } else {
                    Message msg = new Message();
                    msg.what = ANSWER_WRONG;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                }
            }
        }).start();
    }

    private void sleepTime(int mili) {
        SystemClock.sleep(mili);
    }

    private void setTimePlay() {
        mTimePlay = 30;
        mIsPause = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mTimePlay > 0 && !mIsPause) {
                    sleepTime(1000);
                    mTimePlay--;
                    Message msg = new Message();
                    msg.what = UPDATE_TIME_PLAY;
                    msg.arg1 = mTimePlay;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                }
                if (!mIsPause) {
                    Log.i(TAG, "Time over");
                    Message msg = new Message();
                    msg.what = SHOW_DIALOG_TIME_OUT;
                    msg.setTarget(mHandler);
                    msg.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void setFinishGameOver() {
        mBackgroundMusic.release();
        Intent mIntent = new Intent(PlayActivity.this, GameOverActivity.class);
        mScore = new Score(mLevel, false);
        score = mScore.returnScore();
        mIntent.putExtra(SCORE, score);
        startActivity(mIntent);
        PlayActivity.this.finish();
    }

    private void mediaOption(int level) {
        if (level == 0) {
            if (level == 0) {
                mBackgroundMusic = new MediaPlayer().create(PlayActivity.this, R.raw.background_music);
                mBackgroundMusic.start();
                mBackgroundMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        PlayActivity.this.mBackgroundMusic = new MediaPlayer().create(PlayActivity.this, R.raw.connan_one);
                        mBackgroundMusic.setLooping(true);
                        mBackgroundMusic.start();
                    }
                });
            }
            mMedia = MediaPlayer.create(this, R.raw.ques1);
            mMedia.start();
        } else {
            mMedia.release();
            mMedia = null;
        }
        if (level > 4) {
            mBackgroundMusic.release();
            mBackgroundMusic = MediaPlayer.create(PlayActivity.this, R.raw.background_music_b);
            mBackgroundMusic.start();
            mBackgroundMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    PlayActivity.this.mBackgroundMusic = new MediaPlayer().create(PlayActivity.this, R.raw.connan_one);
                    mBackgroundMusic.setLooping(true);
                    mBackgroundMusic.start();
                }
            });
        }
        mRandom = new Random();
        int randomNumber = mRandom.nextInt(2);
        switch (level + 1) {
            case 2:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques2);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques2_b);
                    mMedia.start();
                }
                break;
            case 3:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques3);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques3_b);
                    mMedia.start();
                }
                break;
            case 4:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques4);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques4_b);
                    mMedia.start();
                }
                break;
            case 5:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques5);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques5_b);
                    mMedia.start();
                }
                break;
            case 6:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques6);
                    mMedia.start();
                }
                break;
            case 7:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques7);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques7_b);
                    mMedia.start();
                }
                break;
            case 8:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques8);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques8_b);
                    mMedia.start();
                }
                break;
            case 9:
                if (randomNumber % 2 == 0) {
                    mMedia = MediaPlayer.create(this, R.raw.ques9);
                    mMedia.start();
                } else {
                    mMedia = MediaPlayer.create(this, R.raw.ques9_b);
                    mMedia.start();
                }
                break;
            case 10:
                mMedia = MediaPlayer.create(this, R.raw.ques10);
                mMedia.start();
                break;
            case 11:
                mMedia = MediaPlayer.create(this, R.raw.ques11);
                mMedia.start();
                break;
            case 12:
                mMedia = MediaPlayer.create(this, R.raw.ques12);
                mMedia.start();
                break;
            case 13:
                mMedia = MediaPlayer.create(this, R.raw.ques13);
                mMedia.start();
                break;
            case 14:
                mMedia = MediaPlayer.create(this, R.raw.ques14);
                mMedia.start();
                break;
            case 15:
                mMedia = MediaPlayer.create(this, R.raw.ques15);
                mMedia.start();
                break;
        }


    }

    @Override
    public void setFinish() {
        Intent mIntent = new Intent(PlayActivity.this, GameOverActivity.class);
        mScore = new Score(mLevel, false);
        score = mScore.returnScore();
        mIntent.putExtra(SCORE, score);
        mBackgroundMusic.release();
        mMedia.release();
        startActivity(mIntent);
        PlayActivity.this.finish();
    }
}
