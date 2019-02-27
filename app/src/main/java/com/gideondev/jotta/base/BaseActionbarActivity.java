package com.gideondev.jotta.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import com.gideondev.jotta.base.presenter.Presenter;

/**
 * Created by Enny on 29/11/2016.
 */
public abstract class BaseActionbarActivity
    extends AppCompatActivity
        implements View.OnClickListener {

    private android.support.v4.app.FragmentManager mFragmentManager;

    private final int KEY_PERMISSION = 200;
 //   private PermissionResult permissionResult;
    private String permissionsAsk[];

    public View mRoot;

    public abstract void initView();

    public abstract void initModel();

    public abstract Presenter getPresenter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTransaction();
        mFragmentManager = getSupportFragmentManager();

    }

    /**
     * Switch content tab
     *
     * @param fragment
     */
    public void switchContent(int contentId, Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(contentId, fragment);
        transaction.commit();
    }

    private void initTransaction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition enterTrans = new Explode();
            getWindow().setEnterTransition(enterTrans);
        }
    }


    @Override
    public void onClick(View v) {

    }

    abstract protected void injectInjector() ;


    @Override
    public void onResume() {
        super.onResume();

        if (null != getPresenter()) {
            getPresenter().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != getPresenter()) {
            getPresenter().pause();
        }
    }

    @Override
    public void onDestroy() {
        if (null != getPresenter()) {
            getPresenter().destroy();
        }
        super.onDestroy();
    }

    public boolean isPermissionGranted(Context context, String permission) {
        boolean granted = ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED));
        return granted;
    }

    /**
     * @param context     current Context
     * @param permissions String[] permission to ask
     * @return boolean true/false
     */
    public boolean isPermissionsGranted(Context context, String permissions[]) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        boolean granted = true;

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED))
                granted = false;
        }

        return granted;
    }


//    private void internalRequestPermission(String[] permissionAsk) {
//        String arrayPermissionNotGranted[];
//        ArrayList<String> permissionsNotGranted = new ArrayList<>();
//
//        for (int i = 0; i < permissionAsk.length; i++) {
//            if (!isPermissionGranted(BaseActionbarActivity.this, permissionAsk[i])) {
//                permissionsNotGranted.add(permissionAsk[i]);
//            }
//        }
//
//
//        if (permissionsNotGranted.isEmpty()) {
//
//            if (permissionResult != null)
//                permissionResult.permissionGranted();
//
//        } else {
//
//            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
//            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
//            ActivityCompat.requestPermissions(BaseActionbarActivity.this, arrayPermissionNotGranted, KEY_PERMISSION);
//
//        }
//
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        if (requestCode != KEY_PERMISSION) {
//            return;
//        }
//
//        List<String> permissionDenied = new LinkedList<>();
//        boolean granted = true;
//
//        for (int i = 0; i < grantResults.length; i++) {
//
//            if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
//                granted = false;
//                permissionDenied.add(permissions[i]);
//            }
//
//        }
//
//        if (permissionResult != null) {
//            if (granted) {
//                permissionResult.permissionGranted();
//            } else {
//                for (String s : permissionDenied) {
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
//                        permissionResult.permissionForeverDenied();
//                        return;
//                    }
//                }
//
//                permissionResult.permissionDenied();
//
//
//            }
//        }
//
//    }
//
//    /**
//     * @param permission       String permission ask
//     * @param permissionResult callback PermissionResult
//     */
//    public void askCompactPermission(String permission, PermissionResult permissionResult) {
//        permissionsAsk = new String[]{permission};
//        this.permissionResult = permissionResult;
//        internalRequestPermission(permissionsAsk);
//
//    }
//
//    /**
//     * @param permissions      String[] permissions ask
//     * @param permissionResult callback PermissionResult
//     */
//    public void askCompactPermissions(String permissions[], PermissionResult permissionResult) {
//        permissionsAsk = permissions;
//        this.permissionResult = permissionResult;
//        internalRequestPermission(permissionsAsk);
//
//    }
//
//    public void openSettingsApp(Context context) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:" + context.getPackageName()));
//            startActivity(intent);
//        }
//
//
//    }


}
