package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.trapero.cchoice.R;

public class CheckoutActivity extends AppCompatActivity {

    private TextView billingNameTextView;
    private TextView billingEmailTextView;
    private TextView billingAddressTextView;
    private TextView itemNameTextView;
    private TextView itemQuantityTextView;
    private TextView itemPriceTextView;
    private TextView paymentMethodTextView;
    private TextView productSubtotalTextView;
    private TextView deliveryFeeTextView;
    private TextView discountTextView;
    private TextView totalPaymentTextView;
    private Button placeOrderButton; // Add place order button
    private ImageView backButton; // Custom back button as ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize views
        initializeViews();

        // Populate the views with data from the intent
        populateViews();

        // Set up the Place Order button listener
        placeOrderButton.setOnClickListener(v -> {
            Intent transactionDetailIntent = new Intent(CheckoutActivity.this, TransactionDetailsActivity.class);

            // Pass the relevant data to the next activity
            transactionDetailIntent.putExtra("item_name", itemNameTextView.getText().toString());
            transactionDetailIntent.putExtra("item_quantity", itemQuantityTextView.getText().toString());
            transactionDetailIntent.putExtra("item_price", itemPriceTextView.getText().toString());
            transactionDetailIntent.putExtra("payment_method", paymentMethodTextView.getText().toString());
            transactionDetailIntent.putExtra("product_subtotal", productSubtotalTextView.getText().toString());
            transactionDetailIntent.putExtra("delivery_fee", deliveryFeeTextView.getText().toString());
            transactionDetailIntent.putExtra("discount", discountTextView.getText().toString());
            transactionDetailIntent.putExtra("total_payment", totalPaymentTextView.getText().toString());

            // Start TransactionDetailActivity
            startActivity(transactionDetailIntent);
        });

        // Set up the custom Back button listener
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void initializeViews() {
        billingNameTextView = findViewById(R.id.billing_name_textview);
        billingEmailTextView = findViewById(R.id.billing_email_textview);
        billingAddressTextView = findViewById(R.id.billing_address_textview);
        itemNameTextView = findViewById(R.id.item_name_textview);
        itemQuantityTextView = findViewById(R.id.item_quantity_textview);
        itemPriceTextView = findViewById(R.id.item_price_textview);
        paymentMethodTextView = findViewById(R.id.payment_method_textview);
        productSubtotalTextView = findViewById(R.id.product_subtotal_textview);
        deliveryFeeTextView = findViewById(R.id.delivery_fee_textview);
        discountTextView = findViewById(R.id.discount_textview);
        totalPaymentTextView = findViewById(R.id.total_payment_textview);

        // Initialize Place Order Button
        placeOrderButton = findViewById(R.id.place_order_button);

        // Initialize the custom Back button
        backButton = findViewById(R.id.back_button);
    }

    private void populateViews() {
        // Set data from the intent to TextViews
        setTextViewData(billingNameTextView, "billing_name");
        setTextViewData(billingEmailTextView, "billing_email");
        setTextViewData(billingAddressTextView, "billing_address");
        setTextViewData(itemNameTextView, "item_name");
        setTextViewData(itemQuantityTextView, "item_quantity");
        setTextViewData(itemPriceTextView, "item_price");
        setTextViewData(paymentMethodTextView, "payment_method");
        setTextViewData(productSubtotalTextView, "product_subtotal");
        setTextViewData(deliveryFeeTextView, "delivery_fee");
        setTextViewData(discountTextView, "discount");
        setTextViewData(totalPaymentTextView, "total_payment");
    }

    // Helper method to set text on TextViews from intent data
    private void setTextViewData(TextView textView, String key) {
        String value = getIntent().getStringExtra(key);
        if (value != null) {
            textView.setText(value);
        }
    }
}
