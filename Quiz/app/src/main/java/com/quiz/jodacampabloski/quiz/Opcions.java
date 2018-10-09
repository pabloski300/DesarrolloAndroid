package com.quiz.jodacampabloski.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileOutputStream;

public class Opcions extends AppCompatActivity {

    public static int NumeroPreguntas = 5;
    public static boolean Imagenes = true;
    public static boolean Texto = true;
    RadioGroup r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcions);
         r = (RadioGroup) findViewById(R.id.radioGroup);
        Switch s = (Switch) findViewById(R.id.stext);
        Switch si = (Switch) findViewById(R.id.simagenes);

        si.setChecked(Imagenes);
        s.setChecked(Texto);

        switch (NumeroPreguntas){
            case 5:
                r.check(R.id.preguntas5);
                break;
            case 10:
                r.check(R.id.preguntas10);
                break;
            case 15:
                r.check(R.id.preguntas15);
                break;
        }



        CompoundButton.OnCheckedChangeListener t = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String tag = buttonView.getTag().toString();
                switch (tag) {
                    case "stext":
                        if (isChecked) {
                            Texto = true;

                        } else {
                            if (!((Switch) findViewById(R.id.simagenes)).isChecked()) {
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            } else {
                                Texto = false;
                                r.check(R.id.preguntas5);
                                NumeroPreguntas = 5;

                            }
                        }
                        break;
                    case "simagenes":
                        if (isChecked) {
                            Imagenes = true;
                        } else {
                            if (!((Switch) findViewById(R.id.stext)).isChecked()) {
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            } else {
                                Imagenes = false;
                            }
                        }
                        break;
                }
                WriteOption();
            }
        } ;


        s.setOnCheckedChangeListener(t);
        si.setOnCheckedChangeListener(t);

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();
        if(((Switch) findViewById(R.id.stext)).isChecked()) {
            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.preguntas5:
                    if (checked)
                        NumeroPreguntas = 5;
                    break;
                case R.id.preguntas10:
                    if (checked)
                        NumeroPreguntas = 10;
                    break;
                case R.id.preguntas15:
                    NumeroPreguntas = 15;
                    break;
            }
        }else{
            r.check(R.id.preguntas5);
            Toast.makeText(getApplicationContext(), "Con solo Imagenes solo hay 5 preguntas maximas", Toast.LENGTH_SHORT).show();
        }
        WriteOption();
    }

    public void WriteOption(){
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput("Opciones", MODE_PRIVATE);
            String out = NumeroPreguntas+","+String.valueOf(Texto)+","+String.valueOf(Imagenes);
            outputStream.write(out.getBytes());
            outputStream.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
