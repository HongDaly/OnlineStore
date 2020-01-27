package com.its.onlinestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.its.onlinestore.adapter.UserAuthViewPagerAdapter;

public class UserAuthActivity extends AppCompatActivity {

    private ViewPager vpAuth;
    private UserAuthViewPagerAdapter authViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        vpAuth = findViewById(R.id.uaa_view_pager);

        authViewPagerAdapter = new UserAuthViewPagerAdapter(getSupportFragmentManager());

        vpAuth.setAdapter(authViewPagerAdapter);
    }
}
