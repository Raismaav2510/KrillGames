package com.example.krillgames.Fragments;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.krillgames.ControladorBD;
import com.example.krillgames.R;
import com.google.zxing.integration.android.IntentIntegrator;

public class ReaderFragment extends Fragment {

    View view;
    Spinner spCategory;
    EditText title, comapny, code, price, quantity;
    ControladorBD controler;
    Button scan, register;
    String codes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        codes = getActivity().getIntent().getStringExtra("code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reader, container, false);
        spCategory = view.findViewById(R.id.spCategoria);
        title = view.findViewById(R.id.edtTituloJuego);
        comapny = view.findViewById(R.id.edtCompania);
        code = view.findViewById(R.id.edtCodigo);
        price = view.findViewById(R.id.edtPrecio);
        quantity = view.findViewById(R.id.edtCantidad);
        scan = view.findViewById(R.id.btnEscanear);
        register = view.findViewById(R.id.btnGuardar);
        controler = new ControladorBD(getActivity(), "KrillGames.db", null, 1);
        String[] categoria = {"Select the game category", "History", "Action", "Adventure", "Platform", "RPG", "Curtain Fire"};
        ArrayAdapter<String> spinner_category = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, categoria);
        spinner_category.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spCategory.setAdapter(spinner_category);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerVideogame();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
        code.setText(codes);
        return view;
    }

    public void registerVideogame() {
        SQLiteDatabase bd = controler.getWritableDatabase();
        String namegame = title.getText().toString();
        String comanygame = comapny.getText().toString();
        String codegame = code.getText().toString();
        String categorygame = spCategory.getSelectedItem().toString();
        int pricegame = Integer.parseInt(price.getText().toString());
        int quantitygame = Integer.parseInt(quantity.getText().toString());
        if(!namegame.isEmpty() && !comanygame.isEmpty() && !codegame.isEmpty() && !categorygame.isEmpty() && !price.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()) {
            ContentValues register_videogame = new ContentValues();
            register_videogame.put("Titulo", namegame);
            register_videogame.put("Compania", comanygame);
            register_videogame.put("Codigo", codegame);
            register_videogame.put("Categoria", categorygame);
            register_videogame.put("Precio", pricegame);
            register_videogame.put("Cantidad", quantitygame);
            if (bd != null) {
                long x = 0;
                try {
                    x = bd.insert("Videojuego", null, register_videogame);
                } catch (SQLException e) {
                    Log.e("Exception", "Error:" + String.valueOf(e.getMessage()));
                }
                bd.close();
            }
            title.setText("");
            comapny.setText("");
            code.setText("");
            spCategory.setSelection(0);
            price.setText("");
            quantity.setText("");
            title.requestFocus();
            Toast.makeText(getActivity(), "Registered Videogame", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Record the data first", Toast.LENGTH_SHORT).show();
        }
    }

    public void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - CDP");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }
}