package com.trapero.cchoice.adapters; // Changed package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.trapero.cchoice.models.Product; // Changed import
import com.trapero.cchoice.R; // Changed R import

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imageProduct.setImageResource(product.getImageResId());
        holder.textProductName.setText(product.getName());
        holder.textProductPrice.setText("₱ " + product.getPrice());
        holder.textProductDiscount.setText("-" + product.getDiscount() + "%");
        holder.ratingBar.setRating(product.getRating());
        holder.textReviewCount.setText("(" + product.getReviewCount() + ")");
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textProductName, textProductPrice, textProductDiscount, textReviewCount;
        RatingBar ratingBar;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textProductName = itemView.findViewById(R.id.textProductName);
            textProductPrice = itemView.findViewById(R.id.textProductPrice);
            textProductDiscount = itemView.findViewById(R.id.textProductDiscount);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            textReviewCount = itemView.findViewById(R.id.textReviewCount);
        }
    }
}