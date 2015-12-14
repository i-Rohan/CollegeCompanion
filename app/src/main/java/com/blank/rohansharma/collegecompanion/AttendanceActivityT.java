package com.blank.rohansharma.collegecompanion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AttendanceActivityT extends AppCompatActivity
{

    SQLiteDatabase studinfo;
    int osApi=android.os.Build.VERSION.SDK_INT;
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity_t);

        /*File dbchk=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/attendance");
        if(!dbchk.exists())
        {
            AttendanceRecordT = openOrCreateDatabase("attendance", MODE_PRIVATE, null);
            AttendanceRecordT.execSQL("CREATE TABLE IF NOT EXISTS record(name TEXT UNIQUE NOT NULL);");
            if (osApi >= 21)
                studinfo = SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/studentinfo", null, SQLiteDatabase.OPEN_READONLY);
            else
                studinfo = SQLiteDatabase.openDatabase("/sdcard/studentinfo", null, SQLiteDatabase.OPEN_READONLY);
            Cursor c1 = studinfo.rawQuery("SELECT * FROM info", null);
            c1.moveToFirst();
            while (c1.moveToNext())
                AttendanceRecordT.execSQL("INSERT INTO record VALUES('" + c1.getString(0) + "')");
        }*/

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
                    return new FragmentTakeAttendance();
                case 1:
                    return new FragmentAttendanceRecord();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Take Attendance";
                case 1:
                    return "Attendance Record";
            }
            return null;
        }
    }

    public static class FragmentTakeAttendance extends Fragment
    {
        WifiApManager wifiApManager;
        TextView textView,taking;
        Button take;
        ProgressBar progressBar;

        int osApi=android.os.Build.VERSION.SDK_INT;
        boolean thread,a=true,wifi;
        //SQLiteDatabase Attendance;
        //public String currentDateTimeString;

        public FragmentTakeAttendance() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_take_attendance_t, container, false);
            textView=(TextView)rootView.findViewById(R.id.textView);
            taking=(TextView)rootView.findViewById(R.id.taking);
            taking.setVisibility(View.GONE);
            take=(Button)rootView.findViewById(R.id.take);
            progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            thread=true;
            take.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(a)
                    {
                        /*currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date()).replaceAll(" ","_");
                        currentDateTimeString=currentDateTimeString.replaceAll(",", "");
                        currentDateTimeString=currentDateTimeString.replaceAll(":","_");
                        Attendance=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/attendance", null, SQLiteDatabase.OPEN_READWRITE);
                        Attendance.execSQL("ALTER TABLE record ADD COLUMN "+currentDateTimeString+" CHAR");
                        Attendance.execSQL("UPDATE record SET "+currentDateTimeString+"='A'");*/
                        a=false;
                        take.setText("Done");
                        taking.setVisibility(View.VISIBLE);
                        taking.setText("Taking Attendance...");
                        progressBar.setVisibility(View.VISIBLE);
                        wifiApManager = new WifiApManager(getContext());
                        SQLiteDatabase db;
                        db = SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin", null, SQLiteDatabase.OPEN_READONLY);
                        Cursor c = db.rawQuery("SELECT * FROM user", null);
                        c.moveToFirst();
                        HotspotManager.setHotspotName(c.getString(0),getContext());
                        if (HotspotManager.isWifiOn(getContext()))
                        {
                            wifi=true;
                            HotspotManager.turnOnOffWifi(getContext(), false);
                        }
                        HotspotManager.turnOnOffHotspot(getContext(), true);
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                while(thread)
                                {
                                    try
                                    {
                                        Thread.sleep(1000);
                                        Log.d("Thread", "Running");
                                        scan();
                                    }
                                    catch (InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                    else
                    {
                        a=true;
                        take.setText("Take Attendance");
                        HotspotManager.turnOnOffHotspot(getContext(), false);
                        if(wifi)
                            HotspotManager.turnOnOffWifi(getContext(),true);
                        progressBar.setVisibility(View.GONE);
                        taking.setVisibility(View.GONE);
                        thread = false;
                        SQLiteDatabase studinfo;
                        if(osApi>=21)
                            studinfo =SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/studentinfo",null,SQLiteDatabase.OPEN_READWRITE);
                        else
                            studinfo =SQLiteDatabase.openDatabase("/sdcard/studentinfo",null,SQLiteDatabase.OPEN_READWRITE);
                        studinfo.execSQL("UPDATE info SET marked='0'");
                        studinfo.close();
                    }
                }
            });
            return rootView;
        }

        private void scan()
        {
            wifiApManager.getClientList(false, new FinishScanListener()
            {

                @Override
                public void onFinishScan(final ArrayList<ClientScanResult> clients)
                {
                    SQLiteDatabase studinfo;
                    if(osApi>=21)
                        studinfo =SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/studentinfo",null,SQLiteDatabase.OPEN_READWRITE);
                    else
                        studinfo =SQLiteDatabase.openDatabase("/sdcard/studentinfo",null,SQLiteDatabase.OPEN_READWRITE);
                    for (ClientScanResult clientScanResult : clients)
                    {
                        Cursor c= studinfo.rawQuery("SELECT * FROM info WHERE mac_address='" + clientScanResult.getHWAddr() + "' AND marked='0'", null);
                        if(c.moveToFirst())
                        {
                            textView.append("\n" + c.getString(0)+" is present");
                            studinfo.execSQL("UPDATE info SET marked='1' WHERE mac_address='" + clientScanResult.getHWAddr() + "'");
                            //Attendance=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/attendance", null, SQLiteDatabase.OPEN_READWRITE);
                            //Attendance.execSQL("UPDATE record SET "+currentDateTimeString+"='P' WHERE name='"+c.getString(0)+"'");
                        }
                        c.close();
                    }
                    studinfo.close();
                }
            });
        }
    }

    public static class FragmentAttendanceRecord extends Fragment
    {
        //TextView text,t1,t2,t3,t4,t5,t6;

        public FragmentAttendanceRecord()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_attendance_record_t, container, false);
            /*SQLiteDatabase Attendance;
            t1=(TextView)rootView.findViewById(R.id.t1);
            t2=(TextView)rootView.findViewById(R.id.t2);
            t3=(TextView)rootView.findViewById(R.id.t3);
            t4=(TextView)rootView.findViewById(R.id.t4);
            t5=(TextView)rootView.findViewById(R.id.t5);
            t6=(TextView)rootView.findViewById(R.id.t6);
            Attendance=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/attendance",null,SQLiteDatabase.OPEN_READWRITE);
            Cursor c=Attendance.rawQuery("SELECT * FROM record",null);
            text=(TextView)rootView.findViewById(R.id.text);
            Cursor ti = Attendance.rawQuery("PRAGMA table_info(record)", null);
            if ( ti.moveToFirst() )
                do
                {
                    text.append(ti.getString(1)+" ");
                }
                while (ti.moveToNext());*/
            return rootView;
        }
    }
}