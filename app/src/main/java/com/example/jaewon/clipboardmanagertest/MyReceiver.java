package com.example.jaewon.clipboardmanagertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("MyReceiver", "onReceive");

            Intent i = new Intent(context, CheckClipboardManagerService.class);
            context.startService(i);
        }
    }
}
