package com.its.onlinestore.helper;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.its.onlinestore.model.Product;
import com.its.onlinestore.model.User;

public class FirebaseHelper {

    public static FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

    public static Task<QuerySnapshot> getCategories(){
        return  FirebaseFirestore.getInstance().collection("category").get();
    }

//Register
    public static Task<AuthResult> register(User user){
        return auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword());
    }
//    Login
    public static Task<AuthResult> login(String email,String password){
        return auth.signInWithEmailAndPassword(email,password);
    }

    public static void saveUser(User user){
        database.collection("users").document(user.getId()).set(user);
    }
    //--------Get current----------//////////
    public static FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    //--------Get user by id/////////////////
    public static Task<DocumentSnapshot> getUserById(String id){
        return database.collection("users").document(id).get();
    }

    public static Task<Uri> uploadImage(Uri uri){
        String name = String.valueOf(System.currentTimeMillis());
        String ext = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        final StorageReference reference =  storage.getReference("product/"+name+"."+ext);

        Task<UploadTask.TaskSnapshot> uploadTask = reference.putFile(uri);
        return uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    return null;
                }
                return reference.getDownloadUrl();
            }
        });
    }


//
    public static Task<Void> addProduct(Product product){
        String userId = getCurrentUser().getUid();
        product.setUserId(userId);
        String id = database.collection("product").document().getId();
        product.setId(id);
        return database.collection("product").document(id).set(product);
    }
}
