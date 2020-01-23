package com.its.onlinestore.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.adapter.CategoryAdapter;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Category;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rcCategory;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rcCategory = root.findViewById(R.id.hf_rc_category);

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

        return root;

    }
}