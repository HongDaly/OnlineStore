package com.its.onlinestore.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseHelper {

    private static FirebaseFirestore database = FirebaseFirestore.getInstance();

    public static Task<QuerySnapshot> getCategories(){
        return  FirebaseFirestore.getInstance().collection("category").get();
    }

}
