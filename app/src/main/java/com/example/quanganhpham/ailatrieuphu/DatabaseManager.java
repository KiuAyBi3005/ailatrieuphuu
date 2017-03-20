package com.example.quanganhpham.ailatrieuphu;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by QuangAnhPham on 3/19/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseManager";
    private static String DATABASE_PATH = "/data/data/com.example.quanganhpham.ailatrieuphu/databases/";
    private static String DATABASE_NAME = "ailatrieuphuData.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Question";
    private static final String QUESTION_COLUMN = "question";
    private static final String LEVEL_COLUMN = "level";
    private static final String CASE_A_COLUMN = "casea";
    private static final String CASE_B_COLUMN = "caseb";
    private static final String CASE_C_COLUMN = "casec";
    private static final String CASE_D_COLUMN = "cased";
    private static final String TRUE_CASE_COLUMN = "truecase";

    private SQLiteDatabase database;
    private final Context mContext;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.mContext = context;
    }

    public void openDatabase() throws SQLException {

        String myPath = DATABASE_PATH + DATABASE_NAME;
        database = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READONLY);
    }

    //Check xem database có null hay không
    public boolean checkDatabase(){

        SQLiteDatabase checkDB = null;
        String myPath =DATABASE_PATH + DATABASE_NAME;

        try{
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){

        }
        if(checkDB!=null){
            checkDB.close();
            return true;
        }

        //trả về true nếu không null, trả về false nếu null
        return false;

    }

    private void copyDatabase() throws IOException {
        InputStream myInput =   mContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_NAME + DATABASE_PATH;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDatabase() throws IOException{
        boolean dbExist = checkDatabase();
        if(dbExist) {
            Log.i(TAG,"khong co database");
        }
        else {

            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private Question getQuestion(String level){
        try{
            createDatabase();
        }catch (IOException e){
            e.printStackTrace();
        }
        openDatabase();
        Cursor cursor = database.query(TABLE_NAME,new String[]{QUESTION_COLUMN,CASE_A_COLUMN,CASE_B_COLUMN,CASE_C_COLUMN,CASE_D_COLUMN,TRUE_CASE_COLUMN}
                , LEVEL_COLUMN + "=?", new String[]{level}, null, null,null,null
        );
        int n = new Random().nextInt(cursor.getCount());
        if(cursor != null && cursor.moveToFirst()){
            for (int i = 0; i < n; i++) {
                cursor.moveToNext();
            }
        }
        int questionIndex = cursor.getColumnIndexOrThrow(QUESTION_COLUMN);
        int caseAIndex = cursor.getColumnIndexOrThrow(CASE_A_COLUMN);
        int caseBIndex = cursor.getColumnIndexOrThrow(CASE_B_COLUMN);
        int caseCIndex = cursor.getColumnIndexOrThrow(CASE_C_COLUMN);
        int caseDIndex = cursor.getColumnIndexOrThrow(CASE_D_COLUMN);
        int trueCaseIndex = cursor.getColumnIndexOrThrow(TRUE_CASE_COLUMN);

        Question question = new Question(cursor.getString(questionIndex), cursor.getString(caseAIndex),
                cursor.getString(caseBIndex),cursor.getString(caseCIndex),cursor.getString(caseDIndex),cursor.getInt(trueCaseIndex),level
        );
        database.close();
        return question;
    }

    public List<Question> get15Question(){
        List<Question> listQuestion = new ArrayList<Question>();
        for (int i = 1; i <16 ; i++) {
            String content = Integer.toString(i);
            Question questionContent = getQuestion(content);
            listQuestion.add(questionContent);
        }
        return listQuestion;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}