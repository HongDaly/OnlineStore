package com.its.onlinestore.ui.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.StoreActivity;
import com.its.onlinestore.UserAuthActivity;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Button btnAuth;
    private RelativeLayout rlNoUser;
    private RelativeLayout rlHasUser;
    private Button btnLogout;



//
    private TextView tvHeadName;
    private TextView tvHeadEmail;
    private TextView tvHeadEditProfile;


    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;

    private LinearLayout llStore;



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



        tvHeadEmail = v.findViewById(R.id.fp_tv_head_email);
        tvHeadName = v.findViewById(R.id.fp_tv_head_full_name);
        tvHeadEditProfile = v.findViewById(R.id.fp_tv_head_edit_profile);

        tvEmail = v.findViewById(R.id.fp_tv_email);
        tvName = v.findViewById(R.id.fp_tv_full_name);
        tvPhone = v.findViewById(R.id.fp_tv_phone_number);


        llStore = v.findViewById(R.id.fp_ll_store);



        initUI();



        btnAuth.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        llStore.setOnClickListener(this);

        return v;
    }

    private void initUI(){
        if(FirebaseHelper.getCurrentUser() != null){
            initUserProfileUI();
            rlNoUser.setVisibility(View.INVISIBLE);
            rlHasUser.setVisibility(View.VISIBLE);
        }else {
            rlNoUser.setVisibility(View.VISIBLE);
            rlHasUser.setVisibility(View.INVISIBLE);
        }
    }
    
    private void initUserProfileUI(){
        FirebaseHelper.getUserById(FirebaseHelper.getCurrentUser().getUid())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if(user != null){
                            tvHeadEmail.setText(user.getEmail());
                            tvEmail.setText(user.getEmail());
                            tvHeadName.setText(user.getFullname());
                            tvName.setText(user.getFullname());
                        }
                    }
                });
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
        }else if(id == llStore.getId()){
//            start StoreActivity
            startActivity(new Intent(getContext(), StoreActivity.class));
        }
    }
}
