package com.its.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.adapter.ImageViewPagerAdapter;
import com.its.onlinestore.adapter.ProductAdapter;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Contact;
import com.its.onlinestore.model.Product;

import java.util.ArrayList;

public class ProductInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private TextView tvPrice;
    private TextView tvDescription;
    private ImageView ivBack;
    private ImageView ivSearch;

    private ChipGroup cgCategories;
    private ChipGroup cgTags;
    private RecyclerView rcRelatedProduct;
    private Product product;

    private ViewPager vpImages;
    private ImageViewPagerAdapter adapter;

    private ArrayList<String> imageUrls = new ArrayList<>();

    private TabLayout tabIndicator;

    private ProductAdapter productAdapter;

    private TextView tvStoreName;
    private TextView tvStorePhone;
    private TextView tvStoreAddress;
    private ImageView ivStoreProfile;

    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        tvDescription = findViewById(R.id.api_tv_description);
        tvPrice = findViewById(R.id.api_tv_price);
        tvTitle = findViewById(R.id.api_tv_title);

        ivSearch = findViewById(R.id.api_iv_search);
        ivBack = findViewById(R.id.api_iv_back);

        cgCategories = findViewById(R.id.api_cg_categories);
        cgTags = findViewById(R.id.api_cg_tags);

        vpImages = findViewById(R.id.api_vp_image);

        tabIndicator = findViewById(R.id.api_tab_indicator);


        rcRelatedProduct = findViewById(R.id.api_rc_related_product);


        tvStoreName = findViewById(R.id.api_tv_store_name);
        tvStorePhone = findViewById(R.id.api_tv_store_phone);
        tvStoreAddress = findViewById(R.id.api_tv_store_address);
        ivStoreProfile = findViewById(R.id.api_iv_store_profile);


        ivBack.setOnClickListener(this);
        initUI();


        initRelatedProduct();
        tvStorePhone.setOnClickListener(this);
    }


    private void getContact(Product product){

        FirebaseHelper.getContact(product.getUserId())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot != null){
                            Contact contact = documentSnapshot.toObject(Contact.class);
                            if(contact != null){
                                if(contact.getImageUrl() != null && !contact.getImageUrl().isEmpty()){
                                    Glide.with(getApplicationContext()).load(contact.getImageUrl()).into(ivStoreProfile);
                                }
                                if(contact.getAddress() !=null && !contact.getAddress().isEmpty()){
                                    tvStoreAddress.setText(contact.getAddress());
                                }
                                if(contact.getPhone() != null && !contact.getPhone().isEmpty()){
                                    tvStorePhone.setText(contact.getPhone());
                                }
                                if(contact.getStore() != null && !contact.getStore().isEmpty()){
                                    tvStoreName.setText(contact.getStore());
                                }
                            }
                        }
                    }
                });
    }

    private void initUI(){
        if(getIntent().getExtras() != null){
            product = (Product) getIntent().getExtras().getSerializable("product");
            imageUrls = getIntent().getStringArrayListExtra("image");
            adapter = new ImageViewPagerAdapter(getApplicationContext(),imageUrls);
            vpImages.setAdapter(adapter);
            tabIndicator.setupWithViewPager(vpImages,true);
        }

        if(product != null) {

            getContact(product);

            tvTitle.setText(product.getTitle());
            tvPrice.setText(String.valueOf(product.getPrice()));
            tvDescription.setText(product.getDescription());

            for(String s : product.getCategories()){
                Chip chip = new Chip(this);
                chip.setText(s);
                cgCategories.addView(chip);
            }

            for(String s : product.getTags()){
                Chip chip = new Chip(this);
                chip.setText(s);
                cgTags.addView(chip);
            }

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == ivBack.getId()){
            finish();
        }
        else if(id == tvStorePhone.getId()){
            call(tvStorePhone.getText().toString());
        }
    }


    private void initRelatedProduct(){


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
                                productAdapter = new ProductAdapter(products,getApplicationContext());
                                rcRelatedProduct.setAdapter(productAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }



    private void call(String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] phones = phone.split("-");

        builder.setItems(phones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                open call app in android
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phones[which], null));
                startActivity(intent);
            }
        });
        builder.setTitle("Choose phone number");
        dialog = builder.create();
        dialog.show();
    }
}
