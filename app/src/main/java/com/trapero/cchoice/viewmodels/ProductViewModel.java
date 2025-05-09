package com.trapero.cchoice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import com.trapero.cchoice.models.Product;
import com.trapero.cchoice.R;

public class    ProductViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final List<Product> productList = new ArrayList<>();

    public ProductViewModel() {
        loadInitialProducts(); // Load products in the constructor
    }
    private void loadInitialProducts() {

        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product("Initial Hammer", 100.00, 10, 4.0f, 5, R.drawable.ic_hammer));
        productList.addAll(initialProducts);
        products.setValue(productList);
    }


    public void addProduct(Product product) {
        productList.add(product);
        products.setValue(productList);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }
}
