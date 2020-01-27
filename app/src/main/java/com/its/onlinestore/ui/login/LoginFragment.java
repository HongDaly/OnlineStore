package com.its.onlinestore.ui.login;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.its.onlinestore.MainActivity;
import com.its.onlinestore.R;
import com.its.onlinestore.helper.FirebaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private ProgressBar pbLoading;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_login, container, false);

        edtEmail = v.findViewById(R.id.fl_edt_email);
        edtPassword = v.findViewById(R.id.fl_edt_password);
        btnLogin = v.findViewById(R.id.fl_btn_login);
        pbLoading = v.findViewById(R.id.fl_pb_loading);




        btnLogin.setOnClickListener(this);

        return  v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == btnLogin.getId()){
            String email = edtEmail.getText().toString();
            String pass = edtPassword.getText().toString();
            pbLoading.setVisibility(View.VISIBLE);
            if(email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getContext(),"Please input information",Toast.LENGTH_LONG).show();
            }else{
                FirebaseHelper.login(email,pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                pbLoading.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pbLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(),"Something went wrong ! Please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
