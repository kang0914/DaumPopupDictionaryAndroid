package com.soul0914.daumpopupdictionary;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;

public class MainReceiver extends BroadcastReceiver {

    static final int REQUEST_EXPLAIN_DRAWING = 100;

    public MainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // 부팅 완료 후
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (context);
            Boolean bTapToSearch = prefs.getBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, true);

            if(bTapToSearch) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(context)) {

                        // 재시작시 설정이 false로 바뀌어있음.
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(MainPreferenceFragment.PREF_TAP_TO_SEARCH, false);
                        editor.commit();

                        Intent i = new Intent(context, ExplainDrawingActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                        return;
                    }
                }
                Intent i = new Intent(context, CheckClipboardManagerService.class);
                context.startService(i);
            }
        }
    }
}
