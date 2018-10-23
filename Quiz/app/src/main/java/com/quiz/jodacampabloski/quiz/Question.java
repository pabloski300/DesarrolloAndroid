package com.quiz.jodacampabloski.quiz;

import android.app.Activity;
import android.content.ContentValues;
import android.provider.BaseColumns;


public abstract class Question {




    protected int id;

    protected String[] Answers;

    protected String HeaderImage;

    protected String HeaderVideo;

    protected String QuestionText;

    protected String Type;

    protected String Category;



    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getHeaderImage() {
        return HeaderImage;
    }

    public void setHeaderImage(String headerImage) {
        HeaderImage = headerImage;
    }

    public String getHeaderVideo() {
        return HeaderVideo;
    }

    public void setHeaderVideo(String headerVideo) {
        HeaderVideo = headerVideo;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String[] getAnswers() {
        return Answers;
    }

    public void setAnswers(String[] answers) {
        Answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int CorrectAnswer;

    public abstract void ShowQuestion(Activity mainGame);

    public boolean ChechAnswer(int a) {
        return a == CorrectAnswer;
    }

    int getImageIdFromString(String imageId, Activity contextApplication){
        return contextApplication.getResources().getIdentifier(imageId,"drawable",contextApplication.getPackageName());
    }


    public static class QuestionEntry implements BaseColumns{
        public static final String TABLE_NAME = "Question";
        public static final String TYPE = "type";
        public static final String CATEGORY = "category";
        public static final String ANSWERS = "answers";
        public static final String HEADER_VIDEO = "headervideo";
        public static final String QUESTION_TEXT = "questiontext";
        public static final String HEADER_IMAGE = "headerimage";
        public static final String CORRECT_ANSWER = "correct";


        public static final String DELETE_TABLE = "DROP TABLE IF EXIST "+ TABLE_NAME;

        public static  final String CREATE_DATA_TABLE =  "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                TYPE + " TEXT," +
                CATEGORY + " TEXT,"+
                ANSWERS + " TEXT,"+
                HEADER_IMAGE +" TEXT,"+
                HEADER_VIDEO + " TEXT, "+
                QUESTION_TEXT + " TEXT,"+
                CORRECT_ANSWER+ " INT"+
                ");";
    }

    public ContentValues QuestionValue(){
        ContentValues values = new ContentValues();
        values.put(QuestionEntry.CATEGORY,getCategory());
        values.put(QuestionEntry.TYPE,getType());
        values.put(QuestionEntry.HEADER_IMAGE,getHeaderImage());
        values.put(QuestionEntry.QUESTION_TEXT,getQuestionText());
        values.put(QuestionEntry.HEADER_VIDEO,getHeaderVideo());
        values.put(QuestionEntry.CORRECT_ANSWER,getCorrectAnswer());
        String answers = Answers[0];
        for (int i =1;i<4;i++){
            answers+=","+Answers[i];
        }
        values.put(QuestionEntry.ANSWERS,answers);
        return  values;
    }

}
