package com.example.quanganhpham.ailatrieuphu;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class Question {
    private String mContent;
    private String mCaseA;
    private String mCaseB;
    private String mCaseC;
    private String mCaseD;
    private int mTrueCase;
    private int mId;
    private String mLevel;

    public Question(String mContent,
                    String mCaseA,
                    String mCaseB,
                    String mCaseC,
                    String mCaseD,
                    int mTrueCase,
                    String mLevel) {
        this.mContent = mContent;
        this.mCaseA = mCaseA;
        this.mCaseB = mCaseB;
        this.mCaseC = mCaseC;
        this.mCaseD = mCaseD;
        this.mTrueCase = mTrueCase;
        this.mId = mId;
        this.mLevel = mLevel;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getCaseA() {
        return mCaseA;
    }

    public void setCaseA(String mCaseA) {
        this.mCaseA = mCaseA;
    }

    public String getCaseB() {
        return mCaseB;
    }

    public void setCaseB(String mCaseB) {
        this.mCaseB = mCaseB;
    }

    public String getCaseC() {
        return mCaseC;
    }

    public void setCaseC(String mCaseC) {
        this.mCaseC = mCaseC;
    }

    public String getCaseD() {
        return mCaseD;
    }

    public void setCaseD(String mCaseD) {
        this.mCaseD = mCaseD;
    }

    public int getTrueCase() {
        return mTrueCase;
    }

    public void setTrueCase(int mTrueCase) {
        this.mTrueCase = mTrueCase;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String mLevel) {
        this.mLevel = mLevel;
    }
}