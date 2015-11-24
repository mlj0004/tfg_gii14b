package com.example.mario.gii_14b;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        cargarPreferencias();
    }

    public void cargarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt=(EditText) findViewById(R.id.et_edad);
        EditText estaturaEt=(EditText) findViewById(R.id.et_estatura);
        EditText pesoEt=(EditText) findViewById(R.id.et_peso);
        EditText maxEt=(EditText) findViewById(R.id.et_max);
        EditText minEt=(EditText) findViewById(R.id.et_min);
        EditText uds1Et=(EditText) findViewById(R.id.et_uds1);
        EditText uds2Et=(EditText) findViewById(R.id.et_uds2);
        CheckBox determirCheck = (CheckBox) findViewById(R.id.cb_determir);
        CheckBox glarginaCheck = (CheckBox) findViewById(R.id.cb_glargina);

        nombreEt.setText(misPreferencias.getString("nombre",""));
        edadEt.setText(misPreferencias.getString("edad",""));
        estaturaEt.setText(misPreferencias.getString("estatura",""));
        pesoEt.setText(misPreferencias.getString("peso",""));
        minEt.setText(misPreferencias.getString("min",""));
        maxEt.setText(misPreferencias.getString("max",""));
        uds1Et.setText(misPreferencias.getString("uds1",""));
        uds2Et.setText(misPreferencias.getString("uds2", ""));
        determirCheck.setChecked(misPreferencias.getBoolean("determir", false));
        glarginaCheck.setChecked(misPreferencias.getBoolean("glargina",false));
    }

    public void guardarperfilOnClick(View view){
        //EditText nombreEt, edadEt, estaturaEt, pesoEt, maxEt, minEt, uds1Et, uds2Et;
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = misPreferencias.edit();

        EditText nombreEt = (EditText) findViewById(R.id.et_nombre);
        EditText edadEt=(EditText) findViewById(R.id.et_edad);
        EditText estaturaEt=(EditText) findViewById(R.id.et_estatura);
        EditText pesoEt=(EditText) findViewById(R.id.et_peso);
        EditText maxEt=(EditText) findViewById(R.id.et_max);
        EditText minEt=(EditText) findViewById(R.id.et_min);
        EditText uds1Et=(EditText) findViewById(R.id.et_uds1);
        EditText uds2Et=(EditText) findViewById(R.id.et_uds2);
        CheckBox determirCheck = (CheckBox) findViewById(R.id.cb_determir);
        CheckBox glarginaCheck = (CheckBox) findViewById(R.id.cb_glargina);

        String nombre = nombreEt.getText().toString();
        String edad = edadEt.getText().toString();
        String estatura = estaturaEt.getText().toString();
        String peso = pesoEt.getText().toString();
        String max = maxEt.getText().toString();
        String min = minEt.getText().toString();
        String uds1 = uds1Et.getText().toString();
        String uds2 = uds2Et.getText().toString();
        Boolean determir = determirCheck.isChecked();
        Boolean glargina = glarginaCheck.isChecked();

        editorPreferencias.putBoolean("primeraEjecucion",true);
        editorPreferencias.putString("nombre", nombre);
        editorPreferencias.putString("edad", edad);
        editorPreferencias.putString("estatura", estatura);
        editorPreferencias.putString("peso", peso);
        editorPreferencias.putString("max", max);
        editorPreferencias.putString("min", min);
        editorPreferencias.putString("uds1", uds1);
        editorPreferencias.putString("uds2", uds2);
        editorPreferencias.putBoolean("determir", determir);
        editorPreferencias.putBoolean("glargina", glargina);

        editorPreferencias.commit();



        //Intent i = new Intent(this, MenuPrincipal.class);
        //startActivity(i);
        finish();

    }
}
