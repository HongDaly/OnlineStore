package com.its.onlinestore.helper;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.its.onlinestore.model.User;

public class FirebaseHelper {

    private static FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static Task<QuerySnapshot> getCategories(){
        return  FirebaseFirestore.getInstance().collection("category").get();
    }


    public static Task<AuthResult> register(User user){
        return auth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword());
    }

    public static void saveUser(User user){
        database.collection("users").document(user.getId()).set(user);
    }


}
