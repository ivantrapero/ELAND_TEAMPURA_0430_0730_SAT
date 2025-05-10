package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.trapero.cchoice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView; // Import BottomNavigationView
import com.trapero.cchoice.session.Session;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private ImageView menuIcon;
    private TextView accountText;
    private ImageView profileImage;
    private TextView nameText;
    private TextView emailText;
    private Button editProfileButton;
    private TextView myOrdersTitle;
    private ImageView processingIcon;
    private TextView processingText;
    private ImageView toShipIcon;
    private TextView toShipText;
    private ImageView toReceiveIcon;
    private TextView toReceiveText;
    private TextView servicesTitle;
    private ImageView startSellingIcon;
    private TextView startSellingText;
    private ImageView emailUsIcon;
    private TextView emailUsText;
    private ImageView aboutUsIcon;
    private TextView aboutUsText;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI elements
        menuIcon = findViewById(R.id.menu_icon);
        accountText = findViewById(R.id.account_text);
        profileImage = findViewById(R.id.profile_image);
        nameText = findViewById(R.id.name_text);
        emailText = findViewById(R.id.email_text);
        editProfileButton = findViewById(R.id.edit_profile_button);
        myOrdersTitle = findViewById(R.id.my_orders_title);
        processingIcon = findViewById(R.id.processing_icon);
        processingText = findViewById(R.id.processing_text);
        toShipIcon = findViewById(R.id.to_ship_icon);
        toShipText = findViewById(R.id.to_ship_text);
        toReceiveIcon = findViewById(R.id.to_receive_icon);
        toReceiveText = findViewById(R.id.to_receive_text);
        servicesTitle = findViewById(R.id.services_title);
        startSellingIcon = findViewById(R.id.start_selling_icon);
        startSellingText = findViewById(R.id.start_selling_text);
        emailUsIcon = findViewById(R.id.email_us_icon);
        emailUsText = findViewById(R.id.email_us_text);
        aboutUsIcon = findViewById(R.id.about_us_icon);
        aboutUsText = findViewById(R.id.about_us_text);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // Set data=
        nameText.setText(getString(R.string.full_name_display, Session.INSTANCE.getUser_first_name(), Session.INSTANCE.getUser_last_name()));
        emailText.setText(Session.INSTANCE.getUser_email());
        profileImage.setImageResource(R.drawable.lebron); // Use the resource ID


        // Set up listeners
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        // BottomNavigationView
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    Intent profileIntent = new Intent(this, DashboardActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (itemId == R.id.navigation_basket) {
                    Intent productListIntent = new Intent(this, ProductListActivity.class);
                    startActivity(productListIntent);
                    return true;
                } else if (itemId == R.id.navigation_chat) {
                    Intent messageIntent = new Intent(this, MessageActivity.class);
                    startActivity(messageIntent);
                    return true;
                } else return itemId == R.id.navigation_profile;
            });
        } else {
            Log.e(TAG, "BottomNavigationView is null. Check your activity_profile.xml layout.");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nameText.setText(getString(R.string.full_name_display, Session.INSTANCE.getUser_first_name(), Session.INSTANCE.getUser_last_name()));
        emailText.setText(Session.INSTANCE.getUser_email());
    }
}
