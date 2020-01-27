package com.its.onlinestore.ui.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseUser;
import com.its.onlinestore.R;
import com.its.onlinestore.UserAuthActivity;
import com.its.onlinestore.helper.FirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnAuth;
    private RelativeLayout rlNoUser;
    private RelativeLayout rlHasUser;
    private Button btnLogout;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        btnAuth = v.findViewById(R.id.fp_btn_user_auth);
        rlHasUser = v.findViewById(R.id.fp_rl_has_user);
        rlNoUser = v.findViewById(R.id.fp_rl_none_user);
        btnLogout = v.findViewById(R.id.fp_btn_user_logout);


        initUI();



        btnAuth.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        return v;
    }

    private void initUI(){
        if(FirebaseHelper.getCurrentUser() != null){
            rlNoUser.setVisibility(View.INVISIBLE);
            rlHasUser.setVisibility(View.VISIBLE);
        }else {
            rlNoUser.setVisibility(View.VISIBLE);
            rlHasUser.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnAuth.getId()){
//
            startActivity(new Intent(getContext(), UserAuthActivity.class));

        }else if(id == btnLogout.getId()){
            FirebaseHelper.auth.signOut();
            initUI();
        }
    }
}
