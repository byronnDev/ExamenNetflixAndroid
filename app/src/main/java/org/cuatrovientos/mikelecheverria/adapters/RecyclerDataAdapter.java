package org.cuatrovientos.mikelecheverria.adapters;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.cuatrovientos.mikelecheverria.R;
import org.cuatrovientos.mikelecheverria.activities.FilmActivity;
import org.cuatrovientos.mikelecheverria.activities.MainActivity;
import org.cuatrovientos.mikelecheverria.model.Film;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.RecyclerDataHolder> {
    
    private final List<Film> filmsList;
    private final LayoutInflater inflater;
    private final Context context;

    public RecyclerDataAdapter(List<Film> filmsList, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.filmsList = filmsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerDataAdapter.RecyclerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_film, parent, false); // Inflamos la vista
        return new RecyclerDataAdapter.RecyclerDataHolder(view); // Devolvemos la vista
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDataAdapter.RecyclerDataHolder holder, int position) {
        holder.bindData(filmsList.get(position)); // Llamamos al método BindData
    }

    @Override
    public int getItemCount() {return filmsList.size(); } // Devuelve el tamaño de la lista

    public class RecyclerDataHolder extends RecyclerView.ViewHolder {
        TextView filmId;
        ImageView image;

        public RecyclerDataHolder(@NonNull View itemView) {
            super(itemView);
            filmId = (TextView) itemView.findViewById(R.id.idFilm);
            image = (ImageView) itemView.findViewById(R.id.imageFilm);
        }

        void bindData(@NonNull final Film item) {
            renderFilmItem(item);
            // Set an OnClickListener
            doCardClickFunction(item);
        }

        private void doCardClickFunction(@NonNull Film item) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Envía los datos a la actividad
                    // Si el movil está en modo horizontal
                    int rotation = getRotation();
                    manageRotation(rotation, item);
                }
            });
        }

        private void renderFilmItem(@NonNull Film item) {
            setInformation(item);
        }

        private void setInformation(@NonNull Film item) {
            filmId.setId(item.getId());
            image.setImageResource(item.getImage());
        }
    }

    private void manageRotation(int rotation, @NonNull Film item) {
        if (isHorizontal(rotation)) {
            sendAndRenderData(item);
        } else if (isVertical(rotation)) {
            // Si el movil está en modo vertical
            sendDataToFilmActivityAndStart(item);
        }
    }

    private void sendAndRenderData(@NonNull Film item) {
        ((MainActivity) context).sendData(item.getId());
    }

    private static boolean isVertical(int rotation) {
        return rotation == Configuration.ORIENTATION_PORTRAIT;
    }

    private static boolean isHorizontal(int rotation) {
        return rotation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private int getRotation() {
        return context.getResources().getConfiguration().orientation;
    }

    private void sendDataToFilmActivityAndStart(@NonNull Film item) {
        Intent intent = sendDataToFilmActivity(item);
        context.startActivity(intent);
    }

    @NonNull
    private Intent sendDataToFilmActivity(@NonNull Film item) {
        Intent intent = new Intent(context, FilmActivity.class);
        intent.putExtra("id", item.getId());
        return intent;
    }
}