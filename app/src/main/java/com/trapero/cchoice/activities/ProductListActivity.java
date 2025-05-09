package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        tempProducts.add(new Product("Hammer", 19.99, 10, 4.5f, 25, R.drawable.ic_hammer, "A versatile tool for driving nails."));
        tempProducts.add(new Product("Saw", 29.99, 5, 4.0f, 18, R.drawable.ic_hammer, "A sharp blade for cutting wood."));
        tempProducts.add(new Product("Drill", 49.99, 15, 4.8f, 32, R.drawable.ic_chisel_set, "A power tool for drilling holes."));
        tempProducts.add(new Product("Chisel Set", 24.99, 8, 4.2f, 20, R.drawable.ic_chisel_set, "A set of tools for shaping wood."));

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
