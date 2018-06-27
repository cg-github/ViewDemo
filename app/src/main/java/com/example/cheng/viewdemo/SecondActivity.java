package com.example.cheng.viewdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by cheng on 2018/6/28.
 */

public class SecondActivity extends AppCompatActivity {
    public final static String TAG = SecondActivity.class.getSimpleName();

    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });
        Log.i(TAG,"cheng: "+"onStart " + Thread.currentThread());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"cheng: "+"onStart " + Thread.currentThread());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"cheng: "+"onResume " + Thread.currentThread());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"cheng: "+"onPause " + Thread.currentThread());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"cheng: "+"onStop " + Thread.currentThread());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"cheng: "+"onDestroy " + Thread.currentThread());
    }
}
