package com.its.onlinestore.ui.store.contact;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.onlinestore.R;
import com.its.onlinestore.helper.Constant;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.model.Contact;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreContactFragment extends Fragment implements View.OnClickListener {

    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtStore;
    private ImageView ivStore;
    private Button btnUpdate;
    private Uri imageUri;


    public StoreContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_store_contact, container, false);

        edtPhone = v.findViewById(R.id.fsc_edt_phone);
        edtAddress = v.findViewById(R.id.fsc_edt_address);
        edtStore = v.findViewById(R.id.fsc_edt_store);
        ivStore = v.findViewById(R.id.fsc_iv_store);

        btnUpdate = v.findViewById(R.id.fsc_btn_update);


        ivStore.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        getContact();

        return v;
    }

    private void getContact(){
        FirebaseHelper.getContact(FirebaseHelper.getCurrentUser().getUid())
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot != null){
                            Contact contact = documentSnapshot.toObject(Contact.class);
                            if(contact!=null){
                                initUI(contact);
                            }
                        }
                    }
                });


    }
    private void initUI(Contact contact){
        edtStore.setText(contact.getStore());
        edtPhone.setText(contact.getPhone());
        edtAddress.setText(contact.getAddress());
        if(contact.getImageUrl()!=null && !contact.getImageUrl().isEmpty()){
            Glide.with(getContext())
                    .load(contact.getImageUrl()).into(ivStore);

            ivStore.setTag(R.id.fsc_iv_store,contact.getImageUrl());
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == ivStore.getId()){
            openGallery();
        }else if(id == btnUpdate.getId()){
//            Update
            String user_id = FirebaseHelper.getCurrentUser().getUid();
            Contact contact = new Contact(
                    ""
                    ,edtPhone.getText().toString()
                    ,edtAddress.getText().toString()
                    ,edtStore.getText().toString()
                    ,user_id
            );
            if(ivStore.getTag(R.id.fsc_iv_store)!=null){
                contact.setImageUrl(String.valueOf(ivStore.getTag(R.id.fsc_iv_store)));
            }
            FirebaseHelper.updateContact(contact,imageUri)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Contact info updated!",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }



    private void openGallery(){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Constant.IMAGE_PICK_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.IMAGE_PICK_REQUEST_CODE && resultCode == getActivity().RESULT_OK){
            if(data != null){

                imageUri = data.getData();
                final InputStream imageStream;
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    ivStore.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
