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

        // by jw.kang
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
