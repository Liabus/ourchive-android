package com.liabus.ourchive;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;


public class Home extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        //Start at home:
        mTitle = "Home";

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void navigationDrawerHelper(int position){
        mNavigationDrawerFragment.selectItem(position);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mNavigationDrawerFragment.popItem();
    }

    private boolean started = false;

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        Fragment fragment = onSectionAttached(position);

        FragmentManager fragmentManager = getFragmentManager();

        if(!started){
            started = true;
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        }else{
            fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack((String)mTitle)
                .commit();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(mTitle);
    }

    public void onNavigationDrawerBack(int position) {
        onSectionAttached(position);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(mTitle);
    }

    public Fragment onSectionAttached(int number) {
        Fragment frag = new HomeFragment();
        switch (number) {
            case 0:
                mTitle = "Home";
                frag = new HomeFragment();
                break;
            case 1:
                mTitle = "My Ourchive";
                frag = new MyOurchive();
                break;
            case 2:
                mTitle = "Photos";
                frag = new AddPhoto();
                break;
            case 3:
                mTitle = "Video";
                frag = new AddVideo();
                break;
            case 4:
                mTitle = "Text";
                frag = new AddText();
                break;
            case 5:
                mTitle = "Audio";
                frag = new AddAudio();
                break;
            case 6:
                mTitle = "New Event";
                frag = new NewEvent();
                break;
            case 7:
                mTitle = "All Events";
                frag = new AllEvents();
                break;
            case 8:
                mTitle = "Settings";
                frag = new Settings();
                break;
        }
        return frag;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}