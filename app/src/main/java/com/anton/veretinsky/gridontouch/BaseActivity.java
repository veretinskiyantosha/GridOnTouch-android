package com.anton.veretinsky.gridontouch;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_DRAW_OVERLAY = 1234;

    private boolean canDrawOverlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
            );
            startActivityForResult(intent, REQUEST_PERMISSION_DRAW_OVERLAY);
        } else {
            canDrawOverlay = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PERMISSION_DRAW_OVERLAY) {
            canDrawOverlay = Settings.canDrawOverlays(this);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        int pointerCount = ev.getPointerCount();
        if (canDrawOverlay &&
                pointerCount == 3 &&
                action == MotionEvent.ACTION_POINTER_UP) {
            ((App) getApplication()).toggleShowGrid();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
