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
    final private int REQUEST_EXIT = 0;
    final private int REQUEST_EXIT_BOLO = 1;
    private boolean bolo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_glucemias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = misPreferencias.edit();
        bolo = misPreferencias.getBoolean("boloCorrector",false);
        editorPreferencias.putBoolean("boloCorrector",false);
        editorPreferencias.commit();


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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void guardarGlucemiaOnClick(View view){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = misPreferencias.edit();
        String maxtxt = misPreferencias.getString("max", "");
        String mintxt = misPreferencias.getString("min", "");
        Integer min = Integer.parseInt(mintxt);
        Integer max = Integer.parseInt(maxtxt);
        long insertar;

        EditText valor =  (EditText) findViewById(R.id.et_cantidadglucemia);
        String valortxt = valor.getText().toString();

        if(valortxt.equals("")) {
            Toast.makeText(RegistroGlucemias.this, "Introduce un valor de glucemia", Toast.LENGTH_SHORT).show();

        }else{
            Integer cantidadGlucemia = Integer.parseInt(valortxt);
            editorPreferencias.putInt("glucemia", cantidadGlucemia);
            editorPreferencias.commit();


            //Toast.makeText(getApplicationContext(), periodo, Toast.LENGTH_LONG).show();
            DataBaseManager dbmanager = new DataBaseManager(this);
            final Cursor cursorGlucemias = dbmanager.consultarGlucemias();
            int n = cursorGlucemias.getCount();



            insertar = dbmanager.insertar("glucemias", generarContentValues(periodo, cantidadGlucemia));

            if(insertar!=-1){
                Toast.makeText(RegistroGlucemias.this, "Valor de glucemia guardado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RegistroGlucemias.this, "Valor incorrecto, compruebe que ha introducido valores numéricos", Toast.LENGTH_SHORT).show();
            }

            if(bolo==true){
                Intent i= new Intent(this, ActividadFisica.class);
                startActivityForResult(i, REQUEST_EXIT_BOLO);

            }

            if (cantidadGlucemia < min || cantidadGlucemia > max) {
                Intent i = new Intent(this, Incidencias.class);
                i.putExtra("id", insertar);
                i.putExtra("periodo", periodo);
                i.putExtra("valor",cantidadGlucemia);
                i.putExtra("min",min);
                i.putExtra("max", max);
                //startActivity(i);
                startActivityForResult(i,REQUEST_EXIT);
            }else if(bolo==false){
                super.onBackPressed();
            }


            //super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(bolo==true){
            if(requestCode==REQUEST_EXIT_BOLO){
                finish();
            }
        }else if(requestCode == REQUEST_EXIT)
        {
            finish();
        }
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
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



}
