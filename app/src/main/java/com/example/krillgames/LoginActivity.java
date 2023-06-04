package com.example.krillgames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /* MÉTODO PARA INGRESAR A LA APLICACIÓN */
    public void Ingresar(View view) {
        Intent ingresar = new Intent(this, MenuActivity.class);
        startActivity(ingresar);
    }

    /* MÉTODO PARA IR AL FORMULARIO DE REGISTRO */
    public void Registrar(View view) {
        Intent registrar = new Intent(this, RegisterActivity.class);
        startActivity(registrar);
    }
}