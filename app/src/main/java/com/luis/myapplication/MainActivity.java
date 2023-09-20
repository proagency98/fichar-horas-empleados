package com.luis.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText txt_user;
    EditText txt_password;
    Button btn_iniciar;
    Button btn_accionFormularioRegistro;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Inicializa Firebase Authentication
        con esta accion estamos indicando que se utilice la BBDD de firebase
         */
        firebaseAuth = FirebaseAuth.getInstance();


        txt_user = findViewById(R.id.txt_correoElectronico);
        txt_password = findViewById(R.id.txt_passwordPrincipal);
        btn_iniciar = findViewById(R.id.btn_iniciar);
        btn_accionFormularioRegistro = findViewById(R.id.btn_accionFormularioRegistro);


        btn_accionFormularioRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegistro = new Intent(MainActivity.this, Registro.class);
                startActivity(intentRegistro);
            }
        });


        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txt_user.getText().toString();
                String password = txt_password.getText().toString();
                iniciarSesionFireBase(email, password);
            }
        });
    }

    private void iniciarSesionFireBase(String email, String password) {
        try {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    // El inicio de sesión fue exitoso, puedes hacer algo aquí
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    // Puedes redirigir a una nueva actividad aquí
                                    Intent intent = new Intent(MainActivity.this, menu.class);
                                    startActivity(intent);
                                } else {
                                    // El inicio de sesión falló, muestra un mensaje de error
                                    Toast.makeText(MainActivity.this, "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error: " + e.getMessage());
        }
    }
}
