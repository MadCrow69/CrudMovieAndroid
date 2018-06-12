package com.example.diego.crudmovie.control;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.diego.crudmovie.R;

public class FilmeHolder extends RecyclerView.ViewHolder {

    public TextView nomeFilme;
    public TextView textYear;
    public TextView textRating;
    public TextView urlThunbmail;
    public ImageView imageView = itemView.findViewById(R.id.imageView);

    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public FilmeHolder(View itemView) {
        super(itemView);

        nomeFilme = (TextView) itemView.findViewById(R.id.nomeFilme);
        textYear = (TextView) itemView.findViewById(R.id.textYear);
        textRating = (TextView) itemView.findViewById(R.id.textRating);
        //Picasso.get().load(url()).into(imageView);
        Picasso.get().load("https://api.androidhive.info/json/movies/1.jpg").into(imageView);


        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }

    private String url(){
        if (urlThunbmail.toString() == null){
            return "https://api.androidhive.info/json/movies/1.jpg";
        }
        else {
            return urlThunbmail.toString();
        }

    }
}