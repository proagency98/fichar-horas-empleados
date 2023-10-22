package com.luis.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class menu extends AppCompatActivity {
    ImageButton btn_optionFichar, imgBtn_calendar, imgBtn_documents, imgBtn_solicitud, imgBtn_Exit;
    TextView txt_bienvenido3;
    FirebaseAuth mAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        txt_bienvenido3 = findViewById(R.id.txt_bienvenido3);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //solicitud de firebase para obtener correo
        if (user != null){
           String userName = user.getEmail();

            txt_bienvenido3.setText(userName);
        }else {
            txt_bienvenido3.setText("Usuario no autenticado");
        }




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