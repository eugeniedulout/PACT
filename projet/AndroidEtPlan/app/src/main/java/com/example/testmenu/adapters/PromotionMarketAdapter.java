package com.example.testmenu.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testmenu.ProductOnSpecialOffer;
import com.example.testmenu.R;

import java.util.List;

public class PromotionMarketAdapter extends BaseAdapter {
    private Context context;
    private List<ProductOnSpecialOffer> products;
    private LayoutInflater inflater;
    public PromotionMarketAdapter(Context context, List<ProductOnSpecialOffer> products) {
        this.context = context;
        this.products = products;
        this.inflater  = LayoutInflater.from(context);
    }

    
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public ProductOnSpecialOffer getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_view_promotions, null);

        ProductOnSpecialOffer currentProduct = getItem(position);

        String productName  = currentProduct.getName();
        double productPrice  = currentProduct.getPrice();
        double newProductPrice = currentProduct.getNewPrice();
        String expirationDateText = currentProduct.getExpirationDate();
        String productImage = currentProduct.getProductImageUrl();

        TextView nameProduct = (TextView) convertView.findViewById(R.id.nameProduct);
        nameProduct.setText(productName);
        TextView priceProduct = (TextView) convertView.findViewById(R.id.priceProduct);
        priceProduct.setText(String.valueOf(productPrice /100) + " €");
        priceProduct.setPaintFlags(priceProduct.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TextView newPriceProduct = (TextView) convertView.findViewById(R.id.newPriceProduct);
        newPriceProduct.setText(String.valueOf(newProductPrice /100) + " €");


        TextView expirationDate = (TextView) convertView.findViewById(R.id.expirationDateText);
        expirationDate.setText("Expire le " + expirationDateText);

        int imageId = context.getResources().getIdentifier(productImage, "drawable", context.getPackageName());

        ImageView iconProduct= convertView.findViewById(R.id.iconnProduct);
        iconProduct.setImageResource(imageId);


        return convertView;
    }
}
