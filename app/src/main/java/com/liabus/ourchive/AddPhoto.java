package com.liabus.ourchive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddPhoto#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AddPhoto extends Fragment {

    private static final String ARG_PARAM1 = "photos";

    private ArrayList<String> photos;

    public static AddPhoto newInstance(Object photosParam) {
        ArrayList<String> pp = (ArrayList<String>)photosParam;
        AddPhoto fragment = new AddPhoto();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, pp);
        fragment.setArguments(args);
        return fragment;
    }

    public AddPhoto(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photos = getArguments().getStringArrayList(ARG_PARAM1);
            if(photos.size() == 0){
                //No photos, get out of here:
                ((Home)getActivity()).gracefulBack();
            }
        }else{
            ((Home)getActivity()).gracefulBack();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_photo, container, false);

        ImageView img = (ImageView)root.findViewById(R.id.photo_add_preview);
        img.setImageBitmap(PhotoUtils.getThumbnailFromUrl(photos.get(0), PhotoUtils.dpToPx(this.getResources().getDisplayMetrics(), 150)));
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
