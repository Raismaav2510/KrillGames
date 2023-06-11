package com.example.krillgames.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.krillgames.R;

public class ReaderFragment extends Fragment {

    View view;
    Spinner spdisponibilidad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reader, container, false);
        spdisponibilidad = view.findViewById(R.id.spDisponibilidad);
        String[] dispinibilidad = {"Select availability","Low", "Medium", "High"};
        ArrayAdapter<String> spinnerHead = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dispinibilidad);
        spinnerHead.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spdisponibilidad.setAdapter(spinnerHead);
        return view;
    }
}