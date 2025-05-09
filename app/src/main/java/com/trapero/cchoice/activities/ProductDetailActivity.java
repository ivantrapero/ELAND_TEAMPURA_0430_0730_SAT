package com.trapero.cchoice.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.trapero.cchoice.R;
import com.trapero.cchoice.models.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView favoriteButton;
    private TextView productCategoryTextView;
    private TextView productNameTextView;
    private RatingBar productRatingBar;
    private TextView productPriceTextView;
    private TextView productOldPriceTextView;
    private ImageView productImage;
    private AppCompatButton rentButton;
    private AppCompatButton buyButton;
    private TextView productDescriptionTextView;

    private Product product;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product);

        backButton = findViewById(R.id.back_button);
        favoriteButton = findViewById(R.id.favorite_button);
        productCategoryTextView = findViewById(R.id.product_category_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);
        productRatingBar = findViewById(R.id.product_rating_bar);
        productPriceTextView = findViewById(R.id.product_price_text_view);
        productOldPriceTextView = findViewById(R.id.product_old_price_text_view);
        productImage = findViewById(R.id.product_image_view);
        rentButton = findViewById(R.id.rent_button);
        buyButton = findViewById(R.id.buy_button);
        productDescriptionTextView = findViewById(R.id.product_description_text_view);

        product = getIntent().getParcelableExtra("product");

        if (product != null) {
            productNameTextView.setText(product.getName());
            productRatingBar.setRating(product.getRating());
            productPriceTextView.setText("₱" + String.format("%.2f", product.getPrice()));
            productImage.setImageResource(product.getImageResId());

            if (product.getDescription() != null && !product.getDescription().isEmpty()) {
                productDescriptionTextView.setText(product.getDescription());
            } else {
                productDescriptionTextView.setVisibility(View.GONE);
            }
        }

        backButton.setOnClickListener(v -> finish());

        favoriteButton.setOnClickListener(v -> favoriteButton.setImageResource(R.drawable.heart));

        rentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, RentActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        buyButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, BuyDetailActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });
    }
}
