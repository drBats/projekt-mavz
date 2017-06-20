package com.example.jan.gps_koordinate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.imgproc.Imgproc;

public class DefibrilatorActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defibrilator);
        setTitle("Defibrilator");
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
