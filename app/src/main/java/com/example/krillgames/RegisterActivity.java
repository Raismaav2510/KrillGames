package com.example.krillgames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    //Declaración de componentes
    EditText edtNombre;
    EditText edtApellido;
    EditText edtEdad;
    EditText edtUsuario;
    EditText edtClave;
    Button btnRegistrar;
    Spinner spsexo;

    //Variables globales
    ControladorBD admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Asociación de componentes
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtEdad = (EditText) findViewById(R.id.edtEdad);
        edtUsuario = (EditText) findViewById(R.id.edtUsuarioRegistro);
        edtClave = (EditText) findViewById(R.id.edtContrasenaRegistro);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        spsexo = (Spinner) findViewById(R.id.spSexo);
        admin = new ControladorBD(this, "KrillGames.db", null, 1);

        String[] sexo = {"Select your gender","Female", "Male"};
        ArrayAdapter<String> spSexo = new ArrayAdapter<String>(this, R.layout.spinner_item, sexo);
        spSexo.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spsexo.setAdapter(spSexo);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }

    public void registrarUsuario() {
        if (edtNombre.getText().toString().isEmpty() || edtApellido.getText().toString().isEmpty() || edtClave.getText().toString().isEmpty() || edtUsuario.getText().toString().isEmpty() || spsexo.getSelectedItem().toString().equals("Select your gender")) {
            Toast.makeText(getApplicationContext(), "Datos invalidos", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase bd = admin.getWritableDatabase();
            String nombre = edtNombre.getText().toString();
            String apellido = edtApellido.getText().toString();
            String clave = edtClave.getText().toString();
            String usuario = edtUsuario.getText().toString();
            int edad = Integer.valueOf(edtEdad.getText().toString());
            String genero = spsexo.getSelectedItem().toString();
            ContentValues registro = new ContentValues();
            registro.put("Nombre", nombre);
            registro.put("Apellido", apellido);
            registro.put("Password", clave);
            registro.put("Username", usuario);
            registro.put("Edad", edad);
            registro.put("Gender", genero);
            if(bd != null) {
                long x = 0;
                try {
                    x = bd.insert("Usuario", null, registro);
                } catch(SQLException e) {
                    Log.e("Exception", "Error:"+String.valueOf(e.getMessage()));
                }
                bd.close();
            }
            Toast.makeText(this, "Usuario Registrado Exitosamente", Toast.LENGTH_SHORT).show();
            Intent registrarusuario = new Intent(this, LoginActivity.class);
            startActivity(registrarusuario);
        }
    }
}