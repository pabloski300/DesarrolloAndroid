package com.quiz.jodacampabloski.quiz;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Game extends AppCompatActivity implements FailDialog.NoticeDialogListener {


    private  String SELECT_QUERY = "SELECT * FROM " + Question.QuestionEntry.TABLE_NAME +" WHERE " + Question.QuestionEntry.TYPE +" = %s";
    private String QUESTION_STRING = "%s/%s";
    private String ACIERTOS_FALLOS = "A: %s F: %s";
    List<Question> questions;
    Gson JSONMapper;
    int i;
    Chronometer TimeView;
    TextView QuestionView;
    TextView FailsView;
    Puntuacion p;
    float puntuacion;
    int finalPuntuacion;
    int aciertos = 0;
    int fallos = 0;
    int timesec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TimeView = findViewById(R.id.Timer);
        TimeView.setFormat("Time: %s");
        TimeView.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                timesec++;
            }
        });
        FailsView = findViewById(R.id.questionCounter);
        QuestionView = findViewById(R.id.questionCount);
        SQLiteDatabase db = DataBaseManager.Instance.getReadableDatabase();
        Toast t = Toast.makeText(this, "Game Start", Toast.LENGTH_SHORT);
        t.show();
        QUESTION_STRING = String.format(QUESTION_STRING,"%s",Opcions.NumeroPreguntas);
        FailsView.setText(String.format(ACIERTOS_FALLOS,0,0));
        i=0;
        QuestionView.setText(String.format(QUESTION_STRING,i+1));
        puntuacion = 0;
        questions = new ArrayList<Question>();
        String CategoriesFilter = getCategoriFilter();

            String queryFormated = String.format(SELECT_QUERY,"'texto'") + CategoriesFilter;

            Cursor c = db.rawQuery(queryFormated,null);
            c.moveToFirst();
            do {
                questions.add(new TextQuestion(c));
            }while(c.moveToNext());


            queryFormated = String.format(SELECT_QUERY,"'imagen'") + CategoriesFilter;
            c = db.rawQuery(queryFormated,null);
            c.moveToFirst();
            do {
                questions.add(new ImageQuestion(c));
            }while(c.moveToNext());



        Collections.shuffle(questions);
        TimeView.start();
        questions.get(i).ShowQuestion(this);
    }

    public void Check(View v){

            if (questions.get(i).ChechAnswer(Integer.parseInt(v.getTag().toString()))) {
                Toast t = Toast.makeText(this, "Has acertado", Toast.LENGTH_SHORT);
                t.show();

                aciertos++;
                FailsView.setText(String.format(ACIERTOS_FALLOS,aciertos,fallos));
                puntuacion += 100;
                i++;
                if(i<Opcions.NumeroPreguntas) {
                    questions.get(i).ShowQuestion(this);
                    QuestionView.setText(String.format(QUESTION_STRING,i+1));
                }else{
                    TimeView.stop();
                    EndGame();
                    //super.onBackPressed();
                }
            } else {
                Toast t = Toast.makeText(this, "Has fallado", Toast.LENGTH_SHORT);
                t.show();

                fallos ++;
                FailsView.setText(String.format(ACIERTOS_FALLOS,aciertos,fallos));
                onDialogPositiveClick(null);

            }
    }

    public void onBackPressed(){
        Toast t = Toast.makeText(this, "No puedes retroceder durante el quiz",Toast.LENGTH_SHORT);
        t.show();
    }
    public void EndGame(){

        puntuacion = puntuacion * ((Opcions.NumeroPreguntas * 10) / timesec);
        DecimalFormat df = new DecimalFormat("#");
        finalPuntuacion = Integer.parseInt(df.format(puntuacion));
        Toast t = Toast.makeText(this, "Has Terminado la partida", Toast.LENGTH_SHORT);
        t.show();
        Intent nextActivty = new Intent(this,SavePuntuation.class);
        nextActivty.putExtra("Score",finalPuntuacion);
        MainPage.actualProfile.games++;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        MainPage.actualProfile.date = sdf.format(new Date());
        DataBaseManager.Instance.getWritableDatabase().update(Profile.ProfileSql.TABLE_NAME,MainPage.actualProfile.toSQLValue(),
                Profile.ProfileSql._ID+ "="+MainPage.actualProfile.id,null);

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
            QuestionView.setText(String.format(QUESTION_STRING,i+1));

            questions.get(i).ShowQuestion(this);
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private String getCategoriFilter(){
            String n = " OR "+ Question.QuestionEntry.CATEGORY + "  = %s ";
            String nnor= Question.QuestionEntry.CATEGORY + "  = %s ";
            String query = " AND  ( %s )";
            String insideQuery = "";
            for(int i = 0; i<Opcions.CategoriesChecked.length; i++){
                if(Opcions.CategoriesChecked[i]){
                    if(insideQuery != "") {
                        insideQuery += String.format(n, "'" + Opcions.Categories[i] + "'");
                    } else
                    {
                        insideQuery +=String.format(nnor,"'"+Opcions.Categories[i]+"'");
                    }
                }

            }
            query = String.format(query,insideQuery);
            return query;

    }
}
