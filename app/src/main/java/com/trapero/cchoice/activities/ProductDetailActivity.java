package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.trapero.cchoice.R;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product);

        // 1. Get the Intent that started this activity
        Bundle extras = getIntent().getExtras();

        // 2. Safely Extract Data from the Intent
        if (extras == null) {
            Log.e(TAG, "Error: No extras provided in Intent!");
            finish();
            return;
        }

        String productName = extras.getString("product_name");
        double productPrice = extras.getDouble("product_price", 0.0);
        int productDiscount = extras.getInt("product_discount", 0);
        float productRating = extras.getFloat("product_rating", 0.0f);
        int productReviewCount = extras.getInt("product_review_count", 0);
        int productImageResId = extras.getInt("product_image_res_id", 0);

        // 3. Check for invalid data
        if (productImageResId == 0) {
            Log.e(TAG, "Error: Invalid product image resource ID.");
            finish();
            return;
        }
        if (productName == null)
        {
            Log.e(TAG, "Error: Product Name is null");
            finish();
            return;
        }

        // 4. Find the views in the layout
        ImageView productImageView = findViewById(R.id.product_image_view);
        TextView productNameTextView = findViewById(R.id.product_name_text_view);
        TextView productPriceTextView = findViewById(R.id.product_price_text_view);
        TextView productDiscountTextView = findViewById(R.id.product_old_price_text_view);
        RatingBar ratingBar = findViewById(R.id.product_rating_bar);
        TextView reviewCountTextView = findViewById(R.id.product_category_text_view);

        // 5. Set the data to the views
        productImageView.setImageResource(productImageResId);
        productNameTextView.setText(productName);
        String formattedPrice = String.format(Locale.getDefault(), "₱ %.2f", productPrice);
        productPriceTextView.setText(formattedPrice);
        productDiscountTextView.setText("-" + productDiscount + "%");
        ratingBar.setRating(productRating);
        reviewCountTextView.setText("(" + productReviewCount + ")");

        // 6.  Logging
        Log.d(TAG, "Product Details: " + productName + ", Price: " + formattedPrice + ", Rating: " + productRating);
    }
}
