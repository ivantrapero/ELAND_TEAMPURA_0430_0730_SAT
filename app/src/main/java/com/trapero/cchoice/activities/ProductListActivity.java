package com.trapero.cchoice.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trapero.cchoice.R;
import com.trapero.cchoice.adapters.ProductAdapter;
import com.trapero.cchoice.viewmodels.ProductViewModel;

public class ProductListActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_list); // Create this layout

        recyclerView = findViewById(R.id.recyclerProducts); // Ensure this ID exists in your layout
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // You can adjust the span count
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        viewModel.getProducts().observe(this, products -> {
            adapter.setProductList(products);
        });
    }
}