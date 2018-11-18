package dadm.scaffold;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public final class DataBaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Quiz.db";

    Context c = null;
    public static  DataBaseManager Instance = null;
    private static boolean created = false;

    public static DataBaseManager CreateInstance(Context context){

        if(!created) {
            DataBaseManager.Instance = new DataBaseManager(context);
            DataBaseManager.created = true;
        }
        return Instance;
    }



    private DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Profile.ProfileSql.CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Profile.ProfileSql.DELETE_TABLE);
        onCreate(db);
    }
}
