package com.luis.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class FicharHoras extends AppCompatActivity {

    ToggleButton boton_toggle_on_off;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar_horas);

        boton_toggle_on_off = findViewById(R.id.boton_toggle_on_off);
        boton_toggle_on_off.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                Toast.makeText(FicharHoras.this,"Puedes registrar tu jornada laboral", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(FicharHoras.this, "Puedes registrar tu fin de jornada laboral", Toast.LENGTH_LONG).show();
            }
        });

        }
    }
