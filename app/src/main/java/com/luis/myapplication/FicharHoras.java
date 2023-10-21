package com.luis.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FicharHoras extends AppCompatActivity {

    TextView txt_registro_entrada, txt_registro_salida, txt_resultadoHoras;
    Button boton_guardar, btn_entrar, btn_salir;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userId;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar_horas);

        btn_entrar = findViewById(R.id.btn_entrar);
        btn_salir = findViewById(R.id.btn_salir);

        txt_registro_entrada = findViewById(R.id.txt_registro_entrada);
        txt_registro_salida = findViewById(R.id.txt_registro_salida);
        boton_guardar = findViewById(R.id.boton_guardar);
        txt_resultadoHoras = findViewById(R.id.txt_resultadoHoras);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferencesEntrada = getSharedPreferences("entrada", Context.MODE_PRIVATE);
        SharedPreferences preferencesSalida = getSharedPreferences("salida", Context.MODE_PRIVATE);
        txt_registro_entrada.setText(preferencesEntrada.getString("horaEntrada", ""));
        txt_registro_salida.setText(preferencesSalida.getString("horaSalida", ""));


        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            userId = user.getUid();
        } else{
            Toast.makeText(FicharHoras.this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }

            boton_guardar.setOnClickListener(view -> {
                String datoEntrada = txt_registro_entrada.getText().toString();
                String datoSalida = txt_registro_salida.getText().toString();


                if (!datoEntrada.isEmpty() && datoSalida.isEmpty()){
                    guardarEntrada();
                    Toast.makeText(FicharHoras.this, "Registro Entrada guardado", Toast.LENGTH_LONG).show();
                }else if (!datoEntrada.isEmpty() && !datoSalida.isEmpty()){
                    registrarHorasEntradaySalida();
                    reinicioGuardarEntradaSalida();
                    Toast.makeText(FicharHoras.this, "Registro guardado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FicharHoras.this, "Debes fichar tus horas", Toast.LENGTH_LONG).show();
                }
            });


            btn_entrar.setOnClickListener(view -> {
                String datoEntrada = txt_registro_entrada.getText().toString();

                try {
                    if (btn_entrar.isClickable() && datoEntrada.isEmpty()) {
                        registrarEntrada();
                    } else {
                        Toast.makeText(FicharHoras.this, "Ya has registrado tu hora de entrada", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Toast.makeText(FicharHoras.this, "ERROR", Toast.LENGTH_LONG).show();
                }


            });

            btn_salir.setOnClickListener(view -> {
                String datoSalida = txt_registro_salida.getText().toString();

                if (btn_salir.isClickable() && datoSalida.isEmpty()){
                    registrarSalida();
                    finalizarJornada();
                }else{
                    Toast.makeText(FicharHoras.this, "Ya has registrado tu hora de salida", Toast.LENGTH_LONG).show();
                }
            });


}




    private void reinicioGuardarEntradaSalida() {
        SharedPreferences preferencesEntrada = getSharedPreferences("entrada", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorEntrada = preferencesEntrada.edit();
        editorEntrada.clear();
        editorEntrada.commit();
        SharedPreferences preferencesSalida = getSharedPreferences("salida", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSalida = preferencesSalida.edit();
        editorSalida.clear();
        editorSalida.commit();
        finish();
    }

    private void guardarEntrada() {
        SharedPreferences preferences = getSharedPreferences("entrada", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();
        finish();
    }


    private void finalizarJornada() {
        String inicioStr = txt_registro_entrada.getText().toString();
        String finStar = txt_registro_salida.getText().toString();

        SimpleDateFormat rstH = new SimpleDateFormat("HH:mm:ss");

        try {
            Date horaInicio = rstH.parse(inicioStr);
            Date horaFin = rstH.parse(finStar);

            long differenceMillis = horaFin.getTime() - horaInicio.getTime();

            long segundos = differenceMillis / 1000;
            long horas = segundos / 3600;
            long minutos = segundos % 3600 / 60;
            segundos =segundos % 60;

            @SuppressLint("DefaultLocale") String resultado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
            txt_resultadoHoras.setText(resultado);

        }catch (ParseException e){
            txt_resultadoHoras.setText("Error en el formato hora");
        }

    }

    private void registrarHorasEntradaySalida() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault());
        String fechactual = sdf.format(new Date());
        String entrada = txt_registro_entrada.getText().toString();
        String salida = txt_registro_salida.getText().toString();
        String totalHoras = txt_resultadoHoras.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("Hora salida", salida);
        users.put("Hora entrada", entrada);
        users.put("Total horas", totalHoras);

        db.collection("users").document(userId)
                .collection(fechactual)
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Log.i("MiApp", "Document added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("MiApp", "Error añadiendo el documento", e);
                    }
                });


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("MiApp", document.getId() + "=>" + document.getData());
                            }
                        }else {
                            Log.w("MiApp", "Error getting documents " + task.getException());
                        }
                    }
                });
    }

    private void registrarSalida() {
        SimpleDateFormat sdfOff = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String horaSalida = sdfOff.format(new Date());
        txt_registro_salida.setText(horaSalida);

        SharedPreferences preferencesSalida = getSharedPreferences("salida", Context.MODE_PRIVATE);
        if (!horaSalida.isEmpty()){
            SharedPreferences.Editor editor = preferencesSalida.edit();
            editor.putString("horaSalida", txt_registro_salida.getText().toString());
            editor.apply();
        }else {
            SharedPreferences.Editor editor = preferencesSalida.edit();
            editor.clear();
            editor.apply();
        }






    }

    private void registrarEntrada() {
        SimpleDateFormat sdfOn = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String horaEntrada = sdfOn.format(new Date());
        txt_registro_entrada.setText(horaEntrada);


        SharedPreferences preferenciaEntrada = getSharedPreferences("entrada", Context.MODE_PRIVATE);

        if (!horaEntrada.isEmpty()){
            SharedPreferences.Editor editor = preferenciaEntrada.edit();
            editor.putString("horaEntrada", txt_registro_entrada.getText().toString());
            editor.apply();
        }else {
            SharedPreferences.Editor editor = preferenciaEntrada.edit();
            editor.clear();
            editor.apply();
        }

    }
}