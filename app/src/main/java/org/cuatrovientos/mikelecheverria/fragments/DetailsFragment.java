package org.cuatrovientos.mikelecheverria.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.cuatrovientos.mikelecheverria.R;
import org.cuatrovientos.mikelecheverria.model.Film;

import io.realm.Realm;

public class DetailsFragment extends Fragment {
    ImageView image;
    TextView title;
    TextView synopsis;
    TextView creators;
    TextView cast;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        image = view.findViewById(R.id.imgDetail);
        title = view.findViewById(R.id.txtTitle);
        synopsis = view.findViewById(R.id.txtSysnopsis);
        creators = view.findViewById(R.id.txtCreator);
        cast = view.findViewById(R.id.txtCast);
        return view;
    }

    public void renderData(int id) {
        Realm realm = Realm.getDefaultInstance();
        Film film = realm.where(Film.class).equalTo("id", id).findFirst();

        // image.setImageResource(Integer.parseInt(film.getImageDetail()));
        title.setText(film.getTitle());
        synopsis.setText(film.getSynopsis());
        creators.setText(film.getCreator());
        cast.setText(film.getActors());

    }
}