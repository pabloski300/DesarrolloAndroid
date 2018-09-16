package com.quiz.jodacampabloski.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game extends AppCompatActivity {
    Question[] questions;
    Gson JSONMapper;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i=0;

        JSONMapper = new Gson();
        setContentView(R.layout.activity_game);
        InputStream reader = getResources().openRawResource(R.raw.questions);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        questions = JSONMapper.fromJson(result,TextQuestion[].class);

        questions[i].ShowQuestion(this);
    }

    public void Check(View v){
        if(questions[i].ChechAnswer(Integer.parseInt(v.getTag().toString()))){
            Toast t = Toast.makeText(this, "has acertado",Toast.LENGTH_SHORT);
            t.show();

        }else{
            Toast t = Toast.makeText(this, "has fallado",Toast.LENGTH_SHORT);
            t.show();
        }
    }

}
