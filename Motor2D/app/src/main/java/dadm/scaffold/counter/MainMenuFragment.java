package dadm.scaffold.counter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.DataBaseManager;
import dadm.scaffold.EnterActivity;
import dadm.scaffold.MainPage;
import dadm.scaffold.Profile;
import dadm.scaffold.ProfileCreator;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.Scores;


public class MainMenuFragment extends BaseFragment implements View.OnClickListener {
    public MainMenuFragment() {
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_page, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.Title);
        title.setText(String.format(title.getText().toString(),MainPage.actualProfile.name));
        view.findViewById(R.id.StartButton).setOnClickListener(this);
        view.findViewById(R.id.ScoreButtons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScaffoldActivity)getActivity()).navigateToFragment(new Scores());
            }
        });
        view.findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPage.actualProfile = null;
                Intent next = new Intent(getActivity(), EnterActivity.class);
                startActivity(next);
                getActivity().finish();
            }
        });

       // ((ScaffoldActivity)getActivity()).startGame();

    }


    @Override
    public void onClick(View v) {
        ((ScaffoldActivity)getActivity()).startGame();
    }

    @Override
    public boolean onBackPressed(){
        Intent next = new Intent(getActivity(),EnterActivity.class);
        startActivity(next);
        getActivity().finish();
        return true;

    }

}
