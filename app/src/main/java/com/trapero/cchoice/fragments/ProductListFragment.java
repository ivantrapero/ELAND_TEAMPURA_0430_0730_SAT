package com.trapero.cchoice.fragments; // Changed package

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trapero.cchoice.R; // Changed R import
import com.trapero.cchoice.adapters.ProductAdapter; // Changed import
import com.trapero.cchoice.viewmodels.ProductViewModel; // Changed import

public class ProductListFragment extends Fragment {

    private ProductAdapter adapter;
    private ProductViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.setProductList(products);
        });
    }
}