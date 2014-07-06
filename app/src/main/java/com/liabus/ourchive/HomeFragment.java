package com.liabus.ourchive;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by jack on 7/5/14.
 */


public class HomeFragment extends Fragment {
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
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    private OnClickListener fragmentSwap = new OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();

            Home ac = (Home)getActivity();

            if(id == R.id.add_photo_btn){
                ac.navigationDrawerHelper(2);
            }
            else if(id == R.id.add_video_btn){
                ac.navigationDrawerHelper(3);
            }
            else if(id == R.id.add_text_btn){
                ac.navigationDrawerHelper(4);
            }
            else if(id == R.id.add_audio_btn){
                ac.navigationDrawerHelper(5);
            }

            //TODO: Move this logic to the activity and call a function on the activity to ensure that the core UI updates properly.
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button photoButton = (Button) root.findViewById(R.id.add_photo_btn);
        photoButton.setOnClickListener(fragmentSwap);

        Button audioButton = (Button) root.findViewById(R.id.add_audio_btn);
        audioButton.setOnClickListener(fragmentSwap);

        Button videoButton = (Button) root.findViewById(R.id.add_video_btn);
        videoButton.setOnClickListener(fragmentSwap);

        Button textButton = (Button) root.findViewById(R.id.add_text_btn);
        textButton.setOnClickListener(fragmentSwap);

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

}
