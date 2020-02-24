package com.its.onlinestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.adapter.ProductAdapter;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Category;
import com.its.onlinestore.model.Product;

import java.util.ArrayList;

public class CateProductActivity extends AppCompatActivity {

    private RecyclerView rcListProduct;
    private TextView tvNoProduct;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate_product);

        rcListProduct = findViewById(R.id.cpa_rc_product_list);
        tvNoProduct = findViewById(R.id.cpa_tv_no_product);

        if(getIntent().getExtras() != null){
            Category category = (Category) getIntent().getExtras().getSerializable("cate");
            if(category != null){
//                get product with cate
                initUI(category);
            }
        }

    }

    private void initUI(Category category){
        tvNoProduct.setVisibility(View.INVISIBLE);
        FirebaseHelper.getProductByCategory(category)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Product> products = new ArrayList<>();
                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Product product = snapshot.toObject(Product.class);
                                products.add(product);
                            }
                        }
                        if(products.size() != 0){
                            productAdapter = new ProductAdapter(products,getApplicationContext());
                            rcListProduct.setAdapter(productAdapter);
                        }else {
                            tvNoProduct.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
