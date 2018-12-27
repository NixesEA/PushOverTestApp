package com.nixesea.pushovertestapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRActivity extends AppCompatActivity implements Detector.Processor {

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        final Activity activity = this;


        surfaceView = this.findViewById(R.id.surfaceView);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        barcodeDetector.setProcessor(this);

        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1024, 720)
                .setAutoFocusEnabled(true)
                .setRequestedFps(25)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1024);
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException ie) {
                    ie.printStackTrace();
                    Log.e("Camera start problem", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() != 0) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < barcodes.size(); ++i) {
                sb.append(barcodes.valueAt(i).rawValue).append("\n");
            }

            Intent intent = new Intent();
            intent.putExtra("QRtext",sb.toString());
            Log.i("DataTag",sb.toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
