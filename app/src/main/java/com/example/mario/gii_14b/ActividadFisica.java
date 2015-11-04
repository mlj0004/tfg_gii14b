package com.example.mario.gii_14b;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ActividadFisica extends AppCompatActivity {
    Spinner listaTipos, listaIntensidad;
    String[] tiposEjer = {"Tipo1","Tipo2","Tipo3"};
    String[] intensidadEjer = {"Intensidad1","Intensidad2","Intensidad3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaTipos = (Spinner) findViewById(R.id.sp_tipoejer);
        ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposEjer);
        listaTipos.setAdapter(adpTipos);

        listaTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaIntensidad = (Spinner) findViewById(R.id.sp_intensidad);
        ArrayAdapter<String> adpIntensidad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,intensidadEjer);
        listaIntensidad.setAdapter(adpIntensidad);

        listaIntensidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void siguienteOnClick(View view){
        super.onBackPressed();
    }

}
