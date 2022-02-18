package com.knowtest.lealtest.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;*/
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.activity.MainActivity;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonInstances.FireBaseStarangeApi;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TelaIncialAdapter extends RecyclerView.Adapter<TelaIncialAdapter.TelaInicialMyViewHolder> {

    private List<Treino> treinos;
    private List<Treino> treinosCopy = new ArrayList<>();
    private Context context;
    private Activity activity;

    public TelaIncialAdapter(List<Treino> treinos, Context context, Activity activity) {
        this.treinos = treinos;
        this.treinosCopy.addAll(treinos);
        this.context = context;
        this.activity = activity;

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
        String mess = context.getString(R.string.continuarExerc);
        holder.overView.setText(mess);
        holder.name.setText(treinos.get(position).getDescricao().toString());
        // isso tem que ir para a classe de api
        FirebaseStorage storage = FireBaseStarangeApi.Companion.getStorangeRefe();
        StorageReference storageRef = storage.getReference();
        StorageReference folder = storageRef.child(treinos.get(position).getId()+"/");
        StorageReference file = folder.child(treinos.get(position).
                getExercicios().get(1).getId().toString()+".png");

        file .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String s= uri.toString();
                Picasso.get().load(s).
                        placeholder(R.drawable.icone).error(R.drawable.icone).into(holder.image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public int getItemCount() {

        return this.treinos.size();
    }

    public void filter(String text) {
        treinos.clear();
        if (text.isEmpty()) {
            treinos.addAll(treinosCopy);
        } else {
            text = text.toLowerCase();
            for (Treino t : treinosCopy) {
                if (t.getDescricao().toLowerCase().contains(text)) {
                    treinos.add(t);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class TelaInicialMyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView overView;
        private ImageView image;
        private TextView id;

        public TelaInicialMyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_title);
            overView = itemView.findViewById(R.id.card_text);
            image = itemView.findViewById(R.id.card_image);

        }
    }

}
