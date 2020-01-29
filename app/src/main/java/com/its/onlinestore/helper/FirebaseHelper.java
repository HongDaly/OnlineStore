package com.its.onlinestore.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.model.User;

public class FirebaseHelper {

    public static FirebaseFirestore database = FirebaseFirestore.getInstance();
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

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

    public static FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }


    public static Task<DocumentSnapshot> getUserById(String id){
        return database.collection("users").document(id).get();
    }

}
