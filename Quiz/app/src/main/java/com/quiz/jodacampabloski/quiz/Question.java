package com.quiz.jodacampabloski.quiz;

import android.app.Activity;

public abstract class Question {
    public int CorrectAnswer;
    public abstract void ShowQuestion(Activity mainGame);

    public boolean ChechAnswer(int a) {
        return a == CorrectAnswer;
    }

    int getImageIdFromString(String imageId, Activity contextApplication){
        return contextApplication.getResources().getIdentifier(imageId,"drawable",contextApplication.getPackageName());
    }
}
