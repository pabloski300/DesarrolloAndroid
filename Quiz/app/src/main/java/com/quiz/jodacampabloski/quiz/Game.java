package com.quiz.jodacampabloski.quiz;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game extends AppCompatActivity implements FailDialog.NoticeDialogListener {


    List<Question> questions;

    Gson JSONMapper;
    int i;

    Puntuacion p;
    int puntuacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast t = Toast.makeText(this, "Game Start", Toast.LENGTH_SHORT);
        t.show();

        i=0;
        puntuacion = 0;

        JSONMapper = new Gson();
        setContentView(R.layout.activity_game);
        InputStream reader = getResources().openRawResource(R.raw.questions);

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String[] results = result.split("//");

        questions = new ArrayList<Question>();
        if(Opcions.Texto)
        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[0],TextQuestion[].class)));

        if(Opcions.Imagenes)
        questions.addAll(Arrays.asList(JSONMapper.fromJson(results[1],ImageQuestion[].class)));

        Collections.shuffle(questions);
        questions.get(i).ShowQuestion(this);
    }

    public void Check(View v){

            if (questions.get(i).ChechAnswer(Integer.parseInt(v.getTag().toString()))) {
                Toast t = Toast.makeText(this, "Has acertado", Toast.LENGTH_SHORT);
                t.show();

                puntuacion += 100;
                i++;
                if(i<Opcions.NumeroPreguntas) {
                    questions.get(i).ShowQuestion(this);
                }else{
                   EndGame();
                    //super.onBackPressed();
                }
            } else {
                Toast t = Toast.makeText(this, "Has fallado", Toast.LENGTH_SHORT);
                t.show();
                FailDialog d = new FailDialog();
                d.setCancelable(false);
                d.show(getFragmentManager(),"Dialog");
                //questions.get(i).ShowQuestion(this);
            }
    }

    public void onBackPressed(){
        Toast t = Toast.makeText(this, "No puedes retroceder durante el quiz",Toast.LENGTH_SHORT);
        t.show();
    }
    public void EndGame(){
        Toast t = Toast.makeText(this, "Has Terminado la partida", Toast.LENGTH_SHORT);
        t.show();
        Intent nextActivty = new Intent(this,SavePuntuation.class);
        nextActivty.putExtra("Score",puntuacion);
        startActivity(nextActivty);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        i++;
        puntuacion-=50;
        if(i>=Opcions.NumeroPreguntas){
            EndGame();
        }else
        {
            questions.get(i).ShowQuestion(this);
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
