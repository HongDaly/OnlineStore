package com.its.onlinestore.ui.search;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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
public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView rcResult;
    private ProgressBar pbLoading;
    private TextView tvNoItem;
    private ProductAdapter productAdapter;
    ArrayList<Product> products = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.fs_sv_product);
        rcResult  = view.findViewById(R.id.fs_rc_result);

        pbLoading = view.findViewById(R.id.fs_pb_loading);
        tvNoItem = view.findViewById(R.id.fs_tv_msg_no_item);



        initSearch();

        return view;
    }


    private void initSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.isEmpty())
                {
                    tvNoItem.setVisibility(View.INVISIBLE);
                    products.clear();
                    if(productAdapter != null){
                        productAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    applySearch(query);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.isEmpty()) {
                    tvNoItem.setVisibility(View.INVISIBLE);
                    products.clear();
                    if(productAdapter != null){
                        productAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    applySearch(newText);
                }
                return false;
            }
        });
    }

    private void applySearch(final String keyword){
        pbLoading.setVisibility(View.VISIBLE);
        FirebaseHelper.search()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots != null){
                            products.clear();
                            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                Product product = documentSnapshot.toObject(Product.class);
                                if(product != null){
                                    if(product.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                                        products.add(product);
                                    }
                                }
                            }
                            if(products.size() != 0){
                                tvNoItem.setVisibility(View.INVISIBLE);
                            }else {
                                tvNoItem.setVisibility(View.VISIBLE);
                            }

                            productAdapter = new ProductAdapter(products,getContext());
                            productAdapter.notifyDataSetChanged();
                            rcResult.setAdapter(productAdapter);
                        }else {
                            tvNoItem.setVisibility(View.VISIBLE);
                        }

                        pbLoading.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(productAdapter != null){
                    productAdapter.notifyDataSetChanged();
                }
                pbLoading.setVisibility(View.INVISIBLE);
            }
        });
    }
}
