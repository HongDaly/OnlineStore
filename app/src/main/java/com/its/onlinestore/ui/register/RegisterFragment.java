package com.its.onlinestore.ui.register;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.its.onlinestore.MainActivity;
import com.its.onlinestore.R;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText edtFullname;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        edtFullname = v.findViewById(R.id.fr_edt_fullname);
        edtEmail = v.findViewById(R.id.fr_edt_email);
        edtPassword = v.findViewById(R.id.fr_edt_password);
        btnRegister = v.findViewById(R.id.fr_btn_register);




        btnRegister.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == btnRegister.getId()){
            //register
            if(edtEmail.getText().toString().isEmpty()
                    ||
                    edtPassword.getText().toString().isEmpty() ||
                    edtFullname.getText().toString().isEmpty()
            ){
                Toast.makeText(getContext(),"Please input information",Toast.LENGTH_LONG).show();

            }else {
                final User user = new User(
                        "0",
                        edtFullname.getText().toString() ,
                        edtEmail.getText().toString(),
                        edtPassword.getText().toString());

                FirebaseHelper.register(user)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
//                                Save user to database
                                user.setId(authResult.getUser().getUid());
                                FirebaseHelper.saveUser(user);

                                startActivity(new Intent(getContext(), MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Something went wrong ! Please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}
