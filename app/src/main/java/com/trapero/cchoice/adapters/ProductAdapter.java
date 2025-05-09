package com.trapero.cchoice.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.trapero.cchoice.models.Product;
import com.trapero.cchoice.R;
import com.trapero.cchoice.activities.ProductDetailActivity;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final Context context;

    // Modify the constructor to accept a Context
    public ProductAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imageProduct.setImageResource(product.getImageResId());
        holder.textProductName.setText(product.getName());
        holder.textProductPrice.setText("₱ " + product.getPrice());
        holder.textProductDiscount.setText("-" + product.getDiscount() + "%");
        holder.ratingBar.setRating(product.getRating());
        holder.textReviewCount.setText("(" + product.getReviewCount() + ")");

        // Set a click listener for the item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", product.getPrice());
                intent.putExtra("product_discount", product.getDiscount());
                intent.putExtra("product_rating", product.getRating());
                intent.putExtra("product_review_count", product.getReviewCount());
                intent.putExtra("product_image_res_id", product.getImageResId());
                context.startActivity(intent);
            }
        });
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
            imageProduct = itemView.findViewById(R.id.product_image_view);
            textProductName = itemView.findViewById(R.id.product_name_text_view);
            textProductPrice = itemView.findViewById(R.id.product_price_text_view);
            textProductDiscount = itemView.findViewById(R.id.product_old_price_text_view);
            ratingBar = itemView.findViewById(R.id.product_rating_bar);
            textReviewCount = itemView.findViewById(R.id.product_category_text_view);
        }
    }
}
