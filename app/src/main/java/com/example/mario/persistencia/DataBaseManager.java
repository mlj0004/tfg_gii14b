package com.example.mario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mario on 20/11/2015.
 */
public class DataBaseManager {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();

    }

    public static final String CREATE_GLUCEMIAS = "create table glucemias("
            +"id integer primary key autoincrement,"
            +"fecha text not null,"
            +"periodo text not null,"
            +"valor int not null);";
    public static final String CREATE_INCIDENCIAS = "create table incidencias("
            +"id integer primary key autoincrement,"
            +"tipo text not null,"
            +"observaciones text not null,"
            +"fecha text not null,"
            +"glucemia integer not null)";

    public static final String CREATE_ALIMENTOS = "create table alimentos("
            +"tipoAlimento text not null,"
            +"alimento text primary key,"
            +"racion int not null,"
            +"carga int not null);";


    /*static String[] alimentos={"Arroz", "Fruta", "Legumbre"};
    static String[] alimento={"ArrozBlanco", "Fresas", "Melocoton"};*/

    /*public static final String FILL_ALIMENTOS = "INSERT INTO alimentos (tipoAlimento, alimento, racion, carga) VALUES" +
            "    (Arroz, Arroz blanco, 150, 14)," +
            "    (Arroz, Arroz cajun, 150, 19)," +
            "    (Arroz, Arroz integral, 150, 16)," +
            "    (Arroz, Arroz instantaneo, 150, 19)," +
            "    (Frutas, Fresas, 120, 1)," +
            "    (Frutas, Melocoton, 120, 4)," +
            "    (Frutas, Platano, 120, 12)," +
            "    (Frutas, Uvas, 120, 7)," +
            "    (Legumbres, Garbanzos, 150, 3)," +
            "    (Legumbres, Judias, 150, 3)," +
            "    (Legumbres, Lentejas, 150, 3)," +
            "    (Lacteos, Helado, 50, 8)," +
            "    (Lacteos, Leche entera, 250, 3)," +
            "    (Lacteos, Yogur, 200, 3);";*/




    public long insertar(String tabla, ContentValues valores){
        long result;
        result= db.insert(tabla,null,valores);

        return result;
    }

    public Cursor consultarAlimentos(){
        return db.rawQuery("select * from Alimentos",null);
    }

    public Cursor selectAlimento(String id){
        return db.rawQuery("select * from Alimentos where Alimento='"+id+"'",null);
    }


    String columnas[]=new String[]{"id","fecha","periodo","valor"};
    public Cursor consultarGlucemias(){
        return db.query("Glucemias",columnas,null,null,null,null,null);
    }

    public Cursor selectGlucemia(String fecha, String periodo){
        return db.rawQuery("select * from Glucemias where Fecha='"+fecha+"' and Periodo='"+periodo+"'",null);
    }

}
