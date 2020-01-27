package com.its.onlinestore.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.its.onlinestore.ui.login.LoginFragment;
import com.its.onlinestore.ui.register.RegisterFragment;

public class UserAuthViewPagerAdapter extends FragmentStatePagerAdapter {



    public UserAuthViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0){
            fragment = new RegisterFragment();
        }else if(position == 1){
            fragment = new LoginFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
