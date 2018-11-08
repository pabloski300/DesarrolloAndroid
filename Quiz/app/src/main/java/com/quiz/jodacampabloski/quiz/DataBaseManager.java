package com.quiz.jodacampabloski.quiz;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class DataBaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Quiz.db";
    Gson JSONMapper;
    Context c = null;
    public static  DataBaseManager Instance = null;
    private static boolean created = false;

    public static DataBaseManager CreateInstance(Context context){

        DataBaseManager.Instance = new DataBaseManager(context);
        DataBaseManager.created = true;
        return Instance;
    }



    private DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Question.QuestionEntry.CREATE_DATA_TABLE);
        db.execSQL(Profile.ProfileSql.CREATE_TABLE);
        JSONMapper = new Gson();
        InputStream reader = c.getResources().openRawResource(R.raw.questions);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String[] results = result.split("//");
        List<Question> questions;
        questions = new ArrayList<>();
        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[0],TextQuestion[].class)));
        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[1],ImageQuestion[].class)));
        for(Question i: questions){
            db.insert(Question.QuestionEntry.TABLE_NAME,"",i.QuestionValue());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Question.QuestionEntry.DELETE_TABLE);
        db.execSQL(Profile.ProfileSql.DELETE_TABLE);
        onCreate(db);
    }
}
