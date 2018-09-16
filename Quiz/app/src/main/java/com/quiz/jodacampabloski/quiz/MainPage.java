package com.quiz.jodacampabloski.quiz;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


    }

    public void StartGame(View v){
        Intent nextActivty = new Intent(this,Game.class);
        startActivity(nextActivty);

    }

    public void Scores(View v){
        Intent nextActivty = new Intent(this,Puntuaciones.class);
        startActivity(nextActivty);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
}
