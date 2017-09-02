package com.example.jaewon.clipboardmanagertest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 설정 프래그먼트 지정
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentBorC, new MainPreferenceFragment())
                .commit();

        //프로그램 시작시 설정에 따라서 서비스 시작
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (this);
        Boolean bTapToSearch = prefs.getBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, true);
        if(bTapToSearch)
            startService(new Intent(this, CheckClipboardManagerService.class));
        else
            stopService(new Intent(this, CheckClipboardManagerService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
