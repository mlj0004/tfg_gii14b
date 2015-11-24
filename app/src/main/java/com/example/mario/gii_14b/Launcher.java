package com.example.mario.gii_14b;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);
        Boolean arranque = misPreferencias.getBoolean("primeraEjecucion", false);
        Intent perfil = new Intent(this, Perfil.class);
        Intent menuPrincipal = new Intent(this, MenuPrincipal.class);

        if(arranque==false){
            startActivity(menuPrincipal);
            startActivity(perfil);

        }else{

            startActivity(menuPrincipal);
        }
        finish();
    }
}
