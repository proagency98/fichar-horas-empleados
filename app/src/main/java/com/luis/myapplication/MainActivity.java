package com.luis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText txt_user;
    EditText txt_password;
    Button btn_iniciar;
    Button btn_accionFormularioRegistro;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Inicializa Firebase Authentication
        con esta accion estamos indicando que se utilice la BBDD de firebase
         */
        mAuth = FirebaseAuth.getInstance();


        txt_user = findViewById(R.id.txt_correoElectronico);
        txt_password = findViewById(R.id.txt_passwordPrincipal);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        btn_accionFormularioRegistro = findViewById(R.id.btn_accionFormularioRegistro);


        btn_accionFormularioRegistro.setOnClickListener(view -> {
            Intent intentRegistro = new Intent(MainActivity.this, Registro.class);
            startActivity(intentRegistro);
        });


        btn_iniciar.setOnClickListener(view -> {
            String email = txt_user.getText().toString();
            String password = txt_password.getText().toString();
            iniciarSesionFireBase(email, password);
        });
    }

    private void iniciarSesionFireBase(String email, String password) {
        try {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    mAuth.getCurrentUser();
                                    // El inicio de sesión fue exitoso, puedes hacer algo aquí
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    // Puedes redirigir a una nueva actividad aquí
                                    Intent intent = new Intent(MainActivity.this, menu.class);
                                    startActivity(intent);
                                } else {
                                    // El inicio de sesión falló, muestra un mensaje de error
                                    Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                                }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error: " + e.getMessage());
        }
    }
}
