package com.trapero.cchoice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trapero.cchoice.R;

import java.util.ArrayList;
import java.util.List;

public class InfiniteDiscountBannerAdapter extends RecyclerView.Adapter<InfiniteDiscountBannerAdapter.BannerViewHolder> {

    private Context context;
    private List<Integer> bannerImages;
    private List<Integer> infiniteBannerImages;

    public InfiniteDiscountBannerAdapter(Context context, List<Integer> bannerImages) {
        this.context = context;
        this.bannerImages = bannerImages;
        this.infiniteBannerImages = createInfiniteList(bannerImages);
    }

    private List<Integer> createInfiniteList(List<Integer> originalList) {
        if (originalList == null || originalList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> infiniteList = new ArrayList<>(originalList);
        infiniteList.add(0, originalList.get(originalList.size() - 1)); // Add last to the beginning
        infiniteList.add(originalList.get(0)); // Add first to the end
        return infiniteList;
    }

    public int getRealItemCount() {
        return bannerImages.size();
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discount_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.bannerImageView.setImageResource(infiniteBannerImages.get(position));
    }

    @Override
    public int getItemCount() {
        return infiniteBannerImages.size();
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerImageView = itemView.findViewById(R.id.discountBannerImage);
        }
    }
}