package com.example.quanganhpham.ailatrieuphu;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class Score {
    private int mScore;
    private int mLevel;
    private boolean checkStopGame;
    public Score(int level, boolean checkStopGame){
        this.mLevel = level;
        this.checkStopGame = checkStopGame;
    }
    public int returnScore(){
        if(checkStopGame){
            switch (mLevel){
                case 0:
                    mScore = 0;
                    break;
                case 1:
                    mScore= 200000;
                    break;
                case 2:
                    mScore = 400000;
                    break;
                case 3:
                    mScore = 600000;
                    break;
                case 4:
                    mScore= 1000000;
                    break;
                case 5:
                    mScore= 2000000;
                    break;
                case 6:
                    mScore = 3000000;
                    break;
                case 7:
                    mScore = 6000000;
                    break;
                case 8:
                    mScore = 10000000;
                    break;
                case 9:
                    mScore = 14000000;
                    break;
                case 10:
                    mScore= 22000000;
                    break;
                case 11:
                    mScore = 30000000;
                    break;
                case 12:
                    mScore= 40000000;
                    break;
                case 13:
                    mScore = 60000000;
                    break;
                case 14:
                    mScore = 85000000;
                    break;
                case 15:
                    mScore = 150000000;
                    break;
            }
        } else if(mLevel < 5){
            mScore = 0;
        } else if(mLevel >= 5 &&mLevel < 10){
            mScore = 2000000;
        } else if(mLevel >=10 && mLevel< 15){
            mScore = 22000000;
        } else if (mLevel ==15){
            mScore = 150000000;
        }
        return mScore;
    }



}