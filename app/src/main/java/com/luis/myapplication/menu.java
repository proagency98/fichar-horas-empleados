package com.luis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class menu extends AppCompatActivity {
    ImageButton btn_optionFichar, imgBtn_calendar, imgBtn_documents, imgBtn_solicitud, imgBtn_Exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imgBtn_calendar = findViewById(R.id.imgBtn_calendar);
        imgBtn_calendar.setOnClickListener(view -> {
            Intent intent = new Intent(menu.this, Calendar.class);
            startActivity(intent);
        });


        imgBtn_documents = findViewById(R.id.imgBtn_documents);
        imgBtn_documents.setOnClickListener(view -> {
            Intent intent = new Intent(menu.this, Documentacion.class);
            startActivity(intent);
        });

        imgBtn_solicitud = findViewById(R.id.imgBtn_solicitud);
        imgBtn_solicitud.setOnClickListener(view -> {
            Intent intent = new Intent(menu.this, Solicitud.class);
            startActivity(intent);

        });
        imgBtn_Exit = findViewById(R.id.imgBtn_Exit);
        imgBtn_Exit.setOnClickListener(view -> {
            Toast.makeText(this, "Sesión Finalizada", Toast.LENGTH_LONG).show();
            finish();

        });

        btn_optionFichar = findViewById(R.id.imgBtn_fichar);
        btn_optionFichar.setOnClickListener(view -> {
            Intent intent = new Intent(menu.this, FicharHoras.class);
            startActivity(intent);
            });
    }
}