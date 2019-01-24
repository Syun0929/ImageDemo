package landicrop.imagedemo;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

public class CameraPreview2 extends TextureView implements TextureView.SurfaceTextureListener {

    private static final String TAG = "rustApp";
    private Camera mCamera;
    private Context context;

    public CameraPreview2(Context context) {
        super(context);
    }

    public CameraPreview2(Context context, Camera camera) {
        super(context);
        this.context = context;
        this.mCamera = camera;
        Log.d(TAG,"init Camera");
        this.setSurfaceTextureListener(this);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;

    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "TextureView onSurfaceTextureAvailable");
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(90);
        } else {
            mCamera.setDisplayOrientation(0);
        }
        try {
            mCamera.setPreviewCallback(mCameraPreviewCallback);
            mCamera.setPreviewTexture(surface); // 使用SurfaceTexture
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "TextureView onSurfaceTextureSizeChanged"); // Ignored, Camera does all the work for us
//        int displayOrientation = getDisplayOrientation();
//        mCamera.setDisplayOrientation(displayOrientation);
        List<Camera.Size> supportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Camera.Size optimalPreviewSize = getOptimalPreviewSize(supportedPreviewSizes, width, height);
        mCamera.getParameters().setPictureSize(optimalPreviewSize.width, optimalPreviewSize.height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d(TAG, "TextureView onSurfaceTextureDestroyed");
        surface.release();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
    }

    private Camera.PreviewCallback mCameraPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Log.d(TAG, "onPreviewFrame: data.length=" + data.length);
        }
    };

    //获取最佳的分辨率 而且是16：9的
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.75;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    //这里是一小段算法算出摄像头转多少都和屏幕方向一致
//    private int getDisplayOrientation() {
//        WindowManager windowManager = getWindowManager();
//        Display defaultDisplay = windowManager.getDefaultDisplay();
//        int orientation = defaultDisplay.getOrientation();
//        int degress = 0;
//        switch (orientation) {
//            case Surface.ROTATION_0:
//                degress = 0;
//                break;
//            case Surface.ROTATION_90:
//                degress = 90;
//                break;
//            case Surface.ROTATION_180:
//                degress = 180;
//                break;
//            case Surface.ROTATION_270:
//                degress = 270;
//                break;
//        }
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        android.hardware.Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, cameraInfo);
//        int result = (cameraInfo.orientation - degress + 360) % 360;
//        return result;
//    }
}
