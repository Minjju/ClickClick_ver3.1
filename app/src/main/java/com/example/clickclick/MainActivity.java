package com.example.clickclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout Full, layout01, heartView;
    ImageView start, heart01,heart02,heart03;
    ImageView[] img = new ImageView[12];
    int[] num = new int[12];

    Random random = new Random();
    int count = 0;
    int game = 1;
    int hea=3;
    int score=0;

    TextView mEllapse;

    final static int START = 0;
    final static int STOP = 1;
    int mStatus = START;
    long mBaseTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Full = (LinearLayout) findViewById(R.id.Full);
        layout01 = (LinearLayout) findViewById(R.id.layout01);
        heartView = (LinearLayout)findViewById(R.id.heartView);

        heart01 =(ImageView)findViewById(R.id.heart01);
        heart02 =(ImageView)findViewById(R.id.heart02);
        heart03 =(ImageView)findViewById(R.id.heart03);

        mEllapse = (TextView)findViewById(R.id.ellapse);

        start = (ImageView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setImageResource(R.drawable.ing);
                layout01.setVisibility(View.VISIBLE);
                heartView.setVisibility(View.VISIBLE);

                if(mStatus == START) {
                    start();
                }

            }
        });

        for (int i = 0; i < img.length; i++) {
            img[i] = (ImageView) findViewById(R.id.img01 + i);
            img[i].setOnClickListener(this);
        }
            setting();

    }

    private void setting() {

        for (int i = 0; i < num.length; i++) {
            num[i] = random.nextInt(12);

            for (int j = 0; j < i; j++) {
                if (num[i] == num[j]) {
                    i--;
                    break;
                }
            } // 중복시 i감소

            switch (game){
                case 1:
                    img[i].setImageResource(R.drawable.num01 + num[i]);
                    break;
                case 2:
                    img[i].setImageResource(R.drawable.alpa01 + num[i]);
                    break;
                case 3:
                    img[i].setImageResource(R.drawable.cha01 + num[i]);
                    break;
            }
            img[i].setTag(num[i]);
        }
    }

    @Override
    public void onClick(View v) {

        if (count == Integer.parseInt(v.getTag().toString())) {
            v.setVisibility(View.INVISIBLE);
            count++;
            score++;

            if (count == 12) {
                game++;

                if(game==4){
                    Intent intent = new Intent(MainActivity.this, CongActivity.class);
                    stop();
                    intent.putExtra("time", mEllapse.getText().toString());
                    startActivity(intent);
                    finish();

                } else {
                    reset();
                    setting();
                }
            }
        } else {
            switch (hea){
                case 3:
                    heart01.setVisibility(View.INVISIBLE);
                    break;

                case 2:
                    heart02.setVisibility(View.INVISIBLE);
                    break;

                case 1:
                    heart03.setVisibility(View.INVISIBLE);
                    break;

                case 0:
                    Intent intent = new Intent(MainActivity.this, EndActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            hea--;
        }
    }

    public void reset() {
        for (int i = 0; i < img.length; i++) {
            count = 0;
            img[i].setVisibility(View.VISIBLE);
        }
    }

    Handler mTimer = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            mEllapse.setText(getEllapse());

            mTimer.sendEmptyMessage(0);

            System.out.println(getEllapse());

        }
    };

    protected void onDestroy() {

        mTimer.removeMessages(0);
        super.onDestroy();
    }

    public void start() {
        mBaseTime = SystemClock.elapsedRealtime();
        mTimer.sendEmptyMessage(0);
        mStatus = STOP;
    }

    public void stop() {

        mTimer.removeMessages(0);
        mEllapse.setText(getEllapse());
        mStatus = START;


    }

    String getEllapse(){

        long now = SystemClock.elapsedRealtime();
        long ell = now - mBaseTime;//현재 시간과 지난 시간을 빼서 ell값을 구하고

        String sEll = String.format("%02d:%02d:%02d", ell / 1000 / 60, (ell/1000)%60, (ell %1000)/10);

        return sEll;
    }

}
