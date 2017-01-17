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
import java.util.HashMap;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Resources res = getApplicationContext().getResources();
        XmlResourceParser xpp = res.getXml(R.xml.vibrations);
        HashMap<String, String> elementList = new HashMap<String, String>();
        String currentTag = new String();
        String currentValue = new String();
        String currentId = new String();
        String currentAction = new String();
        boolean newId = false;
        boolean newAction = false;

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
                Log.d(TAG,"Importing vibration preferences");
            }
            else if(eventType == XmlPullParser.START_TAG)  {
                currentTag = xpp.getName();
                if (currentTag.equals("id") && !newId) {
                    newId = true;
                }
                if (currentTag.equals("action") && !newAction) {
                    newAction = true;
                }
            }
            else if(eventType == XmlPullParser.END_TAG) {
                currentTag = xpp.getName();
                if (currentTag.equals("element") && newId && newAction) {
                    Log.d(TAG,"Id: " + currentId + " Action: " + currentAction);
                    elementList.put(currentId, currentAction);
                    newId = false;
                    newAction = false;
                }
            }
            else if(eventType == XmlPullParser.TEXT) {
                currentValue = xpp.getText();
                if (currentTag.equals("id") && newId) {
                    currentId = currentValue;
                }
                if (currentTag.equals("action") && newAction) {
                    currentAction = currentValue;
                }
            }
            try {
                eventType = xpp.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//eof-while
        Log.d(TAG,"Successfully imported vibration preferences");
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
