package dadm.scaffold;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Profile {
    public static class ProfileSql implements BaseColumns {
        public static String TABLE_NAME = "profiles";
        public static String NAME = "name";
        public static String IMAGE = "profile_image";
        public static String MAX_POINTS = "score";
        public static String GAMES = "games";
        public static String DATE = "date";
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID +
               " INTEGER PRIMARY KEY,"+
                NAME + " TEXT,"+
                IMAGE + " TEXT, "+
                MAX_POINTS + " INTEGER,"+
                GAMES + " INTEGER, "+
                DATE + " DATE"+");";


        public static String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public String name;
    String imageName;
    public int id;
    public int games;
    public String date;
    public int maxScore;
    public Profile(String name, String imageName,int id,int games,String date){
        this.name = name;
        this.imageName = imageName;
        this.id = id;
        this.games = games;
        this.date = date;
        maxScore = 0;
    }

    public Profile(Cursor c){
        id = c.getInt(c.getColumnIndex(ProfileSql._ID));
        name = c.getString(c.getColumnIndex(ProfileSql.NAME));
        imageName = c.getString(c.getColumnIndex(ProfileSql.IMAGE));
        maxScore = c.getInt(c.getColumnIndex(ProfileSql.MAX_POINTS));
        games = c.getInt(c.getColumnIndex(ProfileSql.GAMES));
        date = c.getString(c.getColumnIndex(ProfileSql.DATE));

    }
    public ContentValues toSQLValue(){
        ContentValues c = new ContentValues();

        c.put(ProfileSql.NAME,name);
        c.put(ProfileSql.IMAGE,imageName);
        c.put(ProfileSql.MAX_POINTS,maxScore);
        c.put(ProfileSql.GAMES,games);
        c.put(ProfileSql.DATE,date);
        return  c;
    }



}
