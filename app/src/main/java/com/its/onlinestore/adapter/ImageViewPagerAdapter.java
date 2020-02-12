package com.its.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> imageUrl;

    public ImageViewPagerAdapter(Context context, ArrayList<String> imageUrl) {
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public int getCount() {
        return imageUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        imageView.setLayoutParams(layoutParams);
        Glide.with(context).load(imageUrl.get(position)).into(imageView);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((ImageView)object);
    }
}
