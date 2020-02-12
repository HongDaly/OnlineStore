package com.its.onlinestore.viewholder;

import android.content.Intent;
import android.os.Parcelable;
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
import com.its.onlinestore.ProductInfoActivity;
import com.its.onlinestore.R;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Product;
import com.its.onlinestore.model.ProductImage;

import java.util.ArrayList;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvPrice;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private Product product;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        ivImage = itemView.findViewById(R.id.vhp_iv_image);
        tvTitle = itemView.findViewById(R.id.vhp_tv_title);
        tvPrice = itemView.findViewById(R.id.vhp_tv_price);
        itemView.setOnClickListener(this);

    }

    public void init(Product product){
        this.product = product;
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
                                    imageUrls.add(productImage.getImage_url());
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

    @Override
    public void onClick(View v) {
        if(v.getId() == itemView.getId()){
            Intent intent = new Intent(ivImage.getContext(), ProductInfoActivity.class);

            intent.putExtra("product",product);
            intent.putStringArrayListExtra("image",imageUrls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(intent);
        }
    }
}
