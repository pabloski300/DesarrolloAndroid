package dadm.scaffold;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class ProfileCreator extends AppCompatActivity  {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    int id;
    int games;
    String date;
    String imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creator);
        ImageView view =  findViewById(R.id.imageProfile);
        TextInputLayout textView = (TextInputLayout) findViewById(R.id.inputLayout);
        id = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        games = 0;
        date = sdf.format(new Date());
        if(MainPage.actualProfile != null) {
            imageUri=MainPage.actualProfile.imageName;
            view.setImageURI(Uri.parse(MainPage.actualProfile.imageName));
            textView.getEditText().setText(MainPage.actualProfile.name);
            id = MainPage.actualProfile.id;
            games = MainPage.actualProfile.games;
            date = MainPage.actualProfile.date;
        }

    }

    public void setPhoto(View v){
        final Dialog camera = new Dialog(this);
        camera.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Activity t = this;
        camera.setContentView(R.layout.camera_dialog);
        camera.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(t,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        camera.dismiss();
                    }
                }else{

                    ActivityCompat.requestPermissions(t,
                            new String[]{Manifest.permission.CAMERA},
                            0);

                }
            }
        });
        camera.findViewById(R.id.cameraButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
                camera.dismiss();
            }
        });
        camera.show();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       if(resultCode != RESULT_CANCELED) {
           Bitmap imageBitmap = Bitmap.createBitmap(480, 300, Bitmap.Config.ARGB_8888);
           if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
               Bundle extras = data.getExtras();
               imageBitmap = (Bitmap) extras.get("data");
           } else if (requestCode == 100 && resultCode == RESULT_OK) {
               Uri selectedImage = data.getData();
               try {
                   imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
           try {
               File image = File.createTempFile(
                       "Quiz-" + "photo-" + Calendar.getInstance().getTimeInMillis(),  /* prefix */
                       ".jpg",         /* suffix */
                       storageDir      /* directory */
               );

               // Save a file: path for use with ACTION_VIEW intents
               FileOutputStream file = new FileOutputStream(image);

               imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, file);
               ImageView view = findViewById(R.id.imageProfile);
               view.setImageBitmap(imageBitmap);
               imageUri = image.getPath();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }



    public void EndProfile(View v){
        Profile p;
        String name =((TextInputLayout) findViewById(R.id.inputLayout)).getEditText().getText().toString();
        if(imageUri == null || name.isEmpty()){
            Toast.makeText(this.getBaseContext(),"Tienes que elegir una imagen",Toast.LENGTH_SHORT).show();
        }else{
            p = new Profile(name,imageUri,id,games,date);
            p.maxScore = MainPage.actualProfile != null ? MainPage.actualProfile.maxScore:0;
            ContentValues c = p.toSQLValue();
            if(MainPage.actualProfile != null){
                c.put(Profile.ProfileSql._ID,p.id);
            }

            int id = (int) DataBaseManager.CreateInstance(getBaseContext()).getWritableDatabase().insertWithOnConflict(Profile.ProfileSql.TABLE_NAME,null,
                    c, SQLiteDatabase.CONFLICT_REPLACE);
            p.id = id;
            MainPage.actualProfile = p;
            Intent nextActivty = new Intent(this,MainPage.class);
            startActivity(nextActivty);
            finish();

        }
    }
}
