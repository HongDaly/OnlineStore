package com.its.onlinestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.its.onlinestore.helper.Constant;
import com.its.onlinestore.helper.FirebaseHelper;
import com.its.onlinestore.helper.PermissionControllerHelper;
import com.its.onlinestore.model.Category;
import com.its.onlinestore.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivImagePicker;
    private ImageView[] imageViews = new ImageView[4];
    private LinearLayout llChooseCategory;
    private ChipGroup cgCategories;

    private EditText edtProductTitle;
    private EditText edtProductPrice;
    private EditText edtProductDiscount;
    private EditText edtProductDescription;
    private EditText edtProductTag;
    private Button btnPost;

    private Product product = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        ivImagePicker = findViewById(R.id.app_iv_pick_image);
        imageViews[0] = findViewById(R.id.app_image_1);
        imageViews[1] = findViewById(R.id.app_image_2);
        imageViews[2] = findViewById(R.id.app_image_3);
        imageViews[3] = findViewById(R.id.app_image_4);
        cgCategories = findViewById(R.id.app_cg_categories);

        edtProductTitle = findViewById(R.id.app_edt_product_title);
        edtProductPrice = findViewById(R.id.app_edt_product_price);
        edtProductDiscount = findViewById(R.id.app_edt_product_discount);
        edtProductDescription = findViewById(R.id.app_edt_product_description);
        edtProductTag = findViewById(R.id.app_edt_tag);
        btnPost = findViewById(R.id.app_btn_post);


        llChooseCategory = findViewById(R.id.app_ll_choose_category);

        btnPost.setOnClickListener(this);
        ivImagePicker.setOnClickListener(this);
        llChooseCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == ivImagePicker.getId()){
//            Open Gallery and Select Images
            if(PermissionControllerHelper.checkPermissionStorage(this)){
//              Open Galley
                openGallery();
                Toast.makeText(getApplicationContext(),"Open Gallery",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Permission Denail",Toast.LENGTH_LONG).show();
            }
        }else if(id == llChooseCategory.getId()){
//            Choose category
            chooseCategory();
        }else if (id == btnPost.getId()){

//
//Upload Image to Firebase Storage
            for(int i = 0 ; i < imageViews.length;i++){
                Uri uri = (Uri) imageViews[i].getTag();
                if(uri != null){
                    FirebaseHelper.uploadImage(uri).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUri = task.getResult();
                                Log.d("image",downloadUri.toString());
                            }
                        }
                    });
                }
            }

//            product.setTitle(edtProductTitle.getText().toString());
//            product.setDescription(edtProductDescription.getText().toString());
//            product.setDiscount(Float.valueOf(edtProductDiscount.getText().toString()));
//            product.setPrice(Float.valueOf(edtProductPrice.getText().toString()));

        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, Constant.IMAGE_PICK_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.IMAGE_PICK_REQUEST_CODE
                && resultCode == RESULT_OK){
//

            if(data != null){
                ClipData clipData = data.getClipData();
                if(clipData != null){
                    Log.d("err","image : "+clipData.getItemCount());
                    if(clipData.getItemCount() <= 4){
                        for(int i = 0;i<clipData.getItemCount();i++){
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),clipData.getItemAt(i).getUri());
                                imageViews[i].setImageBitmap(bitmap);
                                imageViews[i].setTag(clipData.getItemAt(i).getUri());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        for(int i = 0;i<4;i++){
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),clipData.getItemAt(i).getUri());
                                imageViews[i].setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else {
                    Log.d("err","err");
                }

            }else {
                Log.d("err","err");
                Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_LONG).show();
            }
        }
    }





//    Category Choose Dialog;

    private void chooseCategory(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        get Categories
        FirebaseHelper.getCategories().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots != null){
                    ArrayList<String> categories = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        Category category = documentSnapshot.toObject(Category.class);
                        if(category != null){
                            categories.addAll(category.getSub_category());
                        }
                    }

                    if(categories.size() != 0){
                        final String[] categoriesChooser = new String[categories.size()];
                        final boolean[] checkedCategory = new boolean[categories.size()];
                        for(int i = 0 ; i < categories.size();i++){
                            categoriesChooser[i] = categories.get(i);
                            checkedCategory[i] = false;
                        }


                        builder.setMultiChoiceItems(categoriesChooser, checkedCategory, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedCategory[which] = isChecked;
                            }
                        });


                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click positive button
//                              selected items display in chip group
                                ArrayList<String> chooseCategories = new ArrayList<>();
                                for (int i = 0 ; i<categoriesChooser.length;i++){
                                    if(checkedCategory[i]){
                                        chooseCategories.add(categoriesChooser[i]);
                                    }
                                }
                                dialog.dismiss();
                                initCategoryToChipGroup(chooseCategories);
                            }
                        });

                        // Set the neutral/cancel button click listener
                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click the neutral button
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        // Display the alert dialog on interface
                        dialog.show();

                    }
                }
            }
        });
    }
    private void initCategoryToChipGroup(ArrayList<String> categories){
        for (String cate : categories){
            Chip chip = new Chip(this);
            chip.setText(cate);
            cgCategories.addView(chip);
        }
    }
}
