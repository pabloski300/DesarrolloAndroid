package dadm.scaffold.counter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;

public class ShipChooser extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ship_chooser, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.ship1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment.playerResourceId = R.drawable.nave64x64smooth;
                ((ScaffoldActivity)getActivity()).startGame();
            }
        });
        view.findViewById(R.id.ship2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment.playerResourceId = R.drawable.nave264x64smooth;
                ((ScaffoldActivity)getActivity()).startGame();
            }
        });
    }

}
