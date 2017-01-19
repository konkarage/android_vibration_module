package com.example.goklagie.vibrate;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public class testActivity extends AppCompatActivity {

    private HashMap<String, String> elementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Resources res = getApplicationContext().getResources();
        XmlResourceParser xpp = res.getXml(R.xml.vibrations);
        elementList = new HashMap<String, String>();
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

    }
    private static final String TAG = testActivity.class.getSimpleName();

    private String getKids(ViewGroup group, Float x, Float y) {
        final int childcount = group.getChildCount();
        for (int i = 0; i < childcount; i++) {
            if (ViewGroup.class.isInstance(group.getChildAt(i))) {
                return getKids( (ViewGroup) group.getChildAt(i), x, y);
            } else {
                View v = group.getChildAt(i);
                int[] temp = new int[2];
                v.getLocationOnScreen(temp);
                Float x1 = (float) temp[0];
                Float y1 = (float) temp[1];
                Float width = (float) v.getWidth();
                Float height = (float) v.getHeight();

                if (x>x1 & x<x1+width & y>y1 & y<y1+height){
                    return getResources().getResourceEntryName(v.getId());
                }
            }
        }
        return "1";
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventaction=event.getAction();
        switch(eventaction) {
            case MotionEvent.ACTION_DOWN:
                ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                String action = getKids(root, event.getX(), event.getY());
                Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);

                Float temp = Float.valueOf(elementList.get(action));
                float temp2 = (float) temp;
                v.vibrate((long) temp2);
                return true;
        }

        return super.onTouchEvent(event);
    }

}
