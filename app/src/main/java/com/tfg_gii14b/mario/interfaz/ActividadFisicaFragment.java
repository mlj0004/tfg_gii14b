package com.tfg_gii14b.mario.interfaz;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mario.gii_14b.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActividadFisicaFragment extends Fragment {

    public ActividadFisicaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actividad_fisica, container, false);
    }
}
