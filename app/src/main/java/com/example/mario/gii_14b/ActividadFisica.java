package com.example.mario.gii_14b;

import android.content.SharedPreferences;
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

    private String tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_fisica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaTipos = (Spinner) findViewById(R.id.sp_tipoejer);
        final ArrayAdapter adpTipos = ArrayAdapter.createFromResource(this,R.array.spinnerIntensidad, android.R.layout.simple_spinner_item);
        listaTipos.setAdapter(adpTipos);
        final ArrayAdapter adpLarga = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidadLarga, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpMedia = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidaMedia, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpIrregular = ArrayAdapter.createFromResource(this, R.array.spinnerIntensidadIrregular, android.R.layout.simple_spinner_item);
        listaIntensidad = (Spinner) findViewById(R.id.sp_intensidad);



        listaTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = adpTipos.getItem(position).toString();
                tipo = opcion;
                if(position==1){
                    listaIntensidad.setAdapter(adpLarga);
                }else if(position==2){
                    listaIntensidad.setAdapter(adpMedia);
                }else if (position==3){
                    listaIntensidad.setAdapter(adpIrregular);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    public void siguienteOnClick(View view){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("tipoEjer",tipo);
        editor.commit();

        super.onBackPressed();
    }

}
