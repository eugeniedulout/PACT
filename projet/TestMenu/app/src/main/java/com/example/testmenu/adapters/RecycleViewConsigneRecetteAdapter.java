package com.example.testmenu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testmenu.R;

import java.util.ArrayList;

public class RecycleViewConsigneRecetteAdapter extends  RecyclerView.Adapter<RecycleViewConsigneRecetteAdapter.ViewHolder> {
    private ArrayList<String> consignes;
    private Context mContext;

    public RecycleViewConsigneRecetteAdapter(ArrayList<String> consignes, Context mContext) {
        this.consignes = consignes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_consigne_recette, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        holder.stepNumberText.setText(String.valueOf(position+ 1));
        holder.consigneText.setText(consignes.get(position));
    }


    @Override
    public int getItemCount() {
        return consignes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumberText;
        TextView consigneText;

        public ViewHolder(View itemView) {
            super(itemView);
            stepNumberText = (TextView) itemView.findViewById(R.id.stepNumberText);
            consigneText = (TextView) itemView.findViewById(R.id.consigneText);

        }
    }
}
