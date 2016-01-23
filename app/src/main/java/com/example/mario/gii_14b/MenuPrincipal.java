package com.example.mario.gii_14b;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mario.persistencia.DataBaseHelper;


public class MenuPrincipal extends AppCompatActivity {

    String[] opciones = {"Registro de glucemias", "Historial de glucemias"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listaMenu = (ListView) findViewById(R.id.lv_opciones);
        ArrayAdapter<String> adaptlv = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_black_text,opciones);
        listaMenu.setAdapter(adaptlv);

        listaMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent i = new Intent(getApplicationContext(), RegistroGlucemias.class);
                    startActivity(i);
                }else if(position == 1){
                    Intent i = new Intent(getApplicationContext(), Historial.class);
                    startActivity(i);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.action_acerca){
            Toast.makeText(this, "Aplicacion para el seguimiento de la diabetes y el calculo del bolo corrector. " +
                    "Universidad de Burgos. 2015", Toast.LENGTH_LONG).show();
        }else if(id==R.id.action_settings){
            //SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
            //SharedPreferences.Editor editorPreferencias = misPreferencias.edit();
            //editorPreferencias.putBoolean("ajustePerfil",false);
            Intent i = new Intent(this,Perfil.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    public void calcularBoloOnClick(View view){
        Intent paso3 = new Intent(this,Carbohidratos.class);
        Intent paso1 = new Intent(this, RegistroGlucemias.class);
        Intent paso2 = new Intent(this, ActividadFisica.class);

        startActivity(paso3);
        startActivity(paso2);
        startActivity(paso1);


    }
}
