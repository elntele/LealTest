package com.knowtest.lealtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knowtest.lealtest.R;
import com.knowtest.lealtest.model.Treino;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TelaIncialAdapter extends RecyclerView.Adapter<TelaIncialAdapter.TelaInicialMyViewHolder> {

    private List <Treino> treinos;
    private List <Treino> treinosCopy = new ArrayList<>();
    private final String baseUrl="https://image.tmdb.org/t/p/";
    private  final String tamanho ="w45";


    public TelaIncialAdapter(List<Treino> treinos) {
       this.treinos=treinos;
       this.treinosCopy.addAll(treinos);

    }

    @NonNull
    @Override
    public TelaInicialMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemLista = LayoutInflater.
                from(parent.getContext()).inflate(R.layout.tela_principal_card_view,
                parent, false);
        return new TelaInicialMyViewHolder(ItemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull TelaInicialMyViewHolder holder, int position) {

        String textoCompleto= treinos.get(position).getDescricao();
        String substring="";
        try {
            substring=textoCompleto.substring(0,50);
        }catch (Exception e){
            substring=textoCompleto.substring(0,textoCompleto.length());
        }
        String mess = "Clique para continuar lendo";
        substring= substring+"..."+"\n"+mess ;
        holder.overView.setText(substring);
        holder.name.setText(treinos.get(position).getNome().toString());

        Picasso.get().load(baseUrl+tamanho+ treinos.get(position).exercicios.get(0).getImagem()).
                placeholder(R.drawable.icone).error(R.drawable.icone).into(holder.image);


    }

    @Override
    public int getItemCount() {

        return this.treinos.size();
    }

    public void filter(String text) {
        treinos.clear();
        if(text.isEmpty()){
            treinos.addAll(treinosCopy);
        } else{
            text = text.toLowerCase();
            for(Treino t: treinosCopy){
                if(t.getDescricao().toLowerCase().contains(text) ){
                    treinos.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }



    public class TelaInicialMyViewHolder extends   RecyclerView.ViewHolder{
        private TextView name;
        private TextView overView;
        private ImageView image;
        private TextView id;

        public TelaInicialMyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.card_title);
            overView=itemView.findViewById(R.id.card_text);
            image=itemView.findViewById(R.id.card_image);

        }
    }

}
