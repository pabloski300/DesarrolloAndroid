package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.DataBaseManager;
import dadm.scaffold.MainPage;
import dadm.scaffold.Profile;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.GameManager;

public class ScoreFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_save_puntuation, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.points)).setText(String.valueOf(GameManager.score));
        view.findViewById(R.id.notSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)GameFragment.actualGameFragment.getActivity()).navigateToFragment(new MainMenuFragment());
            }
        });

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPage.actualProfile.maxScore = MainPage.actualProfile.maxScore< GameManager.score ? GameManager.score : MainPage.actualProfile.maxScore;
                MainPage.actualProfile.games++;
                DataBaseManager.Instance.getWritableDatabase().update(Profile.ProfileSql.TABLE_NAME,MainPage.actualProfile.toSQLValue(),
                        Profile.ProfileSql._ID+ " = " + MainPage.actualProfile.id,null);

                ((ScaffoldActivity)GameFragment.actualGameFragment.getActivity()).navigateToFragment(new MainMenuFragment());
            }
        });
    }





}
