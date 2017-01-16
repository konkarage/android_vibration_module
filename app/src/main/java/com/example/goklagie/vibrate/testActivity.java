package com.example.goklagie.vibrate;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        StringBuffer sb = new StringBuffer();
        Resources res = getApplicationContext().getResources();
        XmlResourceParser xpp = res.getXml(R.xml.vibrations);

        try {
            xpp.next();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                sb.append("******Start document");
            }
            else if(eventType == XmlPullParser.START_TAG)  {
                sb.append("\nStart tag " + xpp.getName());
            }
            else if(eventType == XmlPullParser.END_TAG) {
                sb.append("\nEnd tag " + xpp.getName());
            }
            else if(eventType == XmlPullParser.TEXT) {
                sb.append("\nText " + xpp.getText());
            }
            try {
                eventType = xpp.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//eof-while
        sb.append("\n******End document");

        Log.d(TAG, sb.toString());

//        XmlResourceParser parser = getResources().getXml(R.xml.vibrations);
//
//        try {
//            int f = 0;
//            while (parser.next() != XmlPullParser.END_DOCUMENT) {
//                Log.d(TAG, "d: " + f);
//f++;
//                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("element")) {
//                    String s;
//
//                    for (int i = 0; i < parser.getAttributeCount(); i++) {
//                        Log.d(TAG, "ind: " + i);
//                        if (parser.getAttributeName(i).equals("element"))
//                            Log.d(TAG,getResources().getString(parser.getAttributeResourceValue(i, -1)));
//                    }
//                }
//
//            }
        }


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
