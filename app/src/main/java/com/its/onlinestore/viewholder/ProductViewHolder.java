package com.its.onlinestore.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Product;
import com.its.onlinestore.model.ProductImage;

import java.util.ArrayList;

public class ProductViewHolder  extends RecyclerView.ViewHolder {

    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvPrice;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        ivImage = itemView.findViewById(R.id.vhp_iv_image);
        tvTitle = itemView.findViewById(R.id.vhp_tv_title);
        tvPrice = itemView.findViewById(R.id.vhp_tv_price);

    }

    public void init(Product product){
        tvTitle.setText(product.getTitle());
        tvPrice.setText(String.valueOf(product.getPrice()));
        initImage(product.getId());
    }

    private void initImage(final String productId){
        FirebaseHelper.getProductImages(productId)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<ProductImage> productImages = new ArrayList<>();
                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                ProductImage productImage = snapshot.toObject(ProductImage.class);
                                if(productImage != null){
                                    productImages.add(productImage);
                                }
                            }
                            if(productImages.size() != 0){
//
                                Glide.with(itemView.getContext())
                                        .load(productImages.get(0).getImage_url())
                                        .into(ivImage);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
