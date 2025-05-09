package com.trapero.cchoice.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.trapero.cchoice.R;

public class TransactionDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TransactionDetailsActivity";

    // UI elements
    private ImageView backButton;
    private TextView itemNameTextView;
    private TextView itemQuantityTextView;
    private TextView itemPriceTextView;
    private TextView paymentMethodTextView;
    private TextView productSubtotalTextView;
    private TextView deliveryFeeTextView;
    private TextView discountTextView;
    private TextView totalPaymentTextView;

    private ImageView processingIcon;
    private ImageView toShipIcon;
    private ImageView toReceiveIcon;
    private TextView processingTextView;
    private TextView toShipTextView;
    private TextView toReceiveTextView;

    private String itemName;
    private int itemQuantity;
    private double itemPrice;
    private String paymentMethod;
    private double productSubtotal;
    private double deliveryFee;
    private double discount;
    private double totalPayment;
    private int shippingStage = 0;

    private Handler handler = new Handler();

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        // Initialize UI elements
        backButton = findViewById(R.id.back_button);
        itemNameTextView = findViewById(R.id.item_name_textview);
        itemQuantityTextView = findViewById(R.id.item_quantity_textview);
        itemPriceTextView = findViewById(R.id.item_price_textview);
        paymentMethodTextView = findViewById(R.id.payment_method_textview);
        productSubtotalTextView = findViewById(R.id.product_subtotal_textview);
        deliveryFeeTextView = findViewById(R.id.delivery_fee_textview);
        discountTextView = findViewById(R.id.discount_textview);
        totalPaymentTextView = findViewById(R.id.total_payment_textview);

        processingIcon = findViewById(R.id.processing_icon);
        toShipIcon = findViewById(R.id.to_ship_icon);
        toReceiveIcon = findViewById(R.id.to_receive_icon);
        processingTextView = findViewById(R.id.processing_textview);
        toShipTextView = findViewById(R.id.to_ship_textview);
        toReceiveTextView = findViewById(R.id.to_receive_textview);

        // Get data from Intent
        itemName = getIntent().getStringExtra("item_name");
        itemQuantity = getIntent().getIntExtra("item_quantity", 0);
        itemPrice = getIntent().getDoubleExtra("item_price", 0.0);
        paymentMethod = getIntent().getStringExtra("payment_method");
        productSubtotal = getIntent().getDoubleExtra("product_subtotal", 0.0);
        deliveryFee = getIntent().getDoubleExtra("delivery_fee", 0.0);
        discount = getIntent().getDoubleExtra("discount", 0.0);
        totalPayment = getIntent().getDoubleExtra("total_payment", 0.0);

        // Set data to UI
        itemNameTextView.setText(itemName);
        itemQuantityTextView.setText("QTY: " + itemQuantity + ", Rent April 11 - April 17");
        itemPriceTextView.setText("₱" + String.format("%.2f", itemPrice));
        paymentMethodTextView.setText(paymentMethod);
        productSubtotalTextView.setText("₱" + String.format("%.2f", productSubtotal));
        deliveryFeeTextView.setText("₱" + String.format("%.2f", deliveryFee));
        discountTextView.setText("₱" + String.format("%.2f", discount));
        totalPaymentTextView.setText("₱" + String.format("%.2f", totalPayment));

        // Set up back button listener
        backButton.setOnClickListener(v -> finish());

        // Start shipping progress simulation
        startShippingProgress();
    }

    private void startShippingProgress() {
        handler.postDelayed(() -> {
            shippingStage = 1; // To Ship
            updateShippingUI();
            handler.postDelayed(() -> {
                shippingStage = 2; // To Receive
                updateShippingUI();
                handler.postDelayed(() -> updateShippingUI(), 2000);
            }, 2000);
        }, 2000);
    }

    private void updateShippingUI() {
        switch (shippingStage) {
            case 0: // Processing
                processingIcon.setImageResource(R.drawable.processing);
                processingTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                toShipIcon.setImageResource(R.drawable.ship);
                toShipTextView.setTextColor(getResources().getColor(R.color.grey, getTheme()));
                toReceiveIcon.setImageResource(R.drawable.receive);
                toReceiveTextView.setTextColor(getResources().getColor(R.color.grey, getTheme()));
                break;
            case 1: // To Ship
                processingIcon.setImageResource(R.drawable.processing);
                processingTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                toShipIcon.setImageResource(R.drawable.ship);
                toShipTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                toReceiveIcon.setImageResource(R.drawable.receive);
                toReceiveTextView.setTextColor(getResources().getColor(R.color.grey, getTheme()));
                break;
            case 2: // To Receive
                processingIcon.setImageResource(R.drawable.processing);
                processingTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                toShipIcon.setImageResource(R.drawable.ship);
                toShipTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                toReceiveIcon.setImageResource(R.drawable.receive);
                toReceiveTextView.setTextColor(getResources().getColor(R.color.yellow, getTheme()));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
