package com.example.mario.persistencia;

/**
 * Created by Mario on 24/11/2015.
 */
public class DataBaseManager {

    public static final String CREATE_GLUCEMIAS = "create table glucemias("
            +"fecha integer primary key,"
            +"periodo text not null,"
            +"valor int not null);";
    public static final String CREATE_INCIDENCIAS = "create table incidencias("
            +"id integer primary key autoincrement,"
            +"tipo text not null,"
            +"observaciones text not null,"
            +"fecha integer not null,"
            +"FOREING KEY(fecha) REFERENCES glucemias(fecha);";

    public static final String CREATE_ALIMENTOS = "create table alimentos("
            +"tipoAlimento text not null,"
            +"alimento text primary key,"
            +"carga int not null);";

}
