package com.trapero.cchoice.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.trapero.cchoice.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView aboutUsDescriptionTextView = findViewById(R.id.about_us_description);
        String aboutUsText = "Carpenter’s Choice is a mobile application designed to streamline and enhance the woodworking industry by providing an all-in-one platform for tool rentals and purchases. Whether you are a professional carpenter, a DIY enthusiast, personal use, or a construction business owner, ensures that you have easy access to the tools you need, precisely when you need them.\n\n" +
                "At its core, Carpenter’s Choice functions as a dynamic marketplace where users can rent or buy a wide range of high-quality tools and equipment. The application eliminates the hassle of searching for tools by offering a centralized hub where availability, pricing, and rental durations are transparent and easy to manage. With its user-friendly interface, Carpenter’s Choice simplifies the process of acquiring tools, whether for short-term projects or long-term investments.\n\n" +
                "Beyond its marketplace capabilities, Carpenter’s Choice is equipped with advanced features designed to optimize the user experience and improve workflow efficiency. Real-time inventory tracking allows users to check tool availability instantly, while a secure reservation system ensures that users can lock in the tools they need without delays. Additionally, the platform offers flexible rental options, allowing users to select timeframes that align with their project timelines.\n\n" +
                "For suppliers and tool owners, Carpenter’s Choice presents an opportunity to monetize idle equipment by listing their tools for rent or sale. The seller program provides exposure to a broader audience, enabling businesses and individual owners to maximize their revenue potential. With an integrated rating and review system, users can make informed decisions based on community feedback, ensuring reliability and quality.\n\n" +
                "Carpenter’s Choice is more than just a transactional platform; it is a digital ecosystem that fosters efficiency and collaboration within the woodworking and construction sectors. The app integrates seamless logistics solutions, ensuring on-time tool deliveries and pickups, reducing downtime, and enhancing project productivity. Additionally, it promotes sustainable practices by encouraging tool sharing, reducing unnecessary purchases, and minimizing resource waste.\n\n" +
                "By harnessing the power of technology, Carpenter’s Choice envisions a future where woodworkers, builders, and craftsmen have immediate, hassle-free access to the tools they need to bring their projects to life. The application empowers professionals and hobbyists alike to work smarter, reduce costs, and embrace a more efficient approach to tool management. With Carpenter’s Choice, the right tools are always within reach, helping users achieve precision, creativity, and excellence in every project.";
        aboutUsDescriptionTextView.setText(aboutUsText);
    }
}