package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trapero.cchoice.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editFullName, editEmail, editPassword, editAddress, editContactNumber;
    private Button saveButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize UI elements
        editFullName = findViewById(R.id.edit_full_name);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        editAddress = findViewById(R.id.edit_address);
        editContactNumber = findViewById(R.id.edit_contact_number);
        saveButton = findViewById(R.id.save_button);

        editFullName.setText("Lebrown");
        editEmail.setText("blackmamba@gmail.com");
        editPassword.setText("password123");
        editAddress.setText("123 Main St, Anytown");
        editContactNumber.setText("+1-555-123-4567");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values
                String fullName = editFullName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString(); // NEVER store passwords in plain text!
                String address = editAddress.getText().toString();
                String contactNumber = editContactNumber.getText().toString();

                // Perform validation (Important!)
                if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Stop if any field is empty
                }

                //  Here, you would save the updated profile information to a database or other storage.
                //  DO NOT store the password directly.  Use a secure hashing method (e.g., bcrypt).

                // Display a success message
                Toast.makeText(EditProfileActivity.this, "Profile Saved!\n" +
                        "Name: " + fullName + "\n" +
                        "Email: " + email + "\n" +
                        "Address: " + address + "\n" +
                        "Contact: " + contactNumber, Toast.LENGTH_LONG).show();
                finish(); // Close the activity and go back
            }
        });
    }
}