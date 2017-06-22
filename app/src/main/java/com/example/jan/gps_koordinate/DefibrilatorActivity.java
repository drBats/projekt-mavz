package com.example.jan.gps_koordinate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioRouting;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.OpenCVLoader;

public class DefibrilatorActivity extends AppCompatActivity {

    private ImageView levi,desni;
    private int xDelta;
    private int yDelta;
    private static final String TAG = "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defibrilator);
        setTitle("Defibrilator");
        levi=(ImageView)findViewById(R.id.levi);
        desni=(ImageView)findViewById(R.id.desni);

        levi.setOnTouchListener(onTouchListener());
        desni.setOnTouchListener(onTouchListener());
        }


    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                return true;
            }
        };
    }

    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                }
                break;
                default:
                {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume");
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            funkcija();
        }
    }

    private void funkcija(){
        Mat imageMat = new Mat();
        Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.body);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        Utils.bitmapToMat(bMap,imageMat);
        Imgproc.Laplacian(imageMat, imageMat, CvType.CV_8U, 3, 1, 0); //Laplaceov detektor robov za body
        Utils.matToBitmap(imageMat,bMap);
        image.setImageBitmap(bMap);

        imageMat = new Mat();
        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.leva);
        image = (ImageView) findViewById(R.id.levi);
        Utils.bitmapToMat(bMap,imageMat);
        Imgproc.cvtColor(imageMat,imageMat,Imgproc.COLOR_RGB2GRAY,1); //pretvorimo v črno belo
        Imgproc.GaussianBlur(imageMat,imageMat,new Size(3,3),1); //leva elektroda gauss
        Utils.matToBitmap(imageMat,bMap);
        image.setImageBitmap(bMap);

        imageMat = new Mat();
        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.desna);
        image = (ImageView) findViewById(R.id.desni);
        Utils.bitmapToMat(bMap,imageMat);
        Imgproc.cvtColor(imageMat,imageMat,Imgproc.COLOR_RGB2GRAY,1); //pretvorimo v črno belo
        Imgproc.GaussianBlur(imageMat,imageMat,new Size(3,3),1); //desna elektroda gauss
        Utils.matToBitmap(imageMat,bMap);
        image.setImageBitmap(bMap);
    }
}
