package com.its.onlinestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.its.onlinestore.helper.Constant;
import com.its.onlinestore.helper.PermissionControllerHelper;

import java.io.IOException;
import java.util.ArrayList;

public class PostProductActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rcImageGallery;
    private ImageView ivImagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        rcImageGallery = findViewById(R.id.app_rv_image_gallery);
        ivImagePicker = findViewById(R.id.app_iv_pick_image);



        ivImagePicker.setOnClickListener(this);
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
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                if(clipData != null){
                    Log.d("err","image : "+clipData.getItemCount());
                    for(int i = 0;i<clipData.getItemCount();i++){
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),clipData.getItemAt(i).getUri());
                            bitmaps.add(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    ivImagePicker.setImageBitmap(bitmaps.get(0));
                }else {
                    Log.d("err","err");
                }

            }else {
                Log.d("err","err");
                Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_LONG).show();
            }
        }
    }
}
