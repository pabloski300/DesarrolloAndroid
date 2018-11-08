package com.quiz.jodacampabloski.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
    }

    void StartGame(){
        if(MainPage.actualProfile == null) {
            Intent nextActivty = new Intent(this, ProfileChooser.class);
            startActivity(nextActivty);
        }else{
            Intent nextActivty = new Intent(this,MainPage.class);
            startActivity(nextActivty);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       StartGame();
       finish();
        return super.onTouchEvent(event);
    }
}
