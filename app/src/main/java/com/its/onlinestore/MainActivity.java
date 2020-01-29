package com.its.onlinestore;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.its.onlinestore.helper.Constant;
import com.its.onlinestore.helper.PermissionControllerHelper;
import com.master.permissionhelper.PermissionHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        InitialApp
        FirebaseApp.initializeApp(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        PermissionControllerHelper.getPermission(this).request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
            }
            @Override
            public void onIndividualPermissionGranted(@NotNull String[] strings) {
            }
            @Override
            public void onPermissionDenied() {
                Toast.makeText(MainActivity.this,"Please allow permission to access full features!",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onPermissionDeniedBySystem() {
                Toast.makeText(MainActivity.this,"Permission denied by system !",Toast.LENGTH_LONG).show();
            }
        });


    }


}
