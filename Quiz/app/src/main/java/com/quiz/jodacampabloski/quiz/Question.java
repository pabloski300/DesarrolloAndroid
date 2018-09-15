package com.quiz.jodacampabloski.quiz;

import android.app.Activity;

import com.quiz.jodacampabloski.quiz.R;

public abstract class Question {
    public int CorrectAnswer;
    public abstract void ShowQuestion(Activity mainGame);

    public boolean ChechAnswer(int a) {
        return a == CorrectAnswer;
    }

}
