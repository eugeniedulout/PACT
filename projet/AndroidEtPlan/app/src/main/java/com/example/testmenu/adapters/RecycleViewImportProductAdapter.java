package com.example.testmenu.adapters;

import android.content.Context;
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

public class RecycleViewImportProductAdapter extends  RecyclerView.Adapter<RecycleViewImportProductAdapter.ViewHolder> {
    private ArrayList<Product> listProducts = new ArrayList<>();
    private Context mContext;
    private ArrayList<Boolean> productSelected = new ArrayList<>();

    public ArrayList<Boolean> getProductSelected() {
        return productSelected;
    }

    public RecycleViewImportProductAdapter(ArrayList<Product> listProducts, ArrayList<Boolean> productSelected, Context mContext) {
        this.listProducts = listProducts;
        this.mContext = mContext;
        this.productSelected = productSelected;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_layout, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Glide.with(mContext).asBitmap().load(marketLogoUrlArray.get(position)).into(holder.marketLogo);
        //holder.marketLogo.setImageResource(imageId);
        if(productSelected.get(position)) {
            holder.backgroundSelected.setBackground(mContext.getResources().getDrawable(R.drawable.background_when_product_selected));
        }
        else {
            holder.backgroundSelected.setBackgroundResource(0);
        }

        Product currentProduct = listProducts.get(position);
        String productName  = currentProduct.getName();
        double productPrice  = currentProduct.getPrice();
        String productImage = currentProduct.getProductImageUrl();

        holder.nameProduct.setText(productName);
        holder.priceProduct.setText(String.valueOf(productPrice/100 ) + " €");


        Glide.with(mContext).load(productImage).into(holder.productImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(!productSelected.get(position)) {
                holder.backgroundSelected.setBackground(mContext.getResources().getDrawable(R.drawable.background_when_product_selected));
                productSelected.set(position, true);
            }
            else {

                holder.backgroundSelected.setBackgroundResource(0);
                productSelected.set(position, false);
            }
            }
        });

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
        View backgroundSelected;
        public ViewHolder(View itemView) {
            super(itemView);
            nameProduct = (TextView) itemView.findViewById(R.id.nameProduct);
            priceProduct = (TextView) itemView.findViewById(R.id.priceProduct);
            productImage = (ImageView) itemView.findViewById(R.id.iconRecette);
            cardView = (CardView)itemView.findViewById(R.id.cardVV);
            deleteProductImage = (ImageView)itemView.findViewById(R.id.deleteProductImage);
            backgroundSelected = (View) itemView.findViewById(R.id.backgroundSelected);

            deleteProductImage.setVisibility(View.GONE);
        }
    }



}
