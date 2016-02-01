package com.example.mario.gii_14b;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

import com.example.mario.calculos.CalculaBolo;
import com.example.mario.persistencia.DataBaseHelper;
import com.example.mario.persistencia.DataBaseManager;

import java.util.ArrayList;

public class Carbohidratos extends AppCompatActivity {
    Spinner listaComida, listaTipo;
    private final int RESULT_EXIT=0;

    String comida;

    int sumatorioRaciones;

    //public static final String[] tipoAlimento={"Arroz","Arroz","Arroz","Fruta","Fruta","Fruta","Legumbre","Legumbre","Legumbre","Lacteo","Lacteo","Lacteo"};
    private String[] tipoAlimento;
    private String[] alimento;
    //public static final String[] alimento={"Arroz blanco","Arroz instantaneo", "Arroz integral", "Fresas","Platano","Melocoton",
      //                                      "Garbanzos","Judias","Lentejas","Helado","Leche entera","Yogur"};
    public static final int[] raciones={150,150,150,120,120,120,150,150,150,50,250,200};
    public static final int[] cargas={14,16,19,1,12,4,3,3,3,8,3,3};


    ArrayList<String> arrayAlimentos = new ArrayList<String>();
    ArrayList<Integer> arrayRaciones = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbohidratos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText carboEt = (EditText) findViewById(R.id.et_carbo);
        carboEt.setText("0");

        rellenarTablaAlimentos();

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


        final ArrayAdapter adpTipoAlimento = ArrayAdapter.createFromResource(this,R.array.spinerTipoAlimento,android.R.layout.simple_spinner_item);
        final ArrayAdapter adpArroz = ArrayAdapter.createFromResource(this, R.array.spinerArroz, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpFruta = ArrayAdapter.createFromResource(this, R.array.spinerFruta, android.R.layout.simple_spinner_item);
        final ArrayAdapter adpLegumbre = ArrayAdapter.createFromResource(this,R.array.spinerLegumbre,android.R.layout.simple_spinner_item);
        final ArrayAdapter adpLacteo = ArrayAdapter.createFromResource(this,R.array.spinerLacteo,android.R.layout.simple_spinner_item);

        listaComida = (Spinner) findViewById(R.id.sp_comidas);
        //ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tiposComida);
        //final ArrayAdapter<String> adpTipos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listaAlimentos);
        //final ArrayAdapter adpTipoAlimento = ArrayAdapter.createFromResource(this, R.array.spinnerAlimento, android.R.layout.simple_spinner_item);
        //listaComida.setAdapter(adpTipoAlimento);
        listaComida.setAdapter(adpTipoAlimento);

        listaTipo =(Spinner) findViewById(R.id.spiner_tipo);

        listaComida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String opcion = adpTipos.getItem(position).toString();
                //comida = opcion;
                if(position==0){
                    listaTipo.setAdapter(adpArroz);
                }else if(position==1){
                    listaTipo.setAdapter(adpFruta);
                }else if (position==2){
                    listaTipo.setAdapter(adpLegumbre);
                }else if (position==3){
                    listaTipo.setAdapter(adpLacteo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listaTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comida = listaTipo.getAdapter().getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void rellenarTablaAlimentos(){
        tipoAlimento = getResources().getStringArray(R.array.arrayTipoAlimento);
        alimento = getResources().getStringArray(R.array.arrayAlimentos);

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

    public void añadirOtroOnClick(View view) {
        EditText carboEt = (EditText) findViewById(R.id.et_carbo);
        String carbo = carboEt.getText().toString();
        int nracion = Integer.parseInt(carbo);
        carboEt.setText("0");

        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorGlucemias = dbmanager.selectAlimento(comida);
        if (cursorGlucemias.moveToFirst()) {
            String n = cursorGlucemias.getString(3);
            sumatorioRaciones+=Integer.parseInt(n)*nracion;

        }


    }

    public void finalizarOnClick(View view){

        EditText carboEt = (EditText) findViewById(R.id.et_carbo);
        String carbo = carboEt.getText().toString();
        int nracion = Integer.parseInt(carbo);

        DataBaseManager dbmanager = new DataBaseManager(this);
        final Cursor cursorGlucemias = dbmanager.selectAlimento(comida);

        if (cursorGlucemias.moveToFirst()) {
            String n = cursorGlucemias.getString(3);
            sumatorioRaciones+=Integer.parseInt(n)*nracion;
        }


            SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);

            String uds1txt = misPreferencias.getString("uds1", "");
            String uds2txt = misPreferencias.getString("uds2", "");
            String tipoEjer = misPreferencias.getString("tipoEjer", "");
            int uds1 = Integer.parseInt(uds1txt);
            int uds2 = Integer.parseInt(uds2txt);
            int insulinaBasal = uds1 + uds2;
            int ultimaMedicion = misPreferencias.getInt("glucemia", 0);

            CalculaBolo cb = new CalculaBolo(ultimaMedicion,insulinaBasal,sumatorioRaciones,tipoEjer);

            double boloR=cb.calculoBoloCorrector();

            String comentarioFinal;
            if(boloR>=0) {
                comentarioFinal = getString(R.string.resultado_bolo) + Double.toString(boloR);
            }else{

                comentarioFinal = getString(R.string.ingerir_carbohidratos);
                }


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(comentarioFinal)
                    .setTitle(getString(R.string.bolo))
                    .setCancelable(false)
                    .setNeutralButton(getString(R.string.aceptar),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    setResult(RESULT_EXIT);
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();



    }

}
