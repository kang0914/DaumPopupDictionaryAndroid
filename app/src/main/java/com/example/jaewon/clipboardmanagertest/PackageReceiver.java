package com.example.jaewon.clipboardmanagertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {
    public PackageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getSchemeSpecificPart();
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            Log.d("ADDED",packageName);
        }
        else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            Log.d("REMOVED",packageName);
        }
    }
}
