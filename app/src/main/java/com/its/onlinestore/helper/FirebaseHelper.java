package com.its.onlinestore.helper;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.its.onlinestore.R;
import com.its.onlinestore.model.Category;
import com.its.onlinestore.model.Contact;
import com.its.onlinestore.model.Product;
import com.its.onlinestore.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    private static Task<Uri> uploadImage(Uri uri){
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
    public static Task<Void> addProduct(Product product, ImageView[] images){
        String userId = getCurrentUser().getUid();
        product.setUserId(userId);
        final String id = database.collection("product").document().getId();
        product.setId(id);
        for (final ImageView image : images) {
            final Uri uri = (Uri) image.getTag();
            if (uri != null) {
                FirebaseHelper.uploadImage(uri).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            if (downloadUri != null) {
                                Map<String, String> docData = new HashMap<>();
                                docData.put("image_url",downloadUri.toString());
                                docData.put("product_id",id);
                                database.collection("feature_image")
                                        .add(docData);
                            }
                        }
                    }
                });
            }
        }
        Long create_at = System.currentTimeMillis();
        product.setCreated_at(create_at);
        return database.collection("product").document(id).set(product);
    }



//
    public static Task<QuerySnapshot> getProduct(){
        return database.collection("product").get();
    }
//
    public static Task<QuerySnapshot> getProductImages(String productId){
        return database.collection("feature_image").whereEqualTo("product_id",productId).get();
    }

// get Related Product

//    public static Task<QuerySnapshot> getRelatedProduct(){

//        database.collection("product")
//                .whereEqualTo()
//    }


//   get Event
    public static Task<QuerySnapshot> getEvents(){
        return database.collection("event").get();
    }


//  search
    public static Task<QuerySnapshot> search(){
        return database
                .collection("product")
                .get();
    }
// add contact

    private static Task<Uri> uploadImageContact(Uri uri){
        String name = String.valueOf(System.currentTimeMillis());
        String ext = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        final StorageReference reference =  storage.getReference("contact/"+name+"."+ext);

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
    public  static  Task<Void> updateContact(final Contact contact, Uri uri){
        if(uri != null){
            uploadImageContact(uri)
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            contact.setImageUrl(uri.toString());
                            Map<String, String> docData = new HashMap<>();
                            docData.put("imageUrl",uri.toString());
                            database.collection("contact")
                                    .document(contact.getId())
                                    .set(docData, SetOptions.merge());
                        }
                    });
        }
        contact.setId(contact.getUser_id());
        return  database.collection("contact").document(contact.getId()).set(contact,SetOptions.merge());

    }
//    get contact
    public static Task<DocumentSnapshot> getContact(String userId){
        return database.collection("contact").document(userId).get();
    }

//
    public  static Task<QuerySnapshot> getProductByCategory(Category category){
        return  database
                .collection("product")
                .whereArrayContainsAny("categories",category.getSub_category())
                .get();
    }

}
