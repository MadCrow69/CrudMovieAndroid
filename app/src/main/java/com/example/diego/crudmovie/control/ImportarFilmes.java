package com.example.diego.crudmovie.control;


import android.content.Context;
import android.util.Log;


import com.example.diego.crudmovie.dao.FilmeDAO;
import com.example.diego.crudmovie.model.Filme;
import com.example.diego.crudmovie.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ImportarFilmes {
    private String url = "https://api.androidhive.info/json/movies.json";
    private Context context;

    public ImportarFilmes(Context context){
        this.context = context;
    }

    public void Importar() {
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("MEUAPP", response.toString());
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<Movie>>() { }.getType();
                //ArrayList <Filme> filmeGson = new ArrayList();
                ArrayList<Movie> lstMovies = gson.fromJson(response.toString(), listType);
                if (lstMovies != null)
                    ProcessarImportados_new(lstMovies);
            }
        });
    }


    private void ProcessarImportados_new(ArrayList<Movie> lstMovies) {
        Filme filme;
        FilmeDAO dao = new FilmeDAO(context);
        boolean sucesso;
        for (Movie movie : lstMovies) {
            //salvando os dados

            sucesso = dao.salvar(movie.title, movie.image, movie.releaseYear, movie.rating, "TEST");
            if(sucesso) {
                Log.d("MEUAPP", toString());
            }
            else
                Log.d("Falhou Adição", toString());
            }

    }
}
