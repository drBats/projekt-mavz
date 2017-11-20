package com.example.jan.gps_koordinate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioRouting;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    private TextView rez;
    private ImageView levi,desni,leviPravilni,desniPravilni;
    private int xDelta,yDelta,xLeviPad,yLeviPad,xDesniPad,yDesniPad;
    private int leviX=341;
    private int leviY=638;
    private int desniX=641;
    private int desniY=931;
    private static final String TAG = "MainActivity";
    private Button izracunaj;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defibrilator);
        setTitle("Defibrilator");
        levi=(ImageView)findViewById(R.id.levi);
        desni=(ImageView)findViewById(R.id.desni);
        leviPravilni=(ImageView)findViewById(R.id.leviPravilni);
        desniPravilni=(ImageView)findViewById(R.id.desniPravilni);

        rez=(TextView)findViewById(R.id.rezultat);

        levi.setOnTouchListener(onTouchListener());
        desni.setOnTouchListener(onTouchListener());
        izracunaj = (Button) findViewById(R.id.koncano);
        izracunaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                leviPravilni.setVisibility(View.INVISIBLE);
                desniPravilni.setVisibility(View.INVISIBLE);
                rez.setText("");
                xLeviPad=levi.getLeft();
                yLeviPad=levi.getTop();
                xDesniPad=desni.getLeft();
                yDesniPad=desni.getTop();

                double distance1 = Math.sqrt(Math.pow((leviX-xLeviPad), 2) + Math.pow((leviY-yLeviPad), 2));
                if(distance1<60)
                {

                    levi.setBackgroundColor(Color.GREEN);
                    rez.append("Leva elektroda: PRAVILNO\n");
                }
                else
                {
                    levi.setBackgroundColor(Color.RED);
                    leviPravilni.setVisibility(View.VISIBLE);
                    leviPravilni.setBackgroundColor(Color.GREEN);
                    leviPravilni.setColorFilter(Color.WHITE);
                    leviPravilni.setX(leviX);
                    leviPravilni.setY(leviY);
                    rez.append("Leva elektroda: NAPAČNO\n");
                }
                //rez.append("Leva elektroda oddaljena: "+Double.toString(distance1)+'\n');
                distance1 = Math.sqrt(Math.pow((desniX-xDesniPad), 2) + Math.pow((desniY-yDesniPad), 2));
                if(distance1<60)
                {

                    desni.setBackgroundColor(Color.GREEN);
                    rez.append("Desna elektroda: PRAVILNO\n");
                }
                else
                {
                    desni.setBackgroundColor(Color.RED);
                    desniPravilni.setVisibility(View.VISIBLE);
                    desniPravilni.setBackgroundColor(Color.GREEN);
                    desniPravilni.setColorFilter(Color.WHITE);
                    desniPravilni.setX(desniX);
                    desniPravilni.setY(desniY);
                    rez.append("Desna elektroda: NAPAČNO\n");
                }
                //rez.append("Desna elektroda oddaljena: "+Double.toString(distance1)+'\n');
            }
        });
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

        image.setImageBitmap(bMap);

        imageMat = new Mat();
        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.leva);
        image = (ImageView) findViewById(R.id.levi);
        Utils.bitmapToMat(bMap,imageMat);

        image.setImageBitmap(bMap);

        imageMat = new Mat();
        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.desna);
        image = (ImageView) findViewById(R.id.desni);
        Utils.bitmapToMat(bMap,imageMat);
        image.setImageBitmap(bMap);
    }
}
