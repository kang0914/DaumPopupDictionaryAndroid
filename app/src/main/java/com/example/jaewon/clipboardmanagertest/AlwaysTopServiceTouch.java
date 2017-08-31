package com.example.jaewon.clipboardmanagertest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class AlwaysTopServiceTouch extends Service {
    private View mView;
    private WindowManager mManager;
    private WindowManager.LayoutParams mParams;

    private float mTouchX, mTouchY;
    private int mViewX, mViewY;

    private boolean isMove = false;

    // by jw.kang
    private final String LOG_TAG = "AlwaysTopServiceTouch";

    private  String searchText;

    private TimerTask mTask;
    private Timer mTimer;

    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isMove = false;

                    mTouchX = event.getRawX();
                    mTouchY = event.getRawY();
                    mViewX = mParams.x;
                    mViewY = mParams.y;

                    break;

                case MotionEvent.ACTION_UP:
                    if (!isMove) {
//                        Toast.makeText(getApplicationContext(), "ЕНДЎµК",
//                                Toast.LENGTH_SHORT).show();

                        // by jw.kang
//                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.dic.daum.net/search.do?q=" +
//                                encodeURIComponent(searchText) +
//                                "&dic=all"));
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);

                        //FLAG_ACTIVITY_NEW_TASK
//                        Intent intent = new Intent(getApplicationContext(), SearchResultService.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startService(intent);

                        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("search_text", searchText);
                        startActivity(intent);

                        stopSelf();
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    isMove = true;

                    int x = (int) (event.getRawX() - mTouchX);
                    int y = (int) (event.getRawY() - mTouchY);

                    final int num = 5;
                    if ((x > -num && x < num) && (y > -num && y < num)) {
                        isMove = false;
                        break;
                    }

                    /**
                     * mParams.gravityїЎ µыёҐ єОИЈ єЇ°ж
                     *
                     * LEFT : x°Ў +
                     *
                     * RIGHT : x°Ў -
                     *
                     * TOP : y°Ў +
                     *
                     * BOTTOM : y°Ў -
                     */
                    mParams.x = mViewX + x;
                    mParams.y = mViewY + y;

                    mManager.updateViewLayout(mView, mParams);

                    break;
            }

            return true;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(LOG_TAG, "onCreate");

        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.always_on_top_view_touch, null);

        mView.setOnTouchListener(mViewTouchListener);

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.TOP | Gravity.RIGHT;

        // by jw.kang
        //mParams.windowAnimations = android.R.style.Animation_Toast;
        mParams.windowAnimations = R.style.PauseDialogAnimation;

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(mView, mParams);

        // by jw.kang
        mTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "TimerTask run()");
                stopSelf();
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 5700);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // by jw.kang
        searchText = intent.getExtras().getString("search_text");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(LOG_TAG, "onDestroy");

        if (mView != null) {
            mManager.removeView(mView);
            mView = null;

            mTimer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public String encodeURIComponent(String s)
    {
        String result = null;

        try
        {
            result = java.net.URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (	java.io.UnsupportedEncodingException e)
        {
            result = s;
        }

        return result;
    }
}
