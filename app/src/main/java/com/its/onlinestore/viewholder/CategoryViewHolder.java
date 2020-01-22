package com.its.onlinestore.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.its.onlinestore.R;
import com.its.onlinestore.model.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivIcon;
    private TextView tvName;
    private Context context;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.cate_vh_iv_icon);
        tvName = itemView.findViewById(R.id.cate_vh_tv_name);

        context = itemView.getContext();
    }

    public void init(Category category){
        tvName.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(ivIcon);
    }

}
