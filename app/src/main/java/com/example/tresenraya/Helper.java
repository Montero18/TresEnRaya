package com.example.tresenraya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BDJuego.db";
    public static final String SQL_CREATE_ENTRIES_PARTIDAS = "CREATE TABLE IF NOT EXISTS usuarios(_ID  integer PRIMARY KEY, numpartidas integer, puntos integer);";
    public static final String SQL_DELETE_ENTRIES_PARTIDAS = "DROP TABLE IF EXISTS partidas";
    public static final String SQL_CREATE_ENTRIES_USUARIOS = "CREATE TABLE IF NOT EXISTS partidas(_ID  integer PRIMARY KEY, nombre text, jugador2 text, dificultad text, resultado text);";
    public static final String SQL_DELETE_ENTRIES_USUARIOS = "DROP TABLE IF EXISTS usuarios";

    public Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_PARTIDAS);
        db.execSQL(SQL_CREATE_ENTRIES_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL(SQL_DELETE_ENTRIES_PARTIDAS);
        //Se crea la nueva versión de la tabla
        db.execSQL(SQL_CREATE_ENTRIES_PARTIDAS);
        //Se elimina la versión anterior de la tabla
        db.execSQL(SQL_DELETE_ENTRIES_USUARIOS);
        //Se crea la nueva versión de la tabla
        db.execSQL(SQL_CREATE_ENTRIES_USUARIOS);
    }

}
