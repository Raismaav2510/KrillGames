package com.example.krillgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    //Declaración de elementos
    public Switch swHuella;
    public Button btnIngresar;
    EditText edtUsuario;
    EditText edtClave;

    //Variables Globales
    public Boolean CheckHuella;
    ControladorBD admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Asociación de elementos
        swHuella = (Switch)findViewById(R.id.swHuella);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtClave = (EditText) findViewById(R.id.edtContrasena);
        CheckHuella = false;
        admin = new ControladorBD(this, "KrillGames.db", null, 1);

        //Variables para el uso de huella
        androidx.biometric.BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        Executor executor = ContextCompat.getMainExecutor(this);
        final BiometricPrompt biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Ingresar();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Verifica tu identidad").setDescription("Coloca el dedo sobre el lector de huella").setNegativeButtonText("Cancelar").build();

        //Metodos atados a los botones directamente
        swHuella.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    CheckHuella = true;
                } else {
                    CheckHuella = false;
                }
            }
        });
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckHuella) {
                    biometricPrompt.authenticate(promptInfo);
                } else {
                    Ingresar();
                }
            }
        });
    }

    public void Ingresar() {
        SQLiteDatabase bd = admin.getReadableDatabase();
        if (edtClave.getText().toString().isEmpty() || edtUsuario.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos invalidos", Toast.LENGTH_SHORT).show();
        } else {
            String usuario = edtUsuario.getText().toString().trim();
            String clave = edtClave.getText().toString().trim();
            Cursor fila = bd.rawQuery("select Password from Usuario where Username LIKE ?", new String[] { "%" + usuario + "%" });
            if (fila.moveToFirst()) {
                if (clave.equals(fila.getString(0))) {
                    Intent ingresar = new Intent(this, MenuActivity.class);
                    startActivity(ingresar);
                } else {
                    Toast.makeText(getApplicationContext(),"Datos invalidos", Toast.LENGTH_SHORT).show();
                }
                bd.close();
            } else {
                Toast.makeText(getApplicationContext(), "Datos invalidos", Toast.LENGTH_SHORT).show();
                bd.close();
            }
        }
    }

    public void Registrar(View view) {
        Intent registrar = new Intent(this, RegisterActivity.class);
        startActivity(registrar);
    }
}