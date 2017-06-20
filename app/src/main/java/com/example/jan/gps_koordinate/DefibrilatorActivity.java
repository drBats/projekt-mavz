package com.example.jan.gps_koordinate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import org.opencv.core.Mat;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.imgproc.Imgproc;

public class DefibrilatorActivity extends AppCompatActivity {

    private ImageView levi,desni;
    private int xDelta;
    private int yDelta;

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
                        //android.support.v7.widget.LinearLayoutCompat.LayoutParams lParams = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                        //android.support.v7.widget.LinearLayoutCompat.LayoutParams layoutParams = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) view.getLayoutParams();
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


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            if (status == LoaderCallbackInterface.SUCCESS ) {
                // now we can call opencv code !
                funkcija();
            } else {
                super.onManagerConnected(status);
            }
        }
    };

    private void funkcija(){
        Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.body);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageBitmap(bMap);
    }
}
