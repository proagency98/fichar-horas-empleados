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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
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

                // Crear un nuevo usuario en Firestore
                Map<String, Object> userData = new HashMap<>();
                userData.put("nombre", name);
                userData.put("correo", correo);

                db.collection("Usuarios").document(correo)
                        .set(userData, SetOptions.merge()) // Para fusionar datos si el documento ya existe
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Datos guardados en Firestore con éxito
                                    Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    // Error al guardar datos en Firestore
                                    Toast.makeText(Registro.this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
