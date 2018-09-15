package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.widget.TextView;

public class TextQuestion extends Question {


    public String[] Answers;
    public String HeaderImage;
    public String QuestionText;


    public TextQuestion(String[] answers, String headerImage, String questionText,int correctAnswer) {
        Answers = answers;
        HeaderImage = headerImage;
        QuestionText = questionText;
        CorrectAnswer = correctAnswer;
    }

    @Override
    public void ShowQuestion(Activity mainGame) {
        TextView questionText = mainGame.findViewById(R.id.QuestionText);
        questionText.setText(QuestionText);

    }
}
