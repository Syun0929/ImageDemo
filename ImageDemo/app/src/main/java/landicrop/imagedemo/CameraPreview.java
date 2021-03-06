package landicrop.imagedemo;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "rustApp";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mFrameCount = 0;
    public CameraPreview(Context context) {
        super(context);
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

    }

    public void setCamera(Camera c) {
        this.mCamera = c;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    // 开启预览
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 若需要旋转、更改大小或重新设置，请确保证已停止预览
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        Camera.Parameters parameters = mCamera.getParameters();
        // ImageFormat.NV21 == 17
        Log.d(TAG, "parameters.getPreviewFormat(): " + parameters.getPreviewFormat());
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(90);
        } else {
            mCamera.setDisplayOrientation(0);
        }
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(mCameraPreviewCallback); // 回调要放在 startPreview() 之前
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    private Camera.PreviewCallback mCameraPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            mFrameCount++;
            Log.d(TAG, "onPreviewFrame: data.length=" + data.length + ", frameCount=" + mFrameCount);
        }
    };
}
