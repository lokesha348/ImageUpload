package com.task.imageuploadsoft.util;

import static com.task.imageuploadsoft.util.Constants.CAMERA;
import static com.task.imageuploadsoft.util.Constants.READ_EXTERNAL_STORAGE;
import static com.task.imageuploadsoft.util.Constants.READ_MEDIA_AUDIOS;
import static com.task.imageuploadsoft.util.Constants.READ_MEDIA_IMAGES;
import static com.task.imageuploadsoft.util.Constants.READ_MEDIA_VIDEOS;
import static com.task.imageuploadsoft.util.Constants.READ_PHONE_STATE;
import static com.task.imageuploadsoft.util.Constants.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.task.imageuploadsoft.R;

import java.util.ArrayList;
import java.util.List;

public class AppPermissionsRunTime {
    private static AppPermissionsRunTime app_permission = new AppPermissionsRunTime();

    public static AppPermissionsRunTime getInstance() {
        return app_permission;
    }

    private List<String> permissionsNeeded = null;
    private List<String> permissionsList = null;
    private AlertDialog dialog_parent = null;
    final public int REQUEST_CODE_PERMISSIONS = Constants.REQUEST_CODE;

    public boolean getPermission(final ArrayList<Permission> permission_list, Activity activity) {
        /*
         * Creating the List if not created .
         * if created then clear the list for refresh use.*/
        if (permissionsNeeded == null || permissionsList == null) {
            permissionsNeeded = new ArrayList<>();
            permissionsList = new ArrayList<>();
        } else {
            permissionsNeeded.clear();
            permissionsList.clear();
        }

        if (dialog_parent != null && dialog_parent.isShowing()) {
            dialog_parent.dismiss();
            dialog_parent.cancel();
        }
        for (int count = 0; permission_list != null && count < permission_list.size(); count++) {
            switch (permission_list.get(count)) {
                case CAMERA:
                    if (!addPermission(permissionsList, Manifest.permission.CAMERA, activity)) {

                        permissionsNeeded.add(CAMERA);

                    }
                    break;
                case READ_EXTERNAL_STORAGE:
                    if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE, activity)) {
                        permissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
                    }
                    break;
                case WRITE_EXTERNAL_STORAGE:
                    if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity)) {
                        permissionsNeeded.add(READ_EXTERNAL_STORAGE);
                    }
                    break;
                case PHONE:
                    if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE, activity)) {
                        permissionsNeeded.add(READ_PHONE_STATE);
                    }
                    break;
                case READ_MEDIA_IMAGE:
                    if (!addPermission(permissionsList, Manifest.permission.READ_MEDIA_IMAGES, activity)) {
                        permissionsNeeded.add(READ_MEDIA_IMAGES);
                    }
                case READ_MEDIA_AUDIO:
                    if (!addPermission(permissionsList, Manifest.permission.READ_MEDIA_AUDIO, activity)) {
                        permissionsNeeded.add(READ_MEDIA_AUDIOS);
                    }
                    break;
                case READ_MEDIA_VIDEO:
                    if (!addPermission(permissionsList, Manifest.permission.READ_MEDIA_VIDEO, activity)) {
                        permissionsNeeded.add(READ_MEDIA_VIDEOS);
                    }
                    break;
                default:
                    break;
            }

        }
        if (permissionsList.size() > 0 && permissionsNeeded.size() > 0) {
            StringBuilder message = new StringBuilder(activity.getString(R.string.permission_grant_access)
                    + permissionsNeeded.get(0));
            for (int i = 1; i < permissionsNeeded.size(); i++) {
                message.append(", ").append(permissionsNeeded.get(i));
            }
            check_for_Permission(permissionsList.toArray(new String[permissionsList.size()]), activity);
            return false;
        } else {
            return true;
        }
    }

    public void check_for_Permission(String[] permissions, Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            return false;
        } else {
            return true;
        }
    }

    public enum Permission {
        CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, PHONE, READ_MEDIA_IMAGE, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO
    }
}
