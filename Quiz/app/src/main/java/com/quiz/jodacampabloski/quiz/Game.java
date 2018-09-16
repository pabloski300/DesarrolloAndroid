package com.quiz.jodacampabloski.quiz;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game extends AppCompatActivity {


    List<Question> questions;

    Gson JSONMapper;
    int i;

    Puntuacion p;
    int puntuacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        i=0;
        puntuacion = 0;

        JSONMapper = new Gson();
        setContentView(R.layout.activity_game);
        InputStream reader = getResources().openRawResource(R.raw.questions);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String[] results = result.split("//");

        questions = new ArrayList<Question>();
        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[0],TextQuestion[].class)));

        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[1],ImageQuestion[].class)));
        questions.get(i).ShowQuestion(this);
    }

    public void Check(View v){

            if (questions.get(i).ChechAnswer(Integer.parseInt(v.getTag().toString()))) {
                Toast t = Toast.makeText(this, "has acertado", Toast.LENGTH_SHORT);
                t.show();

                puntuacion += 100;
                i++;
                if(i<questions.size()) {
                    questions.get(i).ShowQuestion(this);
                }else{
                    p = new Puntuacion("Pablo", puntuacion);
                    super.onBackPressed();
                }
            } else {
                Toast t = Toast.makeText(this, "has fallado", Toast.LENGTH_SHORT);
                t.show();
                questions.get(i).ShowQuestion(this);
            }
    }

    protected void onPause(){
        super.onPause();
        List<Puntuacion> puntuaciones = new ArrayList<>();

        JSONMapper = new Gson();
        InputStream reader = getResources().openRawResource(R.raw.scores);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        puntuaciones.addAll(Arrays.asList(JSONMapper.fromJson(result,Puntuacion[].class)));

        puntuaciones.add(p);

        while(puntuaciones.size() > 10){
            puntuaciones.remove(puntuaciones.size()-1);
        }

        String punct = JSONMapper.toJson(puntuaciones);
    }

    public void onBackPressed(){
        Toast t = Toast.makeText(this, "no puedes retroceder durante el cuestionario",Toast.LENGTH_SHORT);
        t.show();
    }

}
