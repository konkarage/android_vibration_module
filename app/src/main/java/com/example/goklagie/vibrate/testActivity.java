package com.example.goklagie.vibrate;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        InputStream ins = getResources().openRawResource(getResources().getIdentifier("vibration",
                        "raw", getPackageName()));
        //Context context = getApplicationContext();
        //InputStream istream = context.getResources().openRawResource(R.raw.vibrate);
//        //File inputFile = new File("app/src/main/res/xml/vibration.xml");
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(ins);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //doc.getDocumentElement().normalize();
        Log.d("asda","Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("element");
        Log.d("asda","Root element :" + nList.getLength());
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :"
                    + nNode.getNodeName());
            Element eElement = (Element) nNode;
            System.out.println("Student roll no : "
                    + eElement.getElementsByTagName("action").item(0).getTextContent());
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
    }
    private static final String TAG = testActivity.class.getSimpleName();

    private void getKids(ViewGroup group, Float x, Float y) {
        final int childcount = group.getChildCount();
        for (int i = 0; i < childcount; i++) {
            if (ViewGroup.class.isInstance(group.getChildAt(i))) {
                getKids( (ViewGroup) group.getChildAt(i), x, y);
            } else {
                View v = group.getChildAt(i);
                int[] temp = new int[2];
                v.getLocationOnScreen(temp);
                Float x1 = (float) temp[0];
                Float y1 = (float) temp[1];
                Float width = (float) v.getWidth();
                Float height = (float) v.getHeight();

                if (x>x1 & x<x1+width & y>y1 & y<y1+height){
                    //return v.getId();
                    Log.d(TAG, getResources().getResourceEntryName(v.getId()));
                }
                //Log.d(TAG, getResources().getResourceEntryName(v.getId()));
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int eventaction=event.getAction();
        switch(eventaction) {
            case MotionEvent.ACTION_DOWN:
                ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
                //final int childcount = root.getChildCount();
                getKids(root, event.getX(), event.getY());
                ViewGroup temp = (ViewGroup) root.getChildAt(0);
                final int childcount = temp.getChildCount();

                //for (int i = 0; i < childcount; i++) {
                //    View v = temp.getChildAt(i);
                //    Log.d(TAG,getResources().getResourceEntryName(v.getId()));
                //}
                //Log.d(TAG,Integer.toString(root.getId()));
                //Log.d(TAG, "ssssssssssssssssssssssss:touch");
                //Log.d(TAG,Float.toString(event.getX()));
                Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
                long[] pattern = {0, 100, 1000};
                //v.vibrate(pattern, 1);
                v.vibrate((long) ((event.getX()+event.getY())/2));
                return true;
        }

        return super.onTouchEvent(event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int eventaction=event.getAction();
//        switch(eventaction) {
//            case MotionEvent.ACTION_DOWN:
//                ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//                Log.d(TAG,Integer.toString(root.getId()));
//        }
//        //Log.d(TAG, "ssssssssssssssssssssssss:touch");
//        //Log.d(TAG,Float.toString(event.getX()));
//        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
//        long[] pattern = {0, 100, 1000};
//        //v.vibrate(pattern, 1);
//        v.vibrate((long) ((event.getX()+event.getY())/2));
//        return super.onTouchEvent(event);
//    }


}
