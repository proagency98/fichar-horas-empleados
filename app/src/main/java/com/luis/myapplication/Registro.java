package com.luis.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText txt_nombre;
    EditText txt_correoElectronico;
    EditText txt_passwordPrincipal;
    Button btn_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_correoElectronico = findViewById(R.id.txt_correoElectronico);
        txt_passwordPrincipal = findViewById(R.id.txt_passwordPrincipal);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt_nombre.getText().toString();
                String correo = txt_correoElectronico.getText().toString();
                String passwordPrincipal = txt_passwordPrincipal.getText().toString();

                // Validación de campos
                if (name.isEmpty() || correo.isEmpty() || passwordPrincipal.isEmpty()) {
                    Toast.makeText(Registro.this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Obtén el usuario actual de Firebase Authentication
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    // El usuario está autenticado, podemos obtener su UID
                    String userId = currentUser.getUid();

                    // Crear un nuevo usuario en Firestore con el mismo ID de usuario
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("nombre", name);
                    userData.put("correo", correo);

                    db.collection("Usuario").document(correo).set(userData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Registro.this, "Error al registrarse", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
