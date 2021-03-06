package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class TextQuestion extends Question {



    public TextQuestion(String[] answers, String headerImage, String headerVideo, String questionText,int correctAnswer,String type,String category) {
        Answers = answers;
        HeaderImage = headerImage;
        HeaderVideo = headerVideo;
        QuestionText = questionText;
        CorrectAnswer = correctAnswer;
        Type = type;
        Category = category;
    }

    public TextQuestion(Cursor r){
        Type = r.getString(1);
        Category = r.getString(2);
        Answers = r.getString(3).split(",");
        HeaderImage = r.getString(4);
        HeaderVideo = r.getString(5);
        QuestionText = r.getString(6);
        CorrectAnswer = r.getInt(7);
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

        button0.setBackground(mainGame.getDrawable(R.drawable.grey));
        button1.setBackground(mainGame.getDrawable(R.drawable.grey));
        button2.setBackground(mainGame.getDrawable(R.drawable.grey));
        button3.setBackground(mainGame.getDrawable(R.drawable.grey));

        ImageView image = mainGame.findViewById(R.id.HeaderImage);
        VideoView video = mainGame.findViewById(R.id.videoView);
        if(HeaderImage == null && HeaderVideo == null) {
            image.setVisibility(View.GONE);
            video.setVisibility(View.GONE);
        }else if(HeaderImage != null){
            video.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            int id = mainGame.getResources().getIdentifier(HeaderImage,"drawable",mainGame.getPackageName());
            image.setImageResource(id);
        }else{
            image.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            int id = mainGame.getResources().getIdentifier(HeaderVideo,"raw",mainGame.getPackageName());
            String uriPath = "android.resource://"+mainGame.getPackageName()+"/"+id;
            Uri uri = Uri.parse(uriPath);
            video.setVideoURI(uri);
            video.start();
        }
    }



}
