package dadm.scaffold;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Scanner;

public class MainPage extends AppCompatActivity {

    public static Profile actualProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseManager.CreateInstance(getApplicationContext());
        setContentView(R.layout.activity_main_page);
        TextView title = findViewById(R.id.Title);
        title.setText(String.format(title.getText().toString(),actualProfile.name));
    }


    public void StartGame(View v){
        Intent nextActivty = new Intent(this,ScaffoldActivity.class);
        startActivity(nextActivty);

    }

    public void Scores(View v){
        Intent nextActivty = new Intent(this,Puntuaciones.class);
        startActivity(nextActivty);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent nextActivty;
        switch (item.getItemId()) {
            case R.id.perfileEdit:
                nextActivty = new Intent(this,ProfileCreator.class);
                startActivity(nextActivty);
                break;
            case R.id.salir:
                MainPage.actualProfile = null;
                Intent next = new Intent(this,EnterActivity.class);
                startActivity(next);
                finish();
                break;
            case R.id.deleteProfile:
                AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle(R.string.youSure).setPositiveButton(R.string.ContinueButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseManager.Instance.getWritableDatabase().delete(Profile.ProfileSql.TABLE_NAME, Profile.ProfileSql._ID+" = "+ MainPage.actualProfile.id,null);
                        MainPage.actualProfile = null;
                        Intent next = new Intent(getApplicationContext(),EnterActivity.class);
                        startActivity(next);
                        finish();
                    }
                }).setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog d = dialogo.create();

                d.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void onBackPressed(){

        //MainPage.actualProfile = null;
        Intent next = new Intent(this,EnterActivity.class);
        startActivity(next);
        finish();
    }


}
