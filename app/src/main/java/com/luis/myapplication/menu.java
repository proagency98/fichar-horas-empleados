package com.luis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {
    Button btn_optionFichar;

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_optionFichar = findViewById(R.id.btn_optionFichar);
        btn_optionFichar.setOnClickListener(view -> {
            Intent intent = new Intent(menu.this, FicharHoras.class);
            startActivity(intent);
        });
    }
}