package com.example.hsport.panicalarm;

import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class SendMMS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mms);
        MediaRecorder recorder = null;
        if(recorder != null){
            recorder.release();
        }

        recorder =  new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                +"test.3gp");
        try {
            recorder.prepare();
            recorder.start();
            //Timer timer = new Timer();

//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        recorder.stop();
//                        recorder.release();
//                    }
//                }, 3000, 3000);
        } catch (IOException io) {
            Toast.makeText(getApplicationContext(), "Record File", Toast.LENGTH_LONG).show();
        }

        recorder.stop();
        recorder.release();


        //Send MMS
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        sendIntent.putExtra("address", "0211077348");
        sendIntent.putExtra("sms_body", "if you are sending text");
        final File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Downloadtest.3gp");
        Uri uri = Uri.fromFile(file1);
        Log.e("Path", "" + uri);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("video/3gp");
        startActivity(sendIntent);


    }
}
