package com.quiz.jodacampabloski.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game extends AppCompatActivity {
    Question[] questions;
    Gson JSONMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONMapper = new Gson();
        setContentView(R.layout.activity_game);
        InputStream reader = getResources().openRawResource(R.raw.questions);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        questions = JSONMapper.fromJson(result,TextQuestion[].class);
    }



}
