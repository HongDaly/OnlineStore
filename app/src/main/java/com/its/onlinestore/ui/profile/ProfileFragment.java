package com.its.onlinestore.ui.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.its.onlinestore.R;
import com.its.onlinestore.UserAuthActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        btnAuth = v.findViewById(R.id.fp_btn_user_auth);

        btnAuth.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnAuth.getId()){
//
            startActivity(new Intent(getContext(), UserAuthActivity.class));

        }
    }
}
