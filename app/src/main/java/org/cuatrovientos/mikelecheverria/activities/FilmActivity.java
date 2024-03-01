package org.cuatrovientos.mikelecheverria.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.cuatrovientos.mikelecheverria.R;
import org.cuatrovientos.mikelecheverria.model.Film;

import io.realm.Realm;

public class FilmActivity extends AppCompatActivity {
    int filmId;
    ImageView image;
    TextView title;
    TextView synopsis;
    TextView creators;
    TextView cast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        image = findViewById(R.id.imgDetail);
        title = findViewById(R.id.txtTitle);
        synopsis = findViewById(R.id.txtSysnopsis);
        creators = findViewById(R.id.txtCreator);
        cast = findViewById(R.id.txtCast);

        getData();
        setData();
    }

    public void getData() {
        Intent intent = getIntent();
        filmId = intent.getIntExtra("id", 0);
    }

    public void setData() {
        Realm realm = Realm.getDefaultInstance();
        Film film = realm.where(Film.class).equalTo("id", filmId).findFirst();
        image.setImageResource(film.getImageDetail());
        title.setText(film.getTitle());
        synopsis.setText(film.getSynopsis());
        creators.setText(film.getCreator());
        cast.setText(film.getActors());
        realm.close();
    }
}