package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trapero.cchoice.R;
import com.trapero.cchoice.adapters.ProductAdapter;
import com.trapero.cchoice.viewmodels.ProductViewModel;

public class ProductListActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_list);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        recyclerView = findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        viewModel.getProducts().observe(this, products -> {
            adapter.setProductList(products);
        });

        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    Intent profileIntent = new Intent(this, DashboardActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (itemId == R.id.navigation_basket) {
                    return true;
                } else if (itemId == R.id.navigation_chat) {
                    Intent messageIntent = new Intent(this, MessageActivity.class);
                    startActivity(messageIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            });
        }
    }
}
