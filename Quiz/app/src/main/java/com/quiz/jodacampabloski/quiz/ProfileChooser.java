package com.quiz.jodacampabloski.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProfileChooser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_chooser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivty = new Intent(getApplicationContext(),ProfileCreator.class);
                startActivity(nextActivty);
            }
        });

        DataBaseManager db = DataBaseManager.CreateInstance(this.getBaseContext());
        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM " + Profile.ProfileSql.TABLE_NAME,null);
        final ListView profileView = findViewById(R.id.profileList);
        int[] views = {R.id.user_name,R.id.profile_image};
        String[] columns = {Profile.ProfileSql.NAME,Profile.ProfileSql.IMAGE};
        SimpleCursorAdapter a = new SimpleCursorAdapter(this,R.layout.profile_layout,c,columns,views,0);
        profileView.setAdapter(a);
        profileView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                MainPage.actualProfile = new Profile(c);
                Intent nextActivty = new Intent(getApplicationContext(),MainPage.class);
                startActivity(nextActivty);
                finish();
            }
        });
    }

}
