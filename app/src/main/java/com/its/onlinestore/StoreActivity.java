package com.its.onlinestore;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.its.onlinestore.adapter.StoreSectionsPagerAdapter;

public class StoreActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fabPostProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        StoreSectionsPagerAdapter sectionsPagerAdapter = new StoreSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        fabPostProduct = findViewById(R.id.sa_fab_post_product);

        fabPostProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == fabPostProduct.getId()){
//            Start Post Activity
            startActivity(new Intent(StoreActivity.this,PostProductActivity.class));
        }
    }
}