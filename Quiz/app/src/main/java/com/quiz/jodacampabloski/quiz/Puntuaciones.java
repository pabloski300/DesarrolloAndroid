package com.quiz.jodacampabloski.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Puntuaciones extends AppCompatActivity{

    Gson JSONMapper;
    Puntuacion[] puntuaciones;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONMapper = new Gson();
        setContentView(R.layout.activity_puntuaciones);


        InputStream reader = null;
        try {
            reader = openFileInput("Score");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner s = new Scanner(reader).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        puntuaciones = JSONMapper.fromJson(result,Puntuacion[].class);

        TextView texts[] = new TextView[10];

        texts[0] = this.findViewById(R.id.textView1);
        texts[1] = this.findViewById(R.id.textView2);
        texts[2] = this.findViewById(R.id.textView3);
        texts[3] = this.findViewById(R.id.textView4);
        texts[4] = this.findViewById(R.id.textView5);
        texts[5] = this.findViewById(R.id.textView6);
        texts[6] = this.findViewById(R.id.textView7);
        texts[7] = this.findViewById(R.id.textView8);
        texts[8] = this.findViewById(R.id.textView9);
        texts[9] = this.findViewById(R.id.textView10);


        for(int i = 0; i<puntuaciones.length; i++){
            texts[i].setText((i+1)+". "+puntuaciones[i].name+": "+puntuaciones[i].score);
        }
    }
}
