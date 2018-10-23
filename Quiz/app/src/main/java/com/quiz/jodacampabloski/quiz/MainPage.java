package com.quiz.jodacampabloski.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseManager.CreateInstance(getApplicationContext());
        setContentView(R.layout.activity_main_page);
        try {
        InputStream fileInput = openFileInput("Opciones");
        Scanner s = new Scanner(fileInput).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String results[] = result.split(",");

        Opcions.NumeroPreguntas = Integer.parseInt(results[0]);
        Opcions.Imagenes = Boolean.parseBoolean(results[2]);
        Opcions.Texto = Boolean.parseBoolean(results[1]);

        } catch (Exception e) {
        FileOutputStream outputStream;


            try {
                outputStream = openFileOutput("Opciones", MODE_PRIVATE);
                String out = "5,true,true";
                outputStream.write(out.getBytes());
                outputStream.close();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            }

        try {
            openFileInput("Score");


        } catch (Exception e) {

            InputStream reader = getResources().openRawResource(R.raw.scores);

            Scanner s = new Scanner(reader).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";


            FileOutputStream outputStream;


            try {
                outputStream = openFileOutput("Score", MODE_PRIVATE);
                outputStream.write(result.getBytes());
                outputStream.close();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Opciones:
                Intent nextActivty = new Intent(this,Opcions.class);
                startActivity(nextActivty);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
