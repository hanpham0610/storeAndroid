package com.example.store.Controller.SanPham;

import android.os.Bundle;
import com.journeyapps.barcodescanner.CaptureActivity;

public class CustomScannerActivity extends CaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is optional but makes sure the activity is locked in portrait mode.
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}