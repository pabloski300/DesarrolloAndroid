package com.quiz.jodacampabloski.quiz;

import android.content.ContentValues;
import android.provider.BaseColumns;

public class Profile {
    public static class ProfileSql implements BaseColumns {
        public static String TABLE_NAME = "profiles";
        public static String NAME = "name";
        public static String IMAGE = "profile_image";
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID +
               " INTEGER PRIMARY KEY,"+
                NAME + " TEXT,"+
                IMAGE + " TEXT );";

        public static String DELETE_TABLE = "DROP TABLE IF EXIST " + TABLE_NAME;
    }

    String name;
    String imageName;
    int id;
    public Profile(String name, String imageName,int id){
        this.name = name;
        this.imageName = imageName;
        this.id = id;
    }
    public ContentValues toSQLValue(){
        ContentValues c = new ContentValues();
        c.put(ProfileSql.NAME,name);
        c.put(ProfileSql.IMAGE,imageName);
        return  c;
    }



}
