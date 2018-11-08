package com.quiz.jodacampabloski.quiz;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SavePuntuation extends AppCompatActivity {
    int Score;
    Gson JSONMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_puntuation);
        JSONMapper = new Gson();
        Score = getIntent().getIntExtra("Score",0);
        if(Score<0){
            Score=0;
        }
        TextView t = findViewById(R.id.NameText);
        String newText = ""+Score;
        t.setText(newText);
    }


    @Override
    public void onBackPressed(){
        Toast t = Toast.makeText(this,getText(R.string.backscore),Toast.LENGTH_SHORT);
        t.show();
        DontSave(null);
    }

    public void Save(View v){
        if(Score>MainPage.actualProfile.maxScore) {
            MainPage.actualProfile.maxScore=Score;
            DataBaseManager.Instance.getWritableDatabase().update(Profile.ProfileSql.TABLE_NAME,MainPage.actualProfile.toSQLValue(),
                    Profile.ProfileSql._ID+ "="+MainPage.actualProfile.id,null);
        }
        else
            {
                Toast t = Toast.makeText(this,getText(R.string.notStore),Toast.LENGTH_SHORT);
                t.show();
        }
        finish();

    }

    public void DontSave(View v){
        finish();
    }
}
