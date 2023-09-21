package com.luis.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FicharHoras extends AppCompatActivity {
    ToggleButton boton_toggle_on_off;
    TextView txt_registro_entrada;
    TextView txt_registro_salida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar_horas);
        boton_toggle_on_off = findViewById(R.id.boton_toggle_on_off);
        txt_registro_entrada = findViewById(R.id.txt_registro_entrada);
        txt_registro_salida = findViewById(R.id.txt_registro_salida);

        boton_toggle_on_off.setOnClickListener(view -> {
            if (boton_toggle_on_off.isChecked()){
                registrarSalida();
                Toast.makeText(FicharHoras.this, "Salida Registrada, buen trabajo!", Toast.LENGTH_LONG).show();
            } else {
                registrarEntrada();
                Toast.makeText(FicharHoras.this, "Entrada Registrada", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void registrarEntrada() {
        SimpleDateFormat sdfOn = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String horaEntrada = sdfOn.format(new Date());
        txt_registro_entrada.setText("Entrada: "+ "\n" + horaEntrada);
        int colorVerde = ContextCompat.getColor(this, R.color.checked);
        txt_registro_entrada.setTextColor(colorVerde);
    }
    private void registrarSalida() {
        SimpleDateFormat sdfOff = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String horaSalida = sdfOff.format(new Date());
        txt_registro_salida.setText("Salida: "+ "\n" + horaSalida);
        int colorRojo = ContextCompat.getColor(this, R.color.unChecked);
        txt_registro_salida.setTextColor(colorRojo);
    }




}


