package com.example.diego.crudmovie.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Filme implements Serializable {

    private int id;
    private String title;
    private String thumbnailUrl;
    private String year;
    private String rating;
    private String genre;
    //public ArrayList<Genero> genre;

    public Filme(int id, String title, String thumbnailUrl, String year, String rating, String genre){
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
    }

    public int getId(){ return this.id; }
    public String getTitle(){ return this.title; }
    public String getThumbnailUrl(){ return this.thumbnailUrl; }
    public String getYear(){ return this.year; }
    public String getRating(){ return this.rating; }
    public String getGenre(){ return this.genre; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Filme)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
