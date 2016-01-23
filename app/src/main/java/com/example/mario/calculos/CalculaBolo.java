package com.example.mario.calculos;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

import com.example.mario.persistencia.DataBaseManager;

import java.util.ArrayList;

/**
 * Created by Mario on 13/12/2015.
 */
public class CalculaBolo extends AppCompatActivity {

    private static final int valorObjetivo = 100;

    int ultimaMedicion;
    int insulinaBasal;

    int raciones;
    String tipoEjercicio;

    public CalculaBolo(int ultimaMedicion, int insulinaTotal, int sumatorio, String ejercicio){
        this.ultimaMedicion=ultimaMedicion;
        this.insulinaBasal=insulinaTotal;
        this.raciones=sumatorio;
        this.tipoEjercicio=ejercicio;
    }

    public double calculaRatio(){

        return 500/insulinaBasal;
    }

    public double calculaFactorSensibilidad(){

        double dosisTresDias=insulinaBasal*3;

        return 1800/dosisTresDias;
    }

    public double calculoBoloCorrector(){
        double ratio=calculaRatio();
        double insulinaPorGramo=0;


        if(raciones>0){
            insulinaPorGramo=raciones/ratio;
            insulinaPorGramo=Math.floor(insulinaPorGramo);
        }

        double factorSensibilidad = calculaFactorSensibilidad();
        double bolo=  (ultimaMedicion-valorObjetivo)/factorSensibilidad;
        bolo=bolo+insulinaPorGramo;

        return Math.floor(bolo);
    }





}
