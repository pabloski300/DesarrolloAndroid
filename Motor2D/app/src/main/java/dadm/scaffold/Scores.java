package dadm.scaffold;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Scores extends BaseFragment {




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_puntuaciones, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {

        Cursor c = DataBaseManager.Instance.getReadableDatabase().rawQuery("SELECT " + Profile.ProfileSql.NAME+","+ Profile.ProfileSql.MAX_POINTS +" FROM " + Profile.ProfileSql.TABLE_NAME + " ORDER BY "+Profile.ProfileSql.MAX_POINTS+" DESC LIMIT 10",null);
        TextView texts[] = new TextView[10];

        texts[0] = view.findViewById(R.id.textView1);
        texts[1] = view.findViewById(R.id.textView2);
        texts[2] = view.findViewById(R.id.textView3);
        texts[3] = view.findViewById(R.id.textView4);
        texts[4] = view.findViewById(R.id.textView5);
        texts[5] = view.findViewById(R.id.textView6);
        texts[6] = view.findViewById(R.id.textView7);
        texts[7] = view.findViewById(R.id.textView8);
        texts[8] = view.findViewById(R.id.textView9);
        texts[9] = view.findViewById(R.id.textView10);

        int l = c.getCount();
        c.moveToFirst();
        for(int i = 0; i<l; i++){
            texts[i].setText((i+1)+". "+c.getString(c.getColumnIndex(Profile.ProfileSql.NAME))+": "+c.getInt(c.getColumnIndex(Profile.ProfileSql.MAX_POINTS)));
            c.moveToNext();
        }
    }
}
