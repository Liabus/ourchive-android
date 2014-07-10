package com.liabus.ourchive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PhotoSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class PhotoSelectFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "photos";

    private ArrayList<String> photos;

    private ArrayList<String> selectedPhotos = new ArrayList<String>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PhotoSelectFragment.
     */
    public static PhotoSelectFragment newInstance(Object param1) {
        ArrayList<String> pp = (ArrayList<String>)param1;
        PhotoSelectFragment fragment = new PhotoSelectFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, pp);
        fragment.setArguments(args);
        return fragment;
    }
    public PhotoSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            photos = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_photo_select, container, false);

        final LinearLayout ll = (LinearLayout)root.findViewById(R.id.photo_select_list);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        final int width = point.x;

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading. Please wait...", true);

        new Thread(new Runnable() {
            public void run() {
                ImageButton img;

                final ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
                anim.setDuration(500);
                anim.setInterpolator(new BounceInterpolator());
                anim.setFillAfter(true);

                final ScaleAnimation animReverse = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);
                anim.setDuration(500);
                anim.setInterpolator(new BounceInterpolator());
                anim.setFillAfter(true);

                for (String photo : photos){

                    final RelativeLayout layout = new RelativeLayout(getActivity());
                    final String finalPhoto = photo;

                    img = new ImageButton(getActivity());
                    img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    img.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    img.setImageBitmap(PhotoUtils.getThumbnailFromUrl(photo, width));

                    final ImageView toggle = new ImageView(getActivity());
                    toggle.setImageDrawable(getResources().getDrawable(R.drawable.orangecheck));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    toggle.setLayoutParams(params);
                    toggle.setVisibility(View.INVISIBLE);

                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(toggle.getVisibility() == View.INVISIBLE) {

                                selectedPhotos.add(finalPhoto);

                                toggle.setVisibility(View.VISIBLE);
                                toggle.startAnimation(anim);
                            }else{

                                selectedPhotos.remove(finalPhoto);

                                toggle.startAnimation(animReverse);
                                toggle.setVisibility(View.INVISIBLE);
                            }

                            if(gMenu != null) {
                                boolean vis = false;
                                if(selectedPhotos.size() > 0) {
                                    vis = true;
                                }
                                gMenu.findItem(R.id.action_done).setEnabled(vis);
                            }
                        }
                    });

                    layout.addView(img);
                    layout.addView(toggle);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ll.addView(layout);
                        }
                    });
                }

                //Close loading indicator:
                dialog.dismiss();
            }
        }).start();

        return root;
    }

    Menu gMenu = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_select, menu);
        gMenu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_done:
                if(selectedPhotos.size() > 0) {
                    ((Home) getActivity()).gracefulAdvanceWithData(201, selectedPhotos);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
