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

public class Carbohidratos extends AppCompatActivity {
    Spinner listaComida;
    String[] tiposComida = {"Tipo1","Tipo2","Tipo3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbohidratos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaComida = (Spinner) findViewById(R.id.sp_tipoejer);
        ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposComida);
        listaComida.setAdapter(adpTipos);

        listaComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void finalizarOnClick(View view){
        super.onBackPressed();
    }

}
