package com.its.onlinestore.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.master.permissionhelper.PermissionHelper;

public class PermissionControllerHelper {

    public static PermissionHelper getPermission(Activity activity){
        return new PermissionHelper(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}
                , Constant.PERMISSION_REQUEST_CODE
        );
    }

    public static boolean checkPermissionStorage(Activity activity){
        return getPermission(activity).checkSelfPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}
        );
    }
}
