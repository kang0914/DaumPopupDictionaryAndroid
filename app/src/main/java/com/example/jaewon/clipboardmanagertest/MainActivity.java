package com.example.jaewon.clipboardmanagertest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    // by jw.kang
    private PackageReceiver mPackageReceiver = new PackageReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // by jw.kang
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentBorC, new BlankFragment())
                .commit();

        // by jw.kang
        this.registerReceiver(mPackageReceiver, new IntentFilter(Intent.ACTION_PACKAGE_ADDED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // by jw.kang
        unregisterReceiver( mPackageReceiver );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mStart(View v) {

        startService(new Intent(this, CheckClipboardManagerService.class));
    }

    public void mStop(View v) {

        stopService(new Intent(this, CheckClipboardManagerService.class));
    }

    public void showSetting(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
