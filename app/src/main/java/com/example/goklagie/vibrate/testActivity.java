package com.example.goklagie.vibrate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Get instance of Vibrator from current Context
        //Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

        // Start without a delay
        // Vibrate for 100 milliseconds
        // Sleep for 1000 milliseconds
        //long[] pattern = {0, 100, 1000};

        // The '0' here means to repeat indefinitely
        // '0' is actually the index at which the pattern keeps repeating from (the start)
        // To repeat the pattern from any other point, you could increase the index, e.g. '1'
        //v.vibrate(pattern, 0);
    }
    private static final String TAG = testActivity.class.getSimpleName();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(TAG, "ssssssssssssssssssssssss:touch");
        //Log.d(TAG,Float.toString(event.getX()));
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000};
        //v.vibrate(pattern, 1);
        v.vibrate((long) ((event.getX()+event.getY())/2));
        return super.onTouchEvent(event);
    }


}
