package com.liabus.ourchive;

import android.app.Activity;
import android.app.Fragment;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.EventListener;

/**
 * Created by jack on 7/5/14.
 */

public class AudioRecorder
{
    public final String LOG_TAG = "AudioRecordTest";
    public String fileName = null;
    public MediaRecorder mRecorder = null;
    public MediaPlayer mPlayer = null;

    private boolean recording = false;

    public boolean statePlay(){
        return mPlayer.isPlaying();
    }
    public boolean stateRecord(){
        return recording;
    }



    public void playToggle() {
        if (mPlayer == null || !mPlayer.isPlaying()) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(fileName);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "play prepare() failed");
            }
        } else {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void recordToggle() {
        if (!recording) {
            fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            fileName += "/audiorecordtest.3gp";
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(fileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "record prepare() failed");
            }

            mRecorder.start();
            recording = true;
        } else {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            recording = false;
        }
    }
}
