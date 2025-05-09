package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.trapero.cchoice.adapters.InfiniteDiscountBannerAdapter;
import com.trapero.cchoice.R;
import com.trapero.cchoice.databinding.ActivityDashboardBinding;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private List<Integer> bannerImages;
    private InfiniteDiscountBannerAdapter bannerAdapter;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 discountBannerPager;
    private Handler slideHandler = new Handler();
    private Runnable sliderRunnable;
    private static final long SLIDE_INTERVAL = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize banner images
        bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.discount_banner);
        bannerImages.add(R.drawable.discount1);
        bannerImages.add(R.drawable.discount2);

        // Get the ViewPager2
        discountBannerPager = binding.discountBannerPager;

        // Set up the adapter
        bannerAdapter = new InfiniteDiscountBannerAdapter(this, bannerImages);
        discountBannerPager.setAdapter(bannerAdapter);

        // Set the initial item
        discountBannerPager.setCurrentItem(1, false);

        discountBannerPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    int currentItem = discountBannerPager.getCurrentItem();
                    int lastRealItem = bannerAdapter.getRealItemCount() - 1;
                    int firstInfinite = 0;
                    int lastInfinite = bannerAdapter.getItemCount() - 1;
                    int firstRealInfinite = 1;
                    int lastRealInfinite = lastInfinite - 1;

                    if (currentItem == firstInfinite) {
                        discountBannerPager.setCurrentItem(lastRealInfinite, false);
                    } else if (currentItem == lastInfinite) {
                        discountBannerPager.setCurrentItem(firstRealInfinite, false);
                    }

                    // Resume auto sliding when scrolling is idle
                    startAutoSlide();
                } else if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                    stopAutoSlide();
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int realPosition = (position - 1 + bannerAdapter.getRealItemCount()) % bannerAdapter.getRealItemCount();
                Log.d("ViewPager", "Real position: " + realPosition);
            }
        });

        // Initialize BottomNavigationView
        bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_basket) {
                Intent productListIntent = new Intent(this, ProductListActivity.class);
                startActivity(productListIntent);
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

        // Start auto sliding
        startAutoSlide();
    }

    private void startAutoSlide() {
        slideHandler.removeCallbacks(sliderRunnable);
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = discountBannerPager.getCurrentItem();
                int nextPosition = currentPosition + 1;
                if (nextPosition >= bannerAdapter.getItemCount()) {
                    nextPosition = 0;
                }
                discountBannerPager.setCurrentItem(nextPosition, true);
                slideHandler.postDelayed(this, SLIDE_INTERVAL);
            }
        };
        slideHandler.postDelayed(sliderRunnable, SLIDE_INTERVAL);
    }

    private void stopAutoSlide() {
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoSlide();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoSlide();
    }
}