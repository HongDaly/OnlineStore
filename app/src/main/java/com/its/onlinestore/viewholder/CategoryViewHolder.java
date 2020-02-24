package com.its.onlinestore.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.its.onlinestore.CateProductActivity;
import com.its.onlinestore.R;
import com.its.onlinestore.model.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView ivIcon;
    private TextView tvName;
    private Context context;
    private Category category;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.cate_vh_iv_icon);
        tvName = itemView.findViewById(R.id.cate_vh_tv_name);

        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void init(Category category){
        this.category = category;
        tvName.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(ivIcon);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == itemView.getId()){
            Intent intent = new Intent(itemView.getContext(), CateProductActivity.class);
            intent.putExtra("cate",category);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(intent);
        }
    }
}
