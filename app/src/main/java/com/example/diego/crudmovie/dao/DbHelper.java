package com.example.diego.crudmovie.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.diego.crudmovie.control.ImportarFilmes;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CrudfILME.db";
    private static final int DATABASE_VERSION = 2;
    private final String CREATE_TABLE = "CREATE TABLE Filmes (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT NOT NULL, THUMBNAILURL TEXT, YEAR TEXT, RATING TEXT, GENRE TEXT);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
