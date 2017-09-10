package com.soul0914.daumpopupdictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ExplainDrawingActivity extends AppCompatActivity {

    static final int REQUEST_PERMIT_DRAWING = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_explain_drawing);
    }

    public void buttonShowPermitDrawing_onClick(View view){
        ShowPermitDrawing();
    }

    public void textViewLater_onClick(View view) {
        finish();
    }

    private  void ShowPermitDrawing()
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_PERMIT_DRAWING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_PERMIT_DRAWING) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, false);
                    editor.commit();
                }else
                {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, true);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "다음 팝업 서비스를 시작합니다.", Toast.LENGTH_SHORT).show();

                    startService(new Intent(this, CheckClipboardManagerService.class));
                }
            }
            finish();
        }
    }
}
