package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.trapero.cchoice.R;
import com.trapero.cchoice.models.Product;

public class BuyDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;
    private RatingBar productRating;

    private TextView quantityText;
    private Button quantityIncreaseButton, quantityDecreaseButton;
    private Button buyButton;
    private int quantity = 1; // Default to 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_product_detail);

        // Bind views
        initializeViews();

        // Load product from intent
        Product product = getProductFromIntent();

        if (product != null) {
            setProductDetails(product);
        }

        // Initial quantity display
        updateQuantityDisplay();

        // Quantity increase
        quantityIncreaseButton.setOnClickListener(v -> increaseQuantity());

        // Quantity decrease
        quantityDecreaseButton.setOnClickListener(v -> decreaseQuantity());

        // Buy button click listener to navigate to CheckoutActivity
        buyButton.setOnClickListener(v -> proceedToCheckout(product));
    }

    private void initializeViews() {
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        productRating = findViewById(R.id.product_rating);
        quantityText = findViewById(R.id.quantity_text);
        quantityIncreaseButton = findViewById(R.id.quantity_increase_button);
        quantityDecreaseButton = findViewById(R.id.quantity_decrease_button);
        buyButton = findViewById(R.id.buy_checkout_button);
    }

    private Product getProductFromIntent() {
        return getIntent().getParcelableExtra("product");
    }

    private void setProductDetails(Product product) {
        productImage.setImageResource(product.getImageResId());
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText("₱" + String.format("%.2f", product.getPrice()));
        productRating.setRating(product.getRating());
    }

    private void updateQuantityDisplay() {
        quantityText.setText(String.valueOf(quantity));
    }

    private void increaseQuantity() {
        quantity++;
        updateQuantityDisplay();
    }

    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            updateQuantityDisplay();
        }
    }

    private void proceedToCheckout(Product product) {
        if (product != null) {
            Intent checkoutIntent = new Intent(BuyDetailActivity.this, CheckoutActivity.class);
            checkoutIntent.putExtra("product", product);
            checkoutIntent.putExtra("quantity", quantity);
            startActivity(checkoutIntent);
        }
    }
}
