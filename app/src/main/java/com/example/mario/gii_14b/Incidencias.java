package com.example.mario.gii_14b;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mario.persistencia.DataBaseManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Incidencias extends AppCompatActivity {

    final private int RESULT_EXIT=0;
    String incidencia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle =  getIntent().getExtras();
        int valor=bundle.getInt("valor");
        int max = bundle.getInt("max");
        int min = bundle.getInt("min");

        final ArrayAdapter adapter;
        Spinner listaIncidencias = (Spinner) findViewById(R.id.sp_incidencias);

        if(valor>max) {
            adapter = ArrayAdapter.createFromResource(this, R.array.spinnerIncidenciasAlto, android.R.layout.simple_spinner_item);

        }else{
            adapter = ArrayAdapter.createFromResource(this, R.array.spinnerIncidenciasBajo, android.R.layout.simple_spinner_item);

        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaIncidencias.setAdapter(adapter);

        /*listaIncidencias = (Spinner) findViewById(R.id.sp_incidencias);
        ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposIncidencias);
        listaIncidencias.setAdapter(adpTipos);*/

        listaIncidencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = adapter.getItem(position).toString();
                incidencia = opcion;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void incidenciasOnClick(View view){
        EditText observEt = (EditText) findViewById(R.id.et_observaciones);
        String observ = observEt.getText().toString();


            Bundle bundle = getIntent().getExtras();
            Long id = bundle.getLong("id");
            Integer idGlucemia = Integer.valueOf(id.intValue());

            String periodo = bundle.getString("periodo");

            DataBaseManager dbmanager = new DataBaseManager(this);

            long insertar = dbmanager.insertar("incidencias", generarContentValues(incidencia, observ, periodo, idGlucemia));

            if(insertar!=-1){
                Toast.makeText(Incidencias.this, R.string.incidencia_correcta, Toast.LENGTH_SHORT).show();
            }
            setResult(RESULT_EXIT);
            super.onBackPressed();

    }

    public ContentValues generarContentValues(String periodo, String observaciones, String tipo, Integer id ){
        ContentValues valores = new ContentValues();
        String fecha = getDateTime();
        valores.put("fecha",fecha);
        valores.put("glucemia",id);
        valores.put("tipo",periodo);
        valores.put("observaciones", observaciones);
        return valores;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
