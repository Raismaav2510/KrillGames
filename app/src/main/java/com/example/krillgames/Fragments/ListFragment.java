package com.example.krillgames.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.krillgames.ControladorBD;
import com.example.krillgames.R;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    View view;
    List<CardAward> cardAwardList;
    ControladorBD controler;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardAwardList = new ArrayList<>();
        controler = new ControladorBD(getActivity(), "KrillGames.db", null, 1);
        SQLiteDatabase bd = controler.getReadableDatabase();
        Cursor register = bd.rawQuery("select * from Videojuego", null);
        int n = register.getCount();
        int [] img = {R.drawable.splatoon, R.drawable.mario, R.drawable.call, R.drawable.tomb, R.drawable.metroid, R.drawable.mortal};
        if(n > 0) {
            register.moveToFirst();
            int counter = 0;
            do {
                cardAwardList.add(new CardAward("Title: " + register.getString(0), "Company: " + register.getString(1), "Code: " + register.getString(2), "Category: " + register.getString(3), "Price: " + register.getString(4), "Quantity: " + register.getString(5), img[counter]));
                counter++;
            } while(register.moveToNext());
        } else {
            Toast.makeText(getActivity(), "No videogame records", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.rvJuegos);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(container.getContext(), cardAwardList);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 1));
        recyclerView.setAdapter(adapter);
        return view;
    }
}