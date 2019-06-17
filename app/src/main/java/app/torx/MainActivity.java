package app.torx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mBtnLayout;
    private ImageView mBtnForeground;
    private ImageView mBtnBackground;
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean toggleState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLayout = findViewById(R.id.btnLayout);
        mBtnBackground = findViewById(R.id.btnBackground);
        mBtnForeground = findViewById(R.id.btnForeground);

        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            showNoFlashError();
        }

        //getting the camera manager and camera id
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        mBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlashlight();
            }
        });
    }

    private void showNoFlashError() {

    }

    private void toggleFlashlight() {
        if (toggleState == false) {
            mBtnBackground.setImageResource(R.drawable.btn_toggle_background);
            mBtnForeground.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorBtnDisabled));
            switchFlashLight(true);
            toggleState = true;
        } else {
            mBtnBackground.setImageResource(R.drawable.btn_toggle_background_disabled);
            mBtnForeground.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorBtnEnabled));
            switchFlashLight(false);
            toggleState = false;
        }
    }

    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
