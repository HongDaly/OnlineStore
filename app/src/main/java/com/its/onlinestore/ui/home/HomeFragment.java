package com.its.onlinestore.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.adapter.CategoryAdapter;
import com.its.onlinestore.adapter.ProductAdapter;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Category;
import com.its.onlinestore.model.Product;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rcCategory;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private RecyclerView rcProductList;
    private ProductAdapter productAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rcCategory = root.findViewById(R.id.fh_rc_category);
        rcProductList = root.findViewById(R.id.fh_rc_product_list);

        FirebaseHelper.getCategories().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots != null){
                            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                Category category = documentSnapshot.toObject(Category.class);
                                categories.add(category);
                            }
                            categoryAdapter = new CategoryAdapter(getContext(),categories);
                            rcCategory.setAdapter(categoryAdapter);
                        }
                        Log.d("Error", "onSuccess: ");
                    }
                }
        );



        initProductList();

        return root;

    }

    private void initProductList(){
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