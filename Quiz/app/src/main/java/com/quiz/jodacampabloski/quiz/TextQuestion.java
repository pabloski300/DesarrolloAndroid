package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        Button button0 = mainGame.findViewById(R.id.button0);
        Button button1 = mainGame.findViewById(R.id.button1);
        Button button2 = mainGame.findViewById(R.id.button2);
        Button button3 = mainGame.findViewById(R.id.button3);

        button0.setText(Answers[0]);
        button1.setText(Answers[1]);
        button2.setText(Answers[2]);
        button3.setText(Answers[3]);

        button0.setBackground(null);
        button1.setBackground(null);
        button2.setBackground(null);
        button3.setBackground(null);



        ImageView image = mainGame.findViewById(R.id.HeaderImage);
        if(HeaderImage == null) {
            image.setImageResource(0);
        }else{
            int id = mainGame.getResources().getIdentifier(HeaderImage,"drawable",mainGame.getPackageName());
            image.setImageResource(id);

        }
    }
}
