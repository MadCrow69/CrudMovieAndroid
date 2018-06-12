package com.example.diego.crudmovie.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import com.example.diego.crudmovie.model.Filme;

public class FilmeDAO {

    private final String TABLE_CLIENTES = "Filmes";
    private DbGateway gw;

    public FilmeDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public List<Filme> retornarTodos(){
        List<Filme> filmes = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Filmes", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String title = cursor.getString(cursor.getColumnIndex("TITLE"));
            String thumbnailurl = cursor.getString(cursor.getColumnIndex("THUMBNAILURL"));
            String year = cursor.getString(cursor.getColumnIndex("YEAR"));
            String rating = cursor.getString((cursor.getColumnIndex("RATING")));
            String genre = cursor.getString(cursor.getColumnIndex("GENRE"));

            filmes.add(new Filme(id, title, thumbnailurl, year, rating, genre));
        }
        cursor.close();
        return filmes;
    }

    public Filme retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Filmes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String title = cursor.getString(cursor.getColumnIndex("TITLE"));
            String thumbnailurl = cursor.getString(cursor.getColumnIndex("THUMBNAILURL"));
            String year = cursor.getString(cursor.getColumnIndex("YEAR"));
            String rating = cursor.getString(cursor.getColumnIndex("RATING"));
            String genre = cursor.getString(cursor.getColumnIndex("GENRE"));

            cursor.close();
            return new Filme(id, title, thumbnailurl, year, rating, genre);
        }

        return null;
    }

    public boolean salvar(String title, String thumbnailUrl, String year, String rating, String genre){
        return salvar(0, title, thumbnailUrl, year, rating, genre);
    }


    public boolean salvar(int id, String title, String thumbnailUrl, String year, String rating, String genre){
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("THUMBNAILURL", thumbnailUrl);
        cv.put("YEAR", year);
        cv.put("RATING", rating);
        cv.put("GENRE", genre);
        if(id > 0)
            return gw.getDatabase().update(TABLE_CLIENTES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CLIENTES, "ID=?", new String[]{ id + "" }) > 0;
    }
}
