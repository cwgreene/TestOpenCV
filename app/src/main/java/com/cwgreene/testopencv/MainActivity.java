package com.cwgreene.testopencv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    public static final String TAG = "DOOM";
    // Used to load the 'native-lib' library on application startup.
    static {
        Log.i(TAG, "Beginning initialization of OpenCV");
        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "Oh noes! This did not work. Sad!");
            // Handle initialization error
        } else {
            Log.i(TAG, "Prepped and Ready!");
        }

        System.loadLibrary("native-lib");
    }
    private CameraBridgeViewBase mOpenCvCameraView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.enableView();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    public void onCameraViewStarted(int width, int height) {
        Log.i(TAG, "onCameraViewStarted!");
    }
    public void onCameraViewStopped() {
        Log.i(TAG, "onCameraViewStopped!");
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat m = inputFrame.rgba();
        double[] x = {255,0,0,0};
        m.put(10, 10, x);
        ORB orb = ORB.create();
        MatOfKeyPoint mkp = new MatOfKeyPoint();
        orb.detect(m, mkp);
        Features2d.drawKeypoints(m, mkp, m, new Scalar(255,0,0,0), Features2d.DRAW_OVER_OUTIMG);
        return m;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
