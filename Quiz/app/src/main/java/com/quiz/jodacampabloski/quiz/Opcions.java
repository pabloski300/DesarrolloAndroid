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
import java.util.List;

public class Opcions extends AppCompatActivity {

    public static int NumeroPreguntas = 5;
    public static boolean Imagenes = true;
    public static boolean Texto = true;
    public static final String[] Categories = {"General","Videojuegos","Programacion","Memes"};
    public static boolean[] CategoriesChecked = {true,true,true,true};
    RadioGroup r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcions);
        r = (RadioGroup) findViewById(R.id.radioGroup);
        Switch s = (Switch) findViewById(R.id.stext);
        Switch si = (Switch) findViewById(R.id.simagenes);
        Switch sp = (Switch) findViewById(R.id.sipro);
        Switch sm = (Switch) findViewById(R.id.simemes);

        s.setChecked(CategoriesChecked[0]);
        si.setChecked(CategoriesChecked[1]);
        sp.setChecked(CategoriesChecked[2]);
        sm.setChecked(CategoriesChecked[3]);


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
                            CategoriesChecked[0]= true;

                        } else {
                            if(getTrueOpcions() <2){
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                CategoriesChecked[0]= false;
                            }

                        }
                        break;
                    case "simagenes":
                        if (isChecked) {
                            CategoriesChecked[1]= true;
                        } else {
                            if(getTrueOpcions() <2){
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                CategoriesChecked[1]= false;
                            }

                        }
                        break;
                    case "sipro":
                        if(isChecked){
                            CategoriesChecked[2]= true;
                        }else
                        {
                            if(getTrueOpcions() <2){
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                CategoriesChecked[2]= false;
                            }

                        }
                        break;
                    case "simemes":
                        if(isChecked){
                            CategoriesChecked[3]= true;
                        }else {
                            if(getTrueOpcions() <2){
                                buttonView.setChecked(true);
                                Toast.makeText(getApplicationContext(), "Tienes que tener al menos una opcion seleccionada ", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                CategoriesChecked[3]= false;
                            }

                        }
                        break;
                }

                int n = getTrueOpcions();

                if(n*5 < NumeroPreguntas){
                    if(n < 10){
                        r.check(R.id.preguntas5);
                    }else
                        if(n<15){
                        r.check(R.id.preguntas10);
                        }
                }

                WriteOption();
            }
        } ;


        s.setOnCheckedChangeListener(t);
        si.setOnCheckedChangeListener(t);
        sp.setOnCheckedChangeListener(t);
        sm.setOnCheckedChangeListener(t);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch (view.getId()) {
                case R.id.preguntas5:
                    if (checked)
                        NumeroPreguntas = 5;
                    break;
                case R.id.preguntas10:
                    if (checked && getTrueOpcions() > 1)
                        NumeroPreguntas = 10;
                    else
                        r.check(R.id.preguntas5);
                    break;
                case R.id.preguntas15:
                    if(checked && getTrueOpcions() > 2)
                    NumeroPreguntas = 15;
                    else if( getTrueOpcions() == 2)
                        r.check(R.id.preguntas10);
                    else
                        r.check(R.id.preguntas5);
                    break;
            }

        WriteOption();
    }

    public void WriteOption(){
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput("Opciones", MODE_PRIVATE);
            String out = NumeroPreguntas+","+String.valueOf(CategoriesChecked[0])+","+String.valueOf(CategoriesChecked[1])+","+String.valueOf(CategoriesChecked[2])+","+ String.valueOf(CategoriesChecked[3]);
            outputStream.write(out.getBytes());
            outputStream.close();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private int getTrueOpcions(){
        int n=0;
        for(int i = 0; i<CategoriesChecked.length;i++){
            if(CategoriesChecked[i]){
                n++;
            }
        }
        return n;
    }
}
