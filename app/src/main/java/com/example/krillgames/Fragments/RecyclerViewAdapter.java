package com.example.krillgames.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.krillgames.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<CardAward> dataAward;

    public RecyclerViewAdapter(Context context, List<CardAward> dataAward) {
        this.context = context;
        this.dataAward = dataAward;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int indice) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.recyclerview_desing, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(dataAward.get(position).getImage());
        holder.title.setText(dataAward.get(position).getTitle());
        holder.information.setText(dataAward.get(position).getCompany() + "\n" + dataAward.get(position).getCode() + "\n" + dataAward.get(position).getCategory() + "\n" + dataAward.get(position).getPrice() + "\n" + dataAward.get(position).getQuantity());
    }

    @Override
    public int getItemCount() { return dataAward.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView information;
        ImageView imageView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTituloJuego);
            information = itemView.findViewById(R.id.txtInformacionJuego);
            imageView = itemView.findViewById(R.id.imgJuego);
            cardView = itemView.findViewById(R.id.id_card);
        }
    }
}