package com.example.mario.gii_14b;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class RegistroGlucemias extends AppCompatActivity {
    //Spinner lista;
    /*String[] opciones = {"Selecciona una de las opciones","Antes de desayunar","Después de desayunar",
                            "Antes de almorzar", "Despues de almorzar", "Antes de comer",
                            "Después de comer", "Antes de merendar", "Después de merendar",
                            "Antes de cenar", "Después de cenar"};*/
    private String periodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_glucemias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner listaPeriodos = (Spinner) findViewById(R.id.sp_glucemias);
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinnerPeriodo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listaPeriodos.setAdapter(adapter);


        /*lista = (Spinner) findViewById(R.id.sp_glucemias);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);

        lista.setAdapter(adapterSpinner);*/

        listaPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    String opcion = adapter.getItem(position).toString();
                    periodo = opcion;

                    Toast.makeText(getApplicationContext(), opcion, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void guardarGlucemiaOnClick(View view){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);
        String maxtxt = misPreferencias.getString("max", "");
        String mintxt = misPreferencias.getString("min", "");
        Integer min = Integer.parseInt(mintxt);
        Integer max = Integer.parseInt(maxtxt);
        long insertar;

        EditText valor =  (EditText) findViewById(R.id.et_cantidadglucemia);
        String valortxt = valor.getText().toString();
        Integer cantidadGlucemia = Integer.parseInt(valortxt);



        //Toast.makeText(getApplicationContext(), periodo, Toast.LENGTH_LONG).show();
        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorGlucemias = dbmanager.consultarGlucemias();
        int n = cursorGlucemias.getCount();


        insertar=dbmanager.insertar("glucemias",generarContentValues(periodo,cantidadGlucemia));
        if(insertar==-1){
            Toast.makeText(this, "No se ha realizado la inserccion correctamente", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Insercion realizada correctamente"+Integer.toString(n), Toast.LENGTH_LONG).show();
        }

        if(cantidadGlucemia<min || cantidadGlucemia>max){
            Intent i = new Intent(this,Incidencias.class);
            i.putExtra("id", insertar);
            i.putExtra("periodo",periodo);
            startActivity(i);
        }

        super.onBackPressed();
    }

    public ContentValues generarContentValues(String periodo, Integer valor){
        ContentValues valores = new ContentValues();
        String fecha = getDateTime();
        valores.put("fecha",fecha);
        valores.put("periodo",periodo);
        valores.put("valor",valor);
        return valores;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



}
