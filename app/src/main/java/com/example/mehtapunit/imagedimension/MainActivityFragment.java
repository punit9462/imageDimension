package com.example.mehtapunit.imagedimension;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.logging.Logger;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private Button screenShotButton;
    private ImageView imageView;
    private int[] baseP1 =new int[2];
    private int[] baseP2 =new int[2];
    private int[] objP1 =new int[2];
    private int[] objP2 =new int[2];
    int numberOfClicks = 0;
    private double actualLengthOfBaseObject = 5.4; //cm

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        screenShotButton = (Button) view.findViewById(R.id.screen_shot_button);
        screenShotButton.setOnClickListener(screenShotButtonClickListener);
//        imageView.setOnTouchListener(imgSourceOnTouchListener);
        imageView.setOnClickListener(imgSourceOnClickListener);
        return view;
    }

    View.OnClickListener imgSourceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            numberOfClicks++;
            if(numberOfClicks == 1) {
                view.getLocationOnScreen(baseP1);
                Toast.makeText(getContext(),"clicked1", Toast.LENGTH_SHORT).show();
            }
            else if(numberOfClicks == 2) {
                view.getLocationOnScreen(baseP2);
                Toast.makeText(getContext(),"clicked2", Toast.LENGTH_SHORT).show();
            }
            else if(numberOfClicks == 3) {
                view.getLocationOnScreen(objP1);
                Toast.makeText(getContext(),"clicked3", Toast.LENGTH_SHORT).show();
            }
            else if(numberOfClicks == 4) {
                view.getLocationOnScreen(objP2);
                screenShotButton.setText("Calculate Dimensions");
                Toast.makeText(getContext(),"clicked4", Toast.LENGTH_SHORT).show();
            }
            else {}
        }
    };
//    View.OnTouchListener imgSourceOnTouchListener = new View.OnTouchListener(){
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
//            numberOfClicks++;
//            if(numberOfClicks == 1) {
//                baseP1[0] = event.getX();
//                baseP1[1] = event.getY();
//                Toast.makeText(getContext(),"clicked1", Toast.LENGTH_SHORT).show();
//            }
//            else if(numberOfClicks == 2) {
//                baseP2[0] = event.getX();
//                baseP2[1] = event.getY();
//                Toast.makeText(getContext(),"clicked2", Toast.LENGTH_SHORT).show();
//            }
//            else if(numberOfClicks == 3) {
//                objP1[0] = event.getX();
//                objP1[1] = event.getY();
//                Toast.makeText(getContext(),"clicked3", Toast.LENGTH_SHORT).show();
//            }
//            else if(numberOfClicks == 4) {
//                objP2[0] = event.getX();
//                objP2[1] = event.getY();
//                screenShotButton.setText("Calculate Dimensions");
//                Toast.makeText(getContext(),"clicked4", Toast.LENGTH_SHORT).show();
//            }
//            else {}
//            return true;
//        }
//
//    };

    View.OnClickListener screenShotButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(numberOfClicks == 0) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else {
                calculateDistances();
            }
        }
    };
    ssh -T git@github.com
    public void calculateDistances() {
        double baseLength = Math.sqrt((baseP2[0]-baseP1[0])*(baseP2[0]-baseP1[0])+(baseP2[1]-baseP1[1])*(baseP2[1]-baseP1[1]));
        double objLength = Math.sqrt((objP2[0]-objP1[0])*(objP2[0]-objP1[0])+(objP2[1]-objP1[1])*(objP2[1]-objP1[1]));
        double factor = actualLengthOfBaseObject/baseLength;
        double actualLengthOfObject = factor*objLength;
        System.out.println("actual length" + actualLengthOfObject);
        Toast.makeText(getContext(),"Length is : " + actualLengthOfObject, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
        }
    }

}
