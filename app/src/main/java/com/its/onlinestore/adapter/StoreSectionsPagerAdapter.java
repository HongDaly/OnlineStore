package com.its.onlinestore.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.its.onlinestore.R;
import com.its.onlinestore.ui.store.contact.StoreContactFragment;
import com.its.onlinestore.ui.store.home.StoreHomeFragment;
import com.its.onlinestore.ui.store.product.ProductFragment;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class StoreSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_home, R.string.tab_product,R.string.tab_contact};


    private Fragment[] fragments = new Fragment[]{
            new StoreHomeFragment(),
            new ProductFragment(),
            new StoreContactFragment()
    };

    private final Context mContext;

    public StoreSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}