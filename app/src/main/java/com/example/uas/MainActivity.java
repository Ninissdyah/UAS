package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView mImgView;
    Bitmap mBitmap;
    Canvas mCanvas;
    private int mColorBackground;
    Paint mCirclePaint = new Paint();
    Paint mHeadPaint = new Paint();
    Paint mEyeConnectorPaint = new Paint();

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.my_img_view);

        mCirclePaint.setColor(getResources().getColor(R.color.black));
        mHeadPaint.setColor(getResources().getColor(R.color.white));
        mEyeConnectorPaint.setColor(getResources().getColor(R.color.black));
        layout = findViewById(R.id.layout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        mImgView.setImageBitmap(mBitmap);
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mColorBackground);
        mEyeConnectorPaint.setStrokeWidth(15f);

//        drawHead();
//        drawRightEye();
//        drawLeftEye();
//        drawEyeConnector();
//
//
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateBaymax(view);
                mColorBackground = ResourcesCompat.getColor(getResources(), R.color.yellow, null);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(mColorBackground);
                drawHead();
                drawRightEye();
                drawLeftEye();
                drawEyeConnector();
            }
        });
    }

    private void drawHead() {
        int vWidth = mImgView.getWidth();
        int vHeight = mImgView.getHeight();

        mCanvas.save(); // Menyimpan kondisi sebelum melakukan transformasi
        //mCanvas.rotate(90, 255, 255); // Memutar canvas sebesar 90 derajat pada titik (200, 200)
        mCanvas.scale(2, 1); // Mengubah skala pada sumbu X
        //ingat ini di balik 90 derajat
        //mCanvas.drawOval(240, -500, 550, 400, mHeadPaint);
        mCanvas.drawOval((vWidth/2-100), (vHeight/2-500), (vWidth/10), (vHeight/2+100), mHeadPaint);
        mCanvas.restore(); // Mengembalikan kondisi sebelum transformasi
    }

    private void drawRightEye() {
        int vWidth = mImgView.getWidth();
        mCanvas.drawCircle(vWidth/2+150, 500, 45, mCirclePaint);
    }

    private void drawLeftEye() {
        int vWidth = mImgView.getWidth();
        mCanvas.drawCircle(vWidth/2-150, 500, 45, mCirclePaint);
    }

    private void drawEyeConnector() {
        int vWidth = mImgView.getWidth();
        mCanvas.drawLine(vWidth/2-100, 500, vWidth/2+100,500, mEyeConnectorPaint);
    }

    public void animateBaymax(View view){
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImgView, "alpha", 0f, 1f);
        alphaAnimator.setDuration(1000);

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mImgView, "rotationY", 180);
        rotationAnimator.setDuration(1000);

        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(mImgView, "alpha", 1f, 0f);
        alphaAnimator2.setDuration(1000);
        alphaAnimator2.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaAnimator, rotationAnimator, alphaAnimator2);
        animatorSet.start();
    }
}