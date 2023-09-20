package com.luis.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FicharHoras extends AppCompatActivity {
    ToggleButton boton_toggle_on_off;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar_horas);

        TextView txt_FechaActual = findViewById(R.id.txt_FechaActual);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaActual = sdf.format(new Date());
        txt_FechaActual.setText("Fecha Actual: " + fechaActual);

        TextView txt_horaActual = findViewById(R.id.txt_horaActual);
        SimpleDateFormat stz = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String horaActual = stz.format(new Date());
        txt_horaActual.setText("Hora Actual: " + horaActual);




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
