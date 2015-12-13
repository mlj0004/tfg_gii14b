package com.example.mario.gii_14b;

import android.content.ContentValues;
import android.database.Cursor;
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

import com.example.mario.persistencia.DataBaseHelper;
import com.example.mario.persistencia.DataBaseManager;

public class Carbohidratos extends AppCompatActivity {
    Spinner listaComida;
    String[] tiposComida = {"Tipo1","Tipo2","Tipo3"};

    public static final String[] tipoAlimento={"Arroz","Arroz","Arroz","Fruta","Fruta","Fruta","Legumbre","Legumbre","Legumbre","Lacteo","Lacteo","Lacteo"};
    public static final String[] alimento={"Arroz blanco","Arroz instantaneo", "Arroz integral", "Fresas","Platano","Melocoton",
                                            "Garbanzos","Judias","Lentejas","Helado","Leche entera","Yogur"};
    public static final int[] raciones={150,150,150,120,120,120,150,150,150,50,250,200};
    public static final int[] cargas={14,16,19,1,12,4,3,3,3,8,3,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbohidratos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //rellenarTablaAlimentos();

        DataBaseManager dbmanager= new DataBaseManager(this);
        final Cursor cursorAlimentos = dbmanager.consultarAlimentos();
        int count=cursorAlimentos.getCount();
        int i=0;
        String[] listaAlimentos=new String[count];
        if(cursorAlimentos.moveToFirst()){
            do{
                listaAlimentos[i]=cursorAlimentos.getString(1);
                i++;
            }while(cursorAlimentos.moveToNext());
        }

        listaComida = (Spinner) findViewById(R.id.sp_comidas);
        //ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposComida);
        ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listaAlimentos);
        listaComida.setAdapter(adpTipos);

        listaComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    int cantidad=cursorAlimentos.getCount();
                    Toast.makeText(getApplicationContext(), "Alimentos en tabla "+String.valueOf(cantidad), Toast.LENGTH_LONG).show();
                }else if(position==2){
                    int n=alimento.length;
                    Toast.makeText(getApplicationContext(), String.valueOf(n), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void rellenarTablaAlimentos(){
        DataBaseManager dbmanager= new DataBaseManager(this);
        for(int i=0;i<alimento.length;++i){
            ContentValues values = new ContentValues();
            values.put("tipoAlimento",tipoAlimento[i]);
            values.put("alimento",alimento[i]);
            values.put("racion",raciones[i]);
            values.put("carga", cargas[i]);
            dbmanager.insertar("alimentos",values);
        }
    }

    public void finalizarOnClick(View view){
        super.onBackPressed();
    }

}
