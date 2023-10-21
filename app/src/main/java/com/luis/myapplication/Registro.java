package com.luis.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText txt_nombre;
    EditText txt_correoElectronico;
    EditText txt_passwordPrincipal;
    EditText txt_passwordVerificacion;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_correoElectronico = findViewById(R.id.txt_correoElectronico);
        txt_passwordPrincipal = findViewById(R.id.txt_passwordPrincipal);
        txt_passwordVerificacion = findViewById(R.id.txt_passwordVerificacion);
        btn_registrar = findViewById(R.id.btn_registrar);

        mAuth = FirebaseAuth.getInstance();

        btn_registrar.setOnClickListener(view -> registrarUsuario());

    }

    private void registrarUsuario() {
        String name = txt_nombre.getText().toString();
        String correo = txt_correoElectronico.getText().toString();
        String password = txt_passwordVerificacion.getText().toString();
        String password2 = txt_passwordVerificacion.getText().toString();


        if (name.isEmpty() | correo.isEmpty() | password.isEmpty() | password2.isEmpty()){
            Toast.makeText(this,"Rellena todas las casillas", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Registro.this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
