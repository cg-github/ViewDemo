package com.example.cheng.viewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent1 = new Intent(this,SecondActivity.class);
        Intent intent2 = new Intent(this,ThirdActivity.class);
        startActivityForResult(intent1,0);
        startActivityForResult(intent2,0);
        Log.i(TAG,"cheng: "+"onCreate " + Thread.currentThread());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 2:
                Log.i(TAG,"cheng: "+"onActivityResult from second " + Thread.currentThread());
                break;
            case 3:
                Log.i(TAG,"cheng: "+"onActivityResult from third " + Thread.currentThread());
                break;
        }
        Log.i(TAG,"cheng: "+"onActivityResult " + Thread.currentThread());
    }
}
