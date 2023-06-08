package com.example.krillgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    //Declaración de elementos
    public Switch swHuella;
    public Button btnIngresar;

    //Variables Globales
    public Boolean CheckHuella;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Asociación de elementos
        swHuella = (Switch)findViewById(R.id.swHuella);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);

        CheckHuella = false;

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

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Verifica tu identidad")
                .setDescription("Coloca el dedo sobre el lector de huella").setNegativeButtonText("Cancelar").build();


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
                if(CheckHuella){
                    biometricPrompt.authenticate(promptInfo);
                }

            }
        });
    }

    //Metodo que cambia a activity Menu
    public void Ingresar() {
        Intent ingresar = new Intent(this, MenuActivity.class);
        startActivity(ingresar);
    }

    /* MÉTODO PARA IR AL FORMULARIO DE REGISTRO */
    public void Registrar(View view) {
        Intent registrar = new Intent(this, RegisterActivity.class);
        startActivity(registrar);
    }
}