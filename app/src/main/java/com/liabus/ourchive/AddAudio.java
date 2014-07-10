package com.liabus.ourchive;

import android.app.Activity;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AddAudio extends Fragment {

    AudioRecorder recorder = null;

    public AddAudio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(recorder == null){
            recorder = new AudioRecorder();
        }
    }

    private OnClickListener recordPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            recorder.recordToggle();
        }
    };

    private OnClickListener cancelSubmit = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private OnClickListener acceptSubmit = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Home) getActivity()).gracefulAdvanceWithData(500, recorder.fileName);
        }
    };

    private OnClickListener playPressed = new OnClickListener() {
        @Override
        public void onClick(View v) {
            recorder.playToggle();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_audio, container, false);

        ImageButton recordBtn = (ImageButton) root.findViewById(R.id.record_btn);
        ImageButton playBtn = (ImageButton) root.findViewById(R.id.play_btn);

        ImageButton acceptSubmitBtn = (ImageButton) root.findViewById(R.id.submit_btn_audio);

        recordBtn.setOnClickListener(recordPressed);
        playBtn.setOnClickListener(playPressed);
        acceptSubmitBtn.setOnClickListener(acceptSubmit);

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