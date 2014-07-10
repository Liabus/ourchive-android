package com.liabus.ourchive;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UploadAudio#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class UploadAudio extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "fileName";

    private String fileName;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UploadAudio.
     */
    public static UploadAudio newInstance(Object param1) {
        String fn = (String)param1;
        UploadAudio fragment = new UploadAudio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fn);
        fragment.setArguments(args);
        return fragment;
    }
    public UploadAudio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileName = getArguments().getString(ARG_PARAM1);
        }else{
            ((Home)getActivity()).gracefulBack();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_audio, container, false);
    }


}
