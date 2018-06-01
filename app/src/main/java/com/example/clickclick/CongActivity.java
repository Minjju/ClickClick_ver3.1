package com.example.clickclick;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CongActivity extends AppCompatActivity {

    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong);

        Intent getTime = getIntent();

        tvTime = (TextView)findViewById(R.id.tvTime);
        tvTime.setText(getTime.getExtras().getString("time"));
    }

    public void restart(View view) {
        Intent intent = new Intent(CongActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
