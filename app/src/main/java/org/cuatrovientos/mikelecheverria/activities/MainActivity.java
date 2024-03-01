package org.cuatrovientos.mikelecheverria.activities;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.cuatrovientos.mikelecheverria.R;
import org.cuatrovientos.mikelecheverria.adapters.RecyclerDataAdapter;
import org.cuatrovientos.mikelecheverria.fragments.DataFragment;
import org.cuatrovientos.mikelecheverria.fragments.DetailsFragment;
import org.cuatrovientos.mikelecheverria.model.Film;
import org.cuatrovientos.mikelecheverria.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends FragmentActivity implements DataFragment.DataListener {
    List<Film> allElements = new ArrayList<>();
    List<Film> searchResults = new ArrayList<>();
    List<Film> watchingFilms = new ArrayList<>();
    List<Film> popularFilms = new ArrayList<>();
    List<Film> myListFilms = new ArrayList<>();
    EditText search;
    TextView lblWatching;
    TextView lblPopular;
    TextView lblMyList;

    RecyclerView watchingRecycler;
    RecyclerView popularRecycler;
    RecyclerView myListRecycler;
    RecyclerView searchRecycler;
    ImageButton imgNetflix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
    public void init() {
        getData();
        saveDataInDatabase();
        makeCategories();
        findElementsInLayout();
        makeAdaptersAndSetLayouts();
        onSearchTyping();
        backToHomePage();
    }

    private void findElementsInLayout() {
        watchingRecycler = findViewById(R.id.watchingRecycler);
        popularRecycler = findViewById(R.id.popularRecycler);
        myListRecycler = findViewById(R.id.myListRecycler);
        searchRecycler = findViewById(R.id.searchRecycler);

        lblWatching = findViewById(R.id.lblWatching);
        lblPopular = findViewById(R.id.lblPopular);
        lblMyList = findViewById(R.id.lblMyList);
        imgNetflix = findViewById(R.id.imgNetflix);
        search = findViewById(R.id.txtSearch);
    }

    private void makeAdaptersAndSetLayouts() {
        RecyclerDataAdapter watchingAdapter = new RecyclerDataAdapter(watchingFilms, this);
        RecyclerDataAdapter popularAdapter = new RecyclerDataAdapter(popularFilms, this);
        RecyclerDataAdapter myListAdapter = new RecyclerDataAdapter(myListFilms, this);

        setLayoutAndAdaper(watchingAdapter, watchingRecycler);
        setLayoutAndAdaper(popularAdapter, popularRecycler);
        setLayoutAndAdaper(myListAdapter, myListRecycler);
    }

    private void backToHomePage() {
        imgNetflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllAndHideSearchRecycler();
            }
        });
    }

    private void onSearchTyping() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hideAllAndSeeSearchRecycler();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterMovies(String searchText) {
        searchResults.clear();

        for (Film film : allElements) {
            // Filtra las películas cuyo título comienza con el texto de búsqueda
            if (film.getTitle().toLowerCase().startsWith(searchText.toLowerCase())) {
                searchResults.add(film);
            }
        }

        // Actualiza el adaptador del RecyclerView de búsqueda
        RecyclerDataAdapter searchAdapter = new RecyclerDataAdapter(searchResults, this);
        setLayoutAndAdaper(searchAdapter, searchRecycler);
    }

    private void showAllAndHideSearchRecycler() {
        // Muestra el TextView lblWatching
        lblWatching.setVisibility(View.VISIBLE);

        // Muestra el RecyclerView watchingRecycler
        watchingRecycler.setVisibility(View.VISIBLE);

        // Muestra el TextView lblPopular
        lblPopular.setVisibility(View.VISIBLE);

        // Muestra el RecyclerView popularRecycler
        popularRecycler.setVisibility(View.VISIBLE);

        // Muestra el TextView lblMyList
        lblMyList.setVisibility(View.VISIBLE);

        // Muestra el RecyclerView myListRecycler
        myListRecycler.setVisibility(View.VISIBLE);

        // Oculta el RecyclerView searchRecycler
        searchRecycler.setVisibility(View.GONE);
    }


    private void hideAllAndSeeSearchRecycler() {
        // Oculta el TextView lblWatching
        lblWatching.setVisibility(View.GONE);

        // Oculta el RecyclerView watchingRecycler
        watchingRecycler.setVisibility(View.GONE);

        // Oculta el TextView lblPopular
        lblPopular.setVisibility(View.GONE);

        // Oculta el RecyclerView popularRecycler
        popularRecycler.setVisibility(View.GONE);

        // Oculta el TextView lblMyList
        lblMyList.setVisibility(View.GONE);

        // Oculta el RecyclerView myListRecycler
        myListRecycler.setVisibility(View.GONE);

        // Muestra el RecyclerView searchRecycler
        searchRecycler.setVisibility(View.VISIBLE);
    }


    private void makeCategories() {
        watchingFilms = getWatching();
        popularFilms = getPopular();
        myListFilms = getMyList();
    }

    private List<Film> getMyList() {
        List<Film> myList = new ArrayList<>();
        for (Film film : allElements) {
            if (film.isFavorite()) myList.add(film);
        }
        return myList;
    }

    private List<Film> getPopular() {
        List<Film> popularList = new ArrayList<>();
        for (Film film : allElements) {
            if (film.isPopular()) popularList.add(film);
        }
        return popularList;
    }

    private List<Film> getWatching() {
        List<Film> watchingList = new ArrayList<>();
        for (Film film : allElements) {
            if (film.isBeingWatched()) watchingList.add(film);
        }
        return watchingList;
    }

    private static void setLayoutAndAdaper(RecyclerDataAdapter adapter, RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter); // Establecemos el adaptador
    }

    private void saveDataInDatabase() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Film film:allElements) {
            realm.copyToRealm(film);
        }
        realm.commitTransaction();
        realm.close();
    }

    private void getData() {
        allElements = Utils.getDummyData();
    }

    @Override
    public void sendData(int id) {
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
        renderData(id, detailsFragment);
    }

    private static void renderData(int id, DetailsFragment detailsFragment) {
        if (haveDetails(detailsFragment)) {
            detailsFragment.renderData(id);
        }
    }

    private static boolean haveDetails(DetailsFragment detailsFragment) {
        return detailsFragment != null;
    }
}