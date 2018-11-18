package dadm.scaffold;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class Puntuaciones extends AppCompatActivity{




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);
        Cursor c = DataBaseManager.Instance.getReadableDatabase().rawQuery("SELECT " + Profile.ProfileSql.NAME+","+ Profile.ProfileSql.MAX_POINTS +" FROM " + Profile.ProfileSql.TABLE_NAME + " ORDER BY "+Profile.ProfileSql.MAX_POINTS+" DESC LIMIT 10",null);
        TextView texts[] = new TextView[10];

        texts[0] = this.findViewById(R.id.textView1);
        texts[1] = this.findViewById(R.id.textView2);
        texts[2] = this.findViewById(R.id.textView3);
        texts[3] = this.findViewById(R.id.textView4);
        texts[4] = this.findViewById(R.id.textView5);
        texts[5] = this.findViewById(R.id.textView6);
        texts[6] = this.findViewById(R.id.textView7);
        texts[7] = this.findViewById(R.id.textView8);
        texts[8] = this.findViewById(R.id.textView9);
        texts[9] = this.findViewById(R.id.textView10);

        int l = c.getCount();
        c.moveToFirst();
        for(int i = 0; i<l; i++){
            texts[i].setText((i+1)+". "+c.getString(c.getColumnIndex(Profile.ProfileSql.NAME))+": "+c.getInt(c.getColumnIndex(Profile.ProfileSql.MAX_POINTS)));
            c.moveToNext();
        }
    }


}
