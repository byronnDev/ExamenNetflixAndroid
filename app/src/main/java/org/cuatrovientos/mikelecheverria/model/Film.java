package org.cuatrovientos.mikelecheverria.model;

import org.cuatrovientos.mikelecheverria.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Film extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String synopsis;
    private String creator;
    private String actors;
    private int image;
    private int imageDetail;
    private boolean isFavorite;
    private boolean isPopular;
    private boolean isBeingWatched;

    // Constructor
    public Film(String title, String synopsis, String creator, String actors,
                int image, int imageDetail, boolean isFavorite,
                boolean isPopular, boolean isBeingWatched) {
        this.id = MyApplication.filmID.incrementAndGet();
        this.title = title;
        this.synopsis = synopsis;
        this.creator = creator;
        this.actors = actors;
        this.image = image;
        this.imageDetail = imageDetail;
        this.isFavorite = isFavorite;
        this.isPopular = isPopular;
        this.isBeingWatched = isBeingWatched;
    }

    public Film(){}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(int imageDetail) {
        this.imageDetail = imageDetail;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public boolean isBeingWatched() {
        return isBeingWatched;
    }

    public void setBeingWatched(boolean beingWatched) {
        isBeingWatched = beingWatched;
    }
}
