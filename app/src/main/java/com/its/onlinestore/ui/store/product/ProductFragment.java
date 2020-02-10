package com.its.onlinestore.ui.store.product;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.adapter.ProductAdapter;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private RecyclerView rcProductList;
    private ProductAdapter productAdapter;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_product, container, false);
        rcProductList = v.findViewById(R.id.fsp_rc_product_list);



        initData();

        return v;
    }

    private void initData(){
        FirebaseHelper.getProduct()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Product> products = new ArrayList<>();
                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Product product = snapshot.toObject(Product.class);
                                if(product != null){
                                    products.add(product);
                                }
                            }
                            if(products.size() != 0){
                                productAdapter = new ProductAdapter(products,getContext());
                                rcProductList.setAdapter(productAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }

}
