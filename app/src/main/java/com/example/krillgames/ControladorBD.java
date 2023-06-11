package com.example.krillgames;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ControladorBD extends SQLiteOpenHelper {

    public ControladorBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase nombreBD) {
        String sqlUsuarios = "create table Usuario (IDU INTEGER PRIMARY KEY AUTOINCREMENT, Nombre text, Apellido text, Password text, Username text, Edad int, Gender text)";
        String sqlVideojuego = "create table Videojuego (Titulo text, Compania text, Codigo text, Categoria texto, Precio int, Cantidad int)";
        String sqlCompras = "create table Compras (IDC INTEGER PRIMARY KEY AUTOINCREMENT, idu INTEGER REFERENCES Usuario(IDU) ON UPDATE CASCADE ON DELETE CASCADE, idv INTEGER REFERENCES Videojuego(IDV) ON UPDATE CASCADE ON DELETE CASCADE)";
        String sqlSucursal = "create table Sucursal (IDS INTEGER PRIMARY KEY AUTOINCREMENT, Nombre text, Direccion text, Ubicacion text, Telefono int)";
        String sqlRuta = "create table Ruta (IDR INTEGER PRIMARY KEY AUTOINCREMENT, Distancia int, tiempo int,idu INTEGER REFERENCES Usuario(IDU) ON UPDATE CASCADE ON DELETE CASCADE, ids INTEGER REFERENCES Sucursal(IDS) ON UPDATE CASCADE ON DELETE CASCADE )";
        try {
            nombreBD.execSQL(sqlUsuarios);
            nombreBD.execSQL(sqlVideojuego);
            nombreBD.execSQL(sqlCompras);
            nombreBD.execSQL(sqlSucursal);
            nombreBD.execSQL(sqlRuta);
        } catch (SQLException e) {
            Toast.makeText(null, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

