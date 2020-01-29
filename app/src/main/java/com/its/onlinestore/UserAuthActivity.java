package com.its.onlinestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.its.onlinestore.adapter.UserAuthViewPagerAdapter;

public class UserAuthActivity extends AppCompatActivity {

    private ViewPager vpAuth;
    private UserAuthViewPagerAdapter authViewPagerAdapter;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        vpAuth = findViewById(R.id.uaa_view_pager);
        ivBack = findViewById(R.id.uaa_iv_back);

        authViewPagerAdapter = new UserAuthViewPagerAdapter(getSupportFragmentManager());

        vpAuth.setAdapter(authViewPagerAdapter);



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
