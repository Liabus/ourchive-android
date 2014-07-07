package com.liabus.ourchive;

import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Environment;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;


public class AddAudio extends Fragment {

    AudioRecordTest recorder = null;

    public AddAudio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(recorder == null){
            recorder = new AudioRecordTest();
        }
    }

    private boolean isRecording = false;

    private boolean isPlaying = false;

    private OnClickListener recordPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isRecording) {
                recorder.startRecording();
                isRecording = true;
            } else {
                recorder.stopRecording();
                isRecording = false;
            }
        }
    };

    private OnClickListener addStreamPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private OnClickListener playPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isPlaying) {
                recorder.startPlaying();
                isPlaying = true;
            }
            else {
                recorder.stopPlaying();
                isPlaying = false;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_audio, container, false);

        Button recordBtn = (Button) root.findViewById(R.id.record_btn);
        Button addStreamBtn = (Button) root.findViewById(R.id.add_stream_btn);
        Button playBtn = (Button) root.findViewById(R.id.play_btn);

        recordBtn.setOnClickListener(recordPressed);
        addStreamBtn.setOnClickListener(addStreamPressed);
        playBtn.setOnClickListener(playPressed);

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}