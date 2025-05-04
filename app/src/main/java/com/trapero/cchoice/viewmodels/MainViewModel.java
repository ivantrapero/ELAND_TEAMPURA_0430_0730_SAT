package com.trapero.cchoice.viewmodels;

import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.ViewModel;

import com.trapero.cchoice.activities.LoginActivity;

public class MainViewModel extends ViewModel {

    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void onGetStartedClicked() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
