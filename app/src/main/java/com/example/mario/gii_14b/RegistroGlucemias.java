package com.example.mario.gii_14b;

import android.content.Intent;
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

public class RegistroGlucemias extends AppCompatActivity {
    Spinner lista;
    String[] opciones = {"Selecciona una de las opciones","Antes de desayunar","Después de desayunar",
                            "Antes de almorzar", "Despues de almorzar", "Antes de comer",
                            "Después de comer", "Antes de merendar", "Después de merendar",
                            "Antes de cenar", "Después de cenar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_glucemias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (Spinner) findViewById(R.id.sp_glucemias);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        lista.setAdapter(adapterSpinner);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    Toast.makeText(getApplicationContext(), opciones[position], Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void guardarGlucemiaOnClick(View view){
        super.onBackPressed();
    }

}
