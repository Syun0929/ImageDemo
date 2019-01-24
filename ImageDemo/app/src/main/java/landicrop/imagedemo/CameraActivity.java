package landicrop.imagedemo;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity  {

    private static final String TAG = "rustApp";
    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;
    private Camera mCamera;
//        private CameraPreview mPreview;
    private CameraPreview2 mPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        safeCameraOpen();
        new InitCameraThread().start();


//        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
//        mCamera.setDisplayOrientation(90);

    }

    @Override
    protected void onResume() {
        if (null == mCamera) {
            if (safeCameraOpen()) {
                mPreview.setCamera(mCamera); // 重新获取camera操作权

            } else {
                Log.e(TAG, "无法操作camera");
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private boolean safeCameraOpen() {
        boolean qOpened = false;
        try {
            releaseCamera();
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(TAG, "failed to open Camera");
            e.printStackTrace();
        }
        return qOpened;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }



    private class InitCameraThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (safeCameraOpen()) {
                Log.d(TAG, "开启摄像头");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPreview = new CameraPreview2(CameraActivity.this, mCamera);
                        cameraPreview.addView(mPreview);
                    }
                });
            }
        }
    }
}
