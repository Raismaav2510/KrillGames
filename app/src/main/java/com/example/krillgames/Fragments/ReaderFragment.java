package com.example.krillgames.Fragments;

import static android.content.Context.NOTIFICATION_SERVICE;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

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
            crearCanalNotificacion();
            crearNotificacion();
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

    private void crearCanalNotificacion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void crearNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.krill_logo);
        builder.setContentTitle("AMAZING!");
        builder.setContentText("Your game has been registered in your list");
        builder.setColor(R.drawable.gradient_background_dark);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000, 1000);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
        if(ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}