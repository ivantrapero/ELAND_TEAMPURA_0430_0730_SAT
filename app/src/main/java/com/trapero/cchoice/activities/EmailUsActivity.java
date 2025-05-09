package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import com.trapero.cchoice.R;

public class EmailUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_us);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView contactInfoTextView = findViewById(R.id.contact_info_text);
        String contactInfo = "Email: carpenterschoice@gmail.com\n\n" +
                "Contact Number: +1-555-123-4567\n\n" +
                "Telephone Hotlines:\n" +
                "  Main: +1-800-CARPENTER (227-7368)\n" +
                "  Support: +1-800-HELP-TOOL (435-7866)\n" +
                "  Sales: +1-800-SELL-NOW (735-5669)";
        contactInfoTextView.setText(contactInfo);
    }
}