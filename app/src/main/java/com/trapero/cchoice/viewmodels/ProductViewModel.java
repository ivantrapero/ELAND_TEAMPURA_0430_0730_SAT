package com.trapero.cchoice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import com.trapero.cchoice.models.Product;
import com.trapero.cchoice.R;

public class ProductViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();

    public ProductViewModel() {
        loadProducts();
    }

    private void loadProducts() {
        List<Product> dummyList = new ArrayList<>();
        dummyList.add(new Product("Claw Hammer", 200.00, 20, 4.5f, 19, R.drawable.ic_hammer));
        dummyList.add(new Product("Assorted Files", 140.00, 20, 4.0f, 9, R.drawable.ic_files));
        dummyList.add(new Product("Wood Chisel Set", 273.00, 20, 4.3f, 19, R.drawable.ic_chisel_set));
        dummyList.add(new Product("Wood Chisel Set", 273.00, 20, 4.3f, 19, R.drawable.ic_chisel_set));
        dummyList.add(new Product("Wood Chisel Set", 273.00, 20, 4.3f, 19, R.drawable.ic_chisel_set));
        products.setValue(dummyList);
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }
}
