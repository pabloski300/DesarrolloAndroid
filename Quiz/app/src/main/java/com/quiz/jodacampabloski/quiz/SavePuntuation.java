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
        TextView t = findViewById(R.id.TextPoints);
        String newText =  t.getText().toString() +" "+ Score;
        t.setText(newText);
    }


    @Override
    public void onBackPressed(){
        Toast t = Toast.makeText(this,getText(R.string.backscore),Toast.LENGTH_SHORT);
        t.show();
        DontSave(null);
    }

    public void Save(View v){
        List<Puntuacion> puntuaciones = new ArrayList<>();

        JSONMapper = new Gson();
        InputStream reader = null;
        try {
            reader = openFileInput("Score");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        puntuaciones.addAll(Arrays.asList(JSONMapper.fromJson(result,Puntuacion[].class)));

        puntuaciones.add(new Puntuacion(((TextInputLayout)findViewById(R.id.NameText)).getEditText().getText().toString(),Score));

        Collections.sort(puntuaciones);

        while(puntuaciones.size() > 10){
            puntuaciones.remove(puntuaciones.size()-1);
        }

        String punct = JSONMapper.toJson(puntuaciones);

        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput("Score", MODE_PRIVATE);
            outputStream.write(punct.getBytes());
            outputStream.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }

        finish();

    }

    public void DontSave(View v){
        finish();
    }
}
