package com.soul0914.daumpopupdictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    static final int REQUEST_EXPLAIN_DRAWING = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up a listener whenever a key changes
        PreferenceManager.getDefaultSharedPreferences (this).registerOnSharedPreferenceChangeListener(this);

        //프로그램 시작시 설정에 따라서 서비스 시작
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (this);
        Boolean bTapToSearch = prefs.getBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, true);
        if(bTapToSearch) {
            checkDrawPermission();
        }

        // 설정 프래그먼트 지정
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentBorC, new MainPreferenceFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        PreferenceManager.getDefaultSharedPreferences (this).registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        // Set up a listener whenever a key changes
        PreferenceManager.getDefaultSharedPreferences (this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("settings", "preference changed: " + key);

        if(key.equals(MainPreferenceFragment.PREF_TAP_TO_SEARCH)) {
            boolean bTapToSearch = sharedPreferences.getBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, true);

            if(bTapToSearch) {
                // 권한 체크
                checkDrawPermission();
            }
            else {
                stopCheckClipboardManagerService();
            }
        }
    }

    private void startCheckClipboardManagerService(){
        startService(new Intent(this, CheckClipboardManagerService.class));
    }

    private void stopCheckClipboardManagerService() {
        stopService(new Intent(this, CheckClipboardManagerService.class));
    }

    public void checkDrawPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, 123);

                Intent intent = new Intent(this, ExplainDrawingActivity.class);
                startActivityForResult(intent, REQUEST_EXPLAIN_DRAWING);
            } else {
                // display over lay from service
                startCheckClipboardManagerService();
            }
        }else
        {
            // display over lay from service
            startCheckClipboardManagerService();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_EXPLAIN_DRAWING) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, false);
                    editor.commit();

                    // 설정 프래그먼트 다시 로드
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragmentBorC, new MainPreferenceFragment())
                            .commit();

                }else
                {
                    // empty
                }
            }
        }
//        if (requestCode == 123) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (Settings.canDrawOverlays(this)) {
//                    // You have permission
//                    // display over lay from service
//                    startCheckClipboardManagerService();
//                }
//                else {
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (this);
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, false);
//                    editor.commit();
//
//                    // 설정 프래그먼트 지정
//                    getFragmentManager().beginTransaction()
//                            .replace(R.id.fragmentBorC, new MainPreferenceFragment())
//                            .commit();
//                }
//            }
//        }
    }

}
