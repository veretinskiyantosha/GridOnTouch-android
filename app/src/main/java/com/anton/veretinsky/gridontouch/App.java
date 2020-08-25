package com.anton.veretinsky.gridontouch;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;

public class App extends Application {
    private WindowManager windowManager;
    private GridView gridView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    public void toggleShowGrid() {
        if (gridView != null) {
            removeGrid();
        } else {
            showGrid();
        }
    }

    private void showGrid() {
        if (gridView != null) {
            return;
        }

        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        int layoutType = WindowManager.LayoutParams.TYPE_PHONE;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            layoutType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }

        gridView = new GridView(getApplicationContext());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                screenWidth,
                screenHeight,
                layoutType,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT
        );
        windowManager.addView(gridView, layoutParams);
    }

    private void removeGrid() {
        if (gridView != null) {
            windowManager.removeView(gridView);
            gridView = null;
        }
    }
}
