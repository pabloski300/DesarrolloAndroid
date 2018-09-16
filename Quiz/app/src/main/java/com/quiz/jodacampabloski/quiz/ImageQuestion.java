package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageQuestion extends Question {

    public String[] Answers;
    public String HeaderImage;
    public String QuestionText;



    public ImageQuestion(String[] answers, String headerImage, String questionText,int correctAnswer) {
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

        button0.setBackground(mainGame.getDrawable(getImageIdFromString(Answers[0],mainGame)));
        button1.setBackground(mainGame.getDrawable(getImageIdFromString(Answers[1],mainGame)));
        button2.setBackground(mainGame.getDrawable(getImageIdFromString(Answers[2],mainGame)));
        button3.setBackground(mainGame.getDrawable(getImageIdFromString(Answers[3],mainGame)));
        button0.setText("");
        button1.setText("");
        button2.setText("");
        button3.setText("");

        ImageView image = mainGame.findViewById(R.id.HeaderImage);
        if(HeaderImage == null) {
            image.setMaxHeight(0);
            image.setMinimumHeight(0);
            image.setImageResource(0);
        }else{
            int id = mainGame.getResources().getIdentifier(HeaderImage,"drawable",mainGame.getPackageName());
            image.setImageResource(id);

        }
    }
}
