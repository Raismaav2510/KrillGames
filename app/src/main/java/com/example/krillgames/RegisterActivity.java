package com.example.krillgames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /* MÉTODO PARA REGISTRAR USUARIO */
    public void registrarUsuario(View view) {
        Intent registrarusuario = new Intent(this, LoginActivity.class);
        startActivity(registrarusuario);
    }
}