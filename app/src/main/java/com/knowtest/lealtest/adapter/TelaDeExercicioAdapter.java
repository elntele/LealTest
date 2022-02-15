package com.knowtest.lealtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.knowtest.lealtest.R;
import com.knowtest.lealteste.Activity.model.Exercicio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TelaDeExercicioAdapter extends RecyclerView.Adapter<TelaDeExercicioAdapter.TelaDeExercicioAdapterMyViewHolder> {

    private List<Exercicio> exercicios;
    private List<Exercicio> exerciciosCopy = new ArrayList<>();
    Context context;

    public TelaDeExercicioAdapter(List<Exercicio> exercicios, Context context) {
        this.exercicios = exercicios;
        this.exerciciosCopy.addAll(exercicios);
        this.context = context;

    }

    @NonNull
    @Override
    public TelaDeExercicioAdapter.TelaDeExercicioAdapterMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemLista = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.tela_principal_card_view,
                parent, false);
        return new TelaDeExercicioAdapter.TelaDeExercicioAdapterMyViewHolder(ItemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull TelaDeExercicioAdapter.TelaDeExercicioAdapterMyViewHolder holder, int position) {


        String textoCompleto = exercicios.get(position).getObservacoes();
        String substring = "";
        try {
            substring = textoCompleto.substring(0, 50);
        } catch (Exception e) {
            substring = textoCompleto.substring(0, textoCompleto.length());
        }

        String Arrstr[] = exercicios.get(position).getObservacoes().split(" ");
        String firstWord = Arrstr[0];
        String mess = context.getString(R.string.continuarExerc);
        holder.overView.setText(substring + "... " + context.getText(R.string.contnuarLendo));
        holder.name.setText(firstWord);
        Picasso.get().load(exercicios.get(position).getImagem().toString()).
                placeholder(R.drawable.icone).error(R.drawable.icone).into(holder.image);

    }

    @Override
    public int getItemCount() {

        return this.exercicios.size();
    }

    public void filter(String text) {
        exercicios.clear();
        if (text.isEmpty()) {
            exercicios.addAll(exerciciosCopy);
        } else {
            text = text.toLowerCase();
            for (Exercicio e : exerciciosCopy) {
                if (e.getObservacoes().toLowerCase().contains(text)) {
                    exercicios.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class TelaDeExercicioAdapterMyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView overView;
        private ImageView image;
        private TextView id;

        public TelaDeExercicioAdapterMyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_title);
            overView = itemView.findViewById(R.id.card_text);
            image = itemView.findViewById(R.id.card_image);

        }
    }
}
