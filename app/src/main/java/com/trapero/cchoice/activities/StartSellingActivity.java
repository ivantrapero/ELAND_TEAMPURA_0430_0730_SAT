package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Intent;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.net.Uri;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.util.Log;

import com.trapero.cchoice.R;

public class StartSellingActivity extends AppCompatActivity {

    private EditText productNameEditText, productDescriptionEditText, productPriceEditText;
    private RadioGroup saleTypeRadioGroup;
    private RadioButton rentRadioButton, sellRadioButton, bothRadioButton;
    private Button listProductButton, uploadImageButton;
    private ImageView productImageView;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_selling);

        // Initialize UI elements
        productNameEditText = findViewById(R.id.product_name_edit_text);
        productDescriptionEditText = findViewById(R.id.product_description_edit_text);
        productPriceEditText = findViewById(R.id.product_price_edit_text);
        saleTypeRadioGroup = findViewById(R.id.sale_type_radio_group);
        rentRadioButton = findViewById(R.id.rent_radio_button);
        sellRadioButton = findViewById(R.id.sell_radio_button);
        bothRadioButton = findViewById(R.id.both_radio_button);
        listProductButton = findViewById(R.id.list_product_button);
        uploadImageButton = findViewById(R.id.upload_image_button);
        productImageView = findViewById(R.id.product_image_view);

        // Set click listener for the upload image button
        uploadImageButton.setOnClickListener(v -> showImagePickerDialog());

        listProductButton.setOnClickListener(v -> handleListProductButtonClick());
    }

    private void handleListProductButtonClick() {
        // Get user input
        String productName = productNameEditText.getText().toString();
        String productDescription = productDescriptionEditText.getText().toString();
        String productPrice = productPriceEditText.getText().toString();
        int selectedSaleTypeId = saleTypeRadioGroup.getCheckedRadioButtonId();
        String saleType = "";

        // Validate input
        if (productName.isEmpty() || productDescription.isEmpty() || productPrice.isEmpty() || selectedSaleTypeId == -1) {
            Toast.makeText(StartSellingActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected sale type
        if (selectedSaleTypeId == rentRadioButton.getId()) {
            saleType = "Rent";
        } else if (selectedSaleTypeId == sellRadioButton.getId()) {
            saleType = "Sell";
        } else if (selectedSaleTypeId == bothRadioButton.getId()) {
            saleType = "Rent / Sell";
        }

        // Convert price to a double
        double price;
        try {
            price = Double.parseDouble(productPrice);
        } catch (NumberFormatException e) {
            Toast.makeText(StartSellingActivity.this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display a confirmation (replace with your actual listing logic)
        String message = "Product Name: " + productName + "\n"
                + "Description: " + productDescription + "\n"
                + "Price: " + price + "\n"
                + "Sale Type: " + saleType;
        if (imageUri != null) {
            message += "\nImage URI: " + imageUri.toString();
        }
        Toast.makeText(StartSellingActivity.this, message, Toast.LENGTH_LONG).show();

        // Clear the fields after successful submission
        productNameEditText.getText().clear();
        productDescriptionEditText.getText().clear();
        productPriceEditText.getText().clear();
        saleTypeRadioGroup.clearCheck();
        productImageView.setImageResource(R.drawable.camera);
        imageUri = null;

        // Finish
        finish();
    }

    private void showImagePickerDialog() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                checkCameraPermissionAndOpenCamera();
            } else if (options[item].equals("Choose from Gallery")) {
                openGallery();
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Log.e("StartSellingActivity", "No camera app found.");
            Toast.makeText(this, "No camera app found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera permission required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    productImageView.setImageBitmap(imageBitmap);
                    imageUri = getImageUri(this, imageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                imageUri = data.getData();
                productImageView.setImageURI(imageUri);
            }
        }
    }

    // Helper method to get URI from Bitmap
    private Uri getImageUri(android.content.Context inContext, Bitmap inImage) {
        java.io.File tempFile = new java.io.File(getExternalFilesDir(null), "temp_image.jpg");
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(tempFile);
    }
}

