package com.quiz.jodacampabloski.quiz;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickClick;
import com.vansuita.pickimage.listeners.IPickResult;

public class ProfileCreator extends AppCompatActivity implements IPickResult {

    String imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creator);
    }

    public void setPhoto(View v){
        PickImageDialog.build(new PickSetup()).show(this);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            ImageView view =  findViewById(R.id.imageProfile);
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            view.setImageURI(null);

            //Setting the real returned image.
             view.setImageURI(r.getUri());

            //If you want the Bitmap.
            view.setImageBitmap(r.getBitmap());

            //Image path
            imageUri = r.getPath();
        }
    }

    public void EndProfile(View v){
        Profile p;
        String name =((TextInputLayout) findViewById(R.id.inputLayout)).getEditText().getText().toString();
        if(imageUri == null){
            Toast.makeText(this.getBaseContext(),"Tienes que elegir una imagen",Toast.LENGTH_SHORT);
        }else{
            p = new Profile(name,imageUri,0);
            int id = (int) DataBaseManager.CreateInstance(getBaseContext()).getWritableDatabase().insert(Profile.ProfileSql.TABLE_NAME,null,p.toSQLValue());
            p.id = id;
            Intent nextActivty = new Intent(this,MainPage.class);

            startActivity(nextActivty);
            finish();

        }
    }
}
