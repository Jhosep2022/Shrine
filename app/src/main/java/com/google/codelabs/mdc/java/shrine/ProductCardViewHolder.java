package com.google.codelabs.mdc.java.shrine;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

public class ProductCardViewHolder extends RecyclerView.ViewHolder {
    public NetworkImageView productImage;
    public TextView productNombre;
    public TextView productPrecio;
    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView
        productImage = itemView.findViewById(R.id.product_image);
        productNombre = itemView.findViewById(R.id.product_nombre);
        productPrecio = itemView.findViewById(R.id.product_precio);
    }
}
