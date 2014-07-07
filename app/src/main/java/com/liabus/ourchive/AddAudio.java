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



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddAudio#newInstance} factory method to
 * create an instance of this fragment.
 *
 */

public class AddAudio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAudio.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAudio newInstance(String param1, String param2) {
        AddAudio fragment = new AddAudio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AddAudio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private boolean isRecording = false;

    private boolean isPlaying = false;

    private OnClickListener recordPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isRecording) {
                AudioRecordTest.startRecording();
                isRecording = true;
            }
            else {
                AudioRecordTest.stopRecording();
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
                AudioRecordTest.startPlaying();
                isPlaying = true;
            }
            else {
                AudioRecordTest.stopPlaying();
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