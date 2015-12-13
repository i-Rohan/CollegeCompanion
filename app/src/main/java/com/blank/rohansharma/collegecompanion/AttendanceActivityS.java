package com.blank.rohansharma.collegecompanion;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class AttendanceActivityS extends AppCompatActivity
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity_s);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    return new FragmentMarkAttendance();
                case 1:
                    return new FragmentAttendanceRecord();
            }
            return null;
        }

        @Override
        public int getCount()
        {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mark Attendance";
                case 1:
                    return "Attendance Record";
            }
            return null;
        }
    }

    public static class FragmentMarkAttendance extends Fragment
    {
        ProgressBar progressBar2;
        Button mark;
        TextView marking;

        boolean a=true;

        public FragmentMarkAttendance()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_mark_attendance_s, container, false);

            progressBar2=(ProgressBar)rootView.findViewById(R.id.progressBar2);
            progressBar2.setVisibility(View.GONE);
            marking=(TextView)rootView.findViewById(R.id.marking);
            marking.setVisibility(View.GONE);
            mark=(Button)rootView.findViewById(R.id.mark);

            mark.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(a)
                    {
                        a=false;
                        String networkSSID = "akanksha.mishra";
                        mark.setText("Done");
                        progressBar2.setVisibility(View.VISIBLE);
                        marking.setVisibility(View.VISIBLE);
                        WifiConfiguration conf = new WifiConfiguration();
                        conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes
                        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.addNetwork(conf);
                        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                        for (WifiConfiguration i : list) {
                            if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\""))
                            {
                                wifiManager.disconnect();
                                wifiManager.enableNetwork(i.networkId, true);
                                wifiManager.reconnect();
                                break;
                            }
                        }
                    }
                    else
                    {
                        a=true;
                        mark.setText("Mark Now");
                        progressBar2.setVisibility(View.GONE);
                        marking.setVisibility(View.GONE);
                    }
                }
            });
            return rootView;
        }
    }

    public static class FragmentAttendanceRecord extends Fragment
    {
        public FragmentAttendanceRecord()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_attendance_record_s, container, false);
            return rootView;
        }
    }
}