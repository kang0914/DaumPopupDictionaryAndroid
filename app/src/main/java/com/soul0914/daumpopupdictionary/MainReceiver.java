package com.soul0914.daumpopupdictionary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MainReceiver extends BroadcastReceiver {

    public MainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // 부팅 완료 후
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, CheckClipboardManagerService.class);
            context.startService(i);
        }
    }
}
