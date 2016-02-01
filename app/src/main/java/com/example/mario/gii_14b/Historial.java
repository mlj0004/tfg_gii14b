package com.example.mario.gii_14b;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mario.persistencia.DataBaseHelper;
import com.example.mario.persistencia.DataBaseManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Historial extends AppCompatActivity implements OnChartValueSelectedListener {

    ArrayList<Entry> entriesDesayuno, entriesComida, entriesCena;
    ArrayList<String> labels;
    int valMax, valMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
        valMax=Integer.parseInt(misPreferencias.getString("max",""));
        valMin=Integer.parseInt(misPreferencias.getString("min",""));

        inicializeChart();

    }


    public void inicializeChart(){
        //Generamos los entries con los valores de la última semana para cada periodo
        entriesDesayuno = new ArrayList<Entry>(generarCursores(generaFechas(),getString(R.string.leyenda_desayuno)));
        entriesComida = new ArrayList<Entry>(generarCursores(generaFechas(),getString(R.string.leyenda_comida)));
        entriesCena = new ArrayList<Entry>(generarCursores(generaFechas(), getString(R.string.leyenda_cena)));

        //Array que contiene las etiquetas para el eje X
        labels = new ArrayList<String>(generaFechas());

        //Inicializamos los set de datos para cada periodo, incluyendo su leyenda
        LineData datasetline = new LineData(labels);
        LineDataSet setDesayuno = new LineDataSet(entriesDesayuno,getString(R.string.leyenda_desayuno));
        LineDataSet setComida = new LineDataSet(entriesComida,getString(R.string.leyenda_comida));
        LineDataSet setCena = new LineDataSet(entriesCena,getString(R.string.leyenda_cena));
        setComida.setCircleColor(Color.GREEN);
        setComida.setColor(Color.GREEN);
        setCena.setCircleColor(Color.RED);
        setCena.setColor(Color.RED);
        datasetline.addDataSet(setDesayuno);
        datasetline.addDataSet(setComida);
        datasetline.addDataSet(setCena);



        RelativeLayout rl = new RelativeLayout(getApplicationContext());
        LineChart mChart = new LineChart(getApplicationContext());
        //rl.addView(mChart);
        rl.addView(
                mChart,
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(rl);




        //customize line chart
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No data for the moment");


        //enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setOnChartValueSelectedListener(this);

        //we want also enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);

        //enable pinch zoom to avoid scaling x and y axis separately
        mChart.setPinchZoom(true);

        //alternative background color
        mChart.setBackgroundColor(Color.WHITE);


        //add data to line chart
        mChart.setData(datasetline);

        //get legend object
        Legend l = mChart.getLegend();

        //customize legend
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLACK);

        XAxis x1 = mChart.getXAxis();
        x1.setTextColor(Color.BLACK);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);


        YAxis y1 = mChart.getAxisLeft();
        y1.setTextColor(Color.BLACK);
        y1.setAxisMaxValue(250f);
        y1.setDrawGridLines(true);

        y1.setDrawLimitLinesBehindData(true);
        LimitLine min = new LimitLine(valMin,"min");
        LimitLine max = new LimitLine(valMax,"max");
        y1.addLimitLine(min);
        y1.addLimitLine(max);

        YAxis y12 = mChart.getAxisRight();
        y12.setEnabled(false);



        mChart.setDescription(getString(R.string.leyenda_chart));
    }



    public ArrayList<Entry> generarCursores(ArrayList<String> fechas, String periodo){
        ArrayList<Entry> entries = new ArrayList<Entry>();
        DataBaseManager dbmanager = new DataBaseManager(this);
        for(int i=0;i<7;i++){
            Cursor cursorDesayuno = dbmanager.selectGlucemia(fechas.get(i),periodo);
            if(cursorDesayuno.moveToLast()){
                entries.add(new BarEntry(Integer.parseInt(cursorDesayuno.getString(3)),i));
            }
        }
        return entries;
    }

    public ArrayList<String> generaFechas(){
        ArrayList<String> fechas = new ArrayList<>();
        for(int i=0;i<7;i++){
            if(i==6){
                fechas.add(i,getFechaActual());
            }else{
                Date d = stringToDate(getFechaActual());
                fechas.add(i,restarFechasDias(d,6-i).toString());
            }
        }
        return fechas;
    }

    //Metodo usado para obtener la fecha actual

    public static String getFechaActual(){
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    //Restarle dias a una fecha determinada

    public static synchronized String restarFechasDias(java.util.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        Date ahora = new Date(cal.getTimeInMillis());
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    //Devuele un java.util.Date desde un String en formato dd-MM-yyyy

    public static synchronized java.util.Date stringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        try {
            fechaEnviar = formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        int posicion = entry.getXIndex();
        int val = (int)entry.getVal();
        String fecha = labels.get(posicion);
        DataBaseManager dbmanager = new DataBaseManager(this);
        if(val<valMin||val>valMax){
            Cursor cursor = dbmanager.selectGlucemiaValor(fecha,val);
            if(cursor.moveToLast()){
                String id = cursor.getString(0);
                Cursor cursorIncidencia = dbmanager.selectIncidencia(Integer.parseInt(id));
                if(cursorIncidencia.moveToFirst()) {
                    String incidencia = cursorIncidencia.getString(1);
                    String observaciones = cursorIncidencia.getString(2);
                    if (!observaciones.equals("")) {
                        Toast.makeText(getApplicationContext(), "Incidencia: " + incidencia + "."
                                + "Observaciones: " + observaciones, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incidencia: " + incidencia + ". No se registraron observaciones para esta incidencia"
                                , Toast.LENGTH_LONG).show();
                    }

                }

            }
        }else{
            Toast.makeText(getApplicationContext(),R.string.sinIncidencia,Toast.LENGTH_LONG).show();
        }



        /*ArrayList<Entry> entriesSelected;
        if(i==0){
            entriesSelected=new ArrayList<>(entriesDesayuno);
        }*/


    }

    @Override
    public void onNothingSelected() {

    }
}




