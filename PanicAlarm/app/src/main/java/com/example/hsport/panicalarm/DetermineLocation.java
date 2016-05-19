package com.example.hsport.panicalarm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DetermineLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determine_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mMap == null) {
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap = mapFragment.getMap();
        }
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
          Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

           if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            double dLatitude = myLocation.getLatitude();
            double dLongitude = myLocation.getLongitude();
            System.out.println("Add " + dLatitude);
            //double dLongitude = mMap.getMyLocation().getLongitude();
            String no = "0211077348";
            String message = "Hi, My location is " + dLatitude + ", " + dLongitude ;

            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(no, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Send Succesfully", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, SendMMS.class);
            startActivityForResult(intent, 0);
            //Record
            //final MediaRecorder recorder = null;

           // if(recorder != null){
               // recorder.release();
            //}

//            final MediaRecorder recorder = new MediaRecorder();
//            if(recorder != null){
//                 recorder.release();
//            }
//            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
//            recorder.setOutputFile(Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                    +"test.3gp");
//            try {
//                recorder.prepare();
//                recorder.start();
//                //Timer timer = new Timer();
//
////                timer.schedule(new TimerTask() {
////                    public void run() {
////                        recorder.stop();
////                        recorder.release();
////                    }
////                }, 3000, 3000);
//            } catch (IOException io) {
//                Toast.makeText(getApplicationContext(), "Record File", Toast.LENGTH_LONG).show();
//            }
//
//            recorder.stop();
//            recorder.release();
//
//
//            //Send MMS
//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
//            sendIntent.putExtra("address", "0211077348");
//            sendIntent.putExtra("sms_body", "if you are sending text");
//            final File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Downloadtest.3gp");
//            Uri uri = Uri.fromFile(file1);
//            Log.e("Path", "" + uri);
//            sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            sendIntent.setType("video/3gp");
//            startActivity(sendIntent);

        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
