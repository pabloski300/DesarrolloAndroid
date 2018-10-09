package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class ImageQuestion extends Question {

    public String[] Answers;
    public String HeaderImage;
    //public String HeaderVideo;
    public String QuestionText;



    public ImageQuestion(String[] answers, String headerImage, /*String headerVideo ,*/String questionText,int correctAnswer) {
        Answers = answers;
        HeaderImage = headerImage;
        //HeaderVideo = headerVideo;
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
        VideoView video = mainGame.findViewById(R.id.videoView);
        if(HeaderImage == null /*&& HeaderVideo == null*/) {
            image.setVisibility(View.GONE);
            video.setVisibility(View.GONE);
        }else if(HeaderImage != null){
            video.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            int id = mainGame.getResources().getIdentifier(HeaderImage,"drawable",mainGame.getPackageName());
            image.setImageResource(id);
        }else{
            /*image.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            int id = mainGame.getResources().getIdentifier(HeaderVideo,"raw",mainGame.getPackageName());
            String uriPath = "android://"+"/"+id;
            Uri uri = Uri.parse(uriPath);
            video.setVideoURI(uri);*/
            //video.setOnPreparedListener(videoViewListener);
        }
    }

    /*private MediaPlayer.OnPreparedListener videoViewListener =
            new MediaPlayer.OnPreparedListener(){

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(false);
                    video.start();
                }
            };*/
}
