package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trapero.cchoice.R;
import com.trapero.cchoice.adapters.ProductAdapter;
import com.trapero.cchoice.models.Product;
import com.trapero.cchoice.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Log.d("ProductListActivity", "onCreate() called");

        recyclerView = findViewById(R.id.recyclerProducts);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (recyclerView == null || bottomNavigationView == null) {
            Log.e("ProductListActivity", "Missing view(s) in layout. Check fragment_product_list.xml.");
            return;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Sample data
        List<Product> tempProducts = new ArrayList<>();
        tempProducts.add(new Product(0,"Hammer", 19.99, 10, 4.5f, 25, "https://www.gigatools.ph/cdn/shop/products/RT0702CX3-L.jpg?v=1665562307 ", "A versatile tool for driving nails."));
        tempProducts.add(new Product(0,"Saw", 29.99, 5, 4.0f, 18, "https://www.ubuy.com.ph/product/4EU4LG-dewalt-sliding-compound-miter-saw-12-inch-dws779", "A sharp blade for cutting wood."));
        tempProducts.add(new Product(0,"Drill", 49.99, 15, 4.8f, 32, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTzOmn6ZeieFl__yJwrg-q44QotUrq53aqLw&s", "A power tool for drilling holes."));
        tempProducts.add(new Product(0,"Chisel Set", 24.99, 8, 4.2f, 20, "https://media.rs-online.com/Y2792235-01.jpg", "A set of tools for shaping wood."));

        viewModel.getProducts().observe(this, products -> {

            Log.d("ProductListActivitadadadaday", "Observed product list of size: "+products.toString());
            adapter.setProductList(products);
        });


        viewModel.getToast().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        viewModel.setTempProducts(tempProducts);

        viewModel.getProducts().observe(this, products -> {
            Log.d("ProductListActivity", "Observed product list of size: " + (products != null ? products.size() : 0));
            adapter.setProductList(products);
        });

        // Set the item click listener for the adapter
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                // Start ProductDetailActivity and pass the product data
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_home) {
                startActivity(new Intent(this, DashboardActivity.class));
            } else if (id == R.id.navigation_chat) {
                startActivity(new Intent(this, MessageActivity.class));
            } else if (id == R.id.navigation_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            }
            return true;
        });
    }
}
