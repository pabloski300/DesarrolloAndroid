package com.quiz.jodacampabloski.quiz;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game extends AppCompatActivity implements FailDialog.NoticeDialogListener {


    private final String SELECT_QUERY = "SELECT * FROM " + Question.QuestionEntry.TABLE_NAME +" WHERE " + Question.QuestionEntry.TYPE +" = %s";
    private  String QUESTION_STRING = "%s/%s";
    List<Question> questions;
    Gson JSONMapper;
    int i;
    TextView TimeView;
    TextView QuestionView;
    TextView FailsView;
    Puntuacion p;
    int puntuacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TimeView = findViewById(R.id.Timer);
        FailsView = findViewById(R.id.questionCounter);
        QuestionView = findViewById(R.id.questionCount);
        SQLiteDatabase db = DataBaseManager.Instance.getReadableDatabase();
        Toast t = Toast.makeText(this, "Game Start", Toast.LENGTH_SHORT);
        t.show();
        QUESTION_STRING = String.format(QUESTION_STRING,"%s",Opcions.NumeroPreguntas);
        i=0;
        QuestionView.setText(String.format(QUESTION_STRING,i));
        puntuacion = 0;
        questions = new ArrayList<Question>();

        if(Opcions.Texto) {
            String queryFormated = String.format(SELECT_QUERY,"'texto'");
            Cursor c = db.rawQuery(queryFormated,null);
            c.moveToFirst();
            do {
                questions.add(new TextQuestion(c));
            }while(c.moveToNext());



        }
        if(Opcions.Imagenes) {
            String queryFormated = String.format(SELECT_QUERY,"'imagen'");
            Cursor c = db.rawQuery(queryFormated,null);
            c.moveToFirst();
            do {
                questions.add(new ImageQuestion(c));
            }while(c.moveToNext());
        }


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
                    QuestionView.setText(String.format(QUESTION_STRING,i));
                }else{
                   EndGame();
                    //super.onBackPressed();
                }
            } else {
                Toast t = Toast.makeText(this, "Has fallado", Toast.LENGTH_SHORT);
                t.show();
                onDialogPositiveClick(null);
                QuestionView.setText(String.format(QUESTION_STRING,i));
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

    public void UpdateTime(){



    }
}
