package com.example.testmenu.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testmenu.Product;
import com.example.testmenu.R;

import java.util.ArrayList;

public class RecycleViewProductAdapter extends  RecyclerView.Adapter<RecycleViewProductAdapter.ViewHolder> {
    private ArrayList<Product> listProducts = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;
    private boolean isDelete;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onItemClick(int position);
    }

    public RecycleViewProductAdapter(ArrayList<Product> listProducts, Context mContext) {
        this.listProducts = listProducts;
        this.mContext = mContext;
        isDelete = false;
    }

    public RecycleViewProductAdapter(ArrayList<Product> listProducts, boolean isDelete, Context mContext) {
        this.listProducts = listProducts;
        this.isDelete = isDelete;
        this.mContext = mContext;

    }


    public void hideDeleteButton(boolean isDelete ){
        this.isDelete = isDelete;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);

        return new ViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        //holder.marketLogo.setImageResource(imageId);


        Product currentProduct = listProducts.get(position);
        String productName  = currentProduct.getName();
        double productPrice  = currentProduct.getPrice();
        String productImage = currentProduct.getProductImageUrl();

        holder.nameProduct.setText(productName);
        holder.priceProduct.setText(String.valueOf(productPrice/100 ) + " €");


        if(isDelete)
            holder.deleteProductImage.setVisibility(View.INVISIBLE);
        else
            holder.deleteProductImage.setVisibility(View.VISIBLE);

        Glide.with(mContext).load(productImage).into(holder.productImage);


       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listProducts.get(position).displayInfo( mContext,R.id.container);
            }
        });*/





    }


    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct;
        TextView priceProduct;
        ImageView productImage;
        CardView cardView;
        ImageView deleteProductImage;
        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameProduct = (TextView) itemView.findViewById(R.id.nameProduct);
            priceProduct = (TextView) itemView.findViewById(R.id.priceProduct);
            productImage = (ImageView) itemView.findViewById(R.id.iconRecette);
            cardView = (CardView)itemView.findViewById(R.id.cardVV);
            deleteProductImage = (ImageView)itemView.findViewById(R.id.deleteProductImage);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });



           deleteProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("CLICKKk", ""+getAdapterPosition());
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }
}
