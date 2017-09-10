package com.soul0914.daumpopupdictionary;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class CheckClipboardManagerService extends Service {

    private final String LOG_TAG = "ClipboardManagerService";

    private ClipboardManager clipBoard;
    private ClipboardManager.OnPrimaryClipChangedListener primaryClipChangedListener;

    public CheckClipboardManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "onCreate");

        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        primaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                CharSequence pasteData = "";
                ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);
                pasteData = item.getText();
                Log.d(LOG_TAG, "onPrimaryClipChanged : " + pasteData);

                Intent intent = new Intent(getApplicationContext(), BubbleService.class);
                intent.putExtra("search_text", pasteData);
                startService(intent);
            }
        };

        clipBoard.addPrimaryClipChangedListener( primaryClipChangedListener );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(LOG_TAG, "onDestroy");

        clipBoard.removePrimaryClipChangedListener( primaryClipChangedListener );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }
}
