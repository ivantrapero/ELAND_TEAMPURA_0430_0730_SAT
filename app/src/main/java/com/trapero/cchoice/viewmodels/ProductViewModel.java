package com.trapero.cchoice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.trapero.cchoice.R;
import com.trapero.cchoice.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final List<Product> productList = new ArrayList<>();

    public ProductViewModel() {
        loadInitialProducts();
    }

    private void loadInitialProducts() {
        productList.add(new Product("Initial Hammer", 100.00, 10, 4.0f, 5, R.drawable.ic_hammer, "Initial Hammer Description"));
        productList.add(new Product("Initial Saw", 50.00, 5, 3.5f, 10, R.drawable.ic_chisel_set, "Initial Saw Description"));
        products.setValue(productList);
    }

    public void setTempProducts(List<Product> tempProducts) {
        productList.clear();
        productList.addAll(tempProducts);
        products.setValue(productList);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }
}
