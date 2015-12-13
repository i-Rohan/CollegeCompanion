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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;

public class TimeTableActivity extends AppCompatActivity
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

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
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position)
            {
                case 0:
                    return new FragmentDayWise();
                case 1:
                    return new FragmentComplete();
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
                    return "Day Wise";
                case 1:
                    return "Complete";
            }
            return null;
        }
    }

    public static class FragmentComplete extends Fragment
    {
        SQLiteDatabase TimeTable,Stream;
        File timetable;
        int osApi=android.os.Build.VERSION.SDK_INT;

        public FragmentComplete()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_time_table, container,false);

            if(osApi>=21)
                timetable=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable");
            else
                timetable=new File("/sdcard/timetable");
            TextView Mon1=(TextView)rootView.findViewById(R.id.Mon1);
            TextView Mon2=(TextView)rootView.findViewById(R.id.Mon2);
            TextView Mon3=(TextView)rootView.findViewById(R.id.Mon3);
            TextView Mon4=(TextView)rootView.findViewById(R.id.Mon4);
            TextView Mon5=(TextView)rootView.findViewById(R.id.Mon5);
            TextView Mon6=(TextView)rootView.findViewById(R.id.Mon6);
            TextView Mon7=(TextView)rootView.findViewById(R.id.Mon7);
            TextView Mon8=(TextView)rootView.findViewById(R.id.Mon8);
            TextView Tue1=(TextView)rootView.findViewById(R.id.Tue1);
            TextView Tue2=(TextView)rootView.findViewById(R.id.Tue2);
            TextView Tue3=(TextView)rootView.findViewById(R.id.Tue3);
            TextView Tue4=(TextView)rootView.findViewById(R.id.Tue4);
            TextView Tue5=(TextView)rootView.findViewById(R.id.Tue5);
            TextView Tue6=(TextView)rootView.findViewById(R.id.Tue6);
            TextView Tue7=(TextView)rootView.findViewById(R.id.Tue7);
            TextView Tue8=(TextView)rootView.findViewById(R.id.Tue8);
            TextView Wed1=(TextView)rootView.findViewById(R.id.Wed1);
            TextView Wed2=(TextView)rootView.findViewById(R.id.Wed2);
            TextView Wed3=(TextView)rootView.findViewById(R.id.Wed3);
            TextView Wed4=(TextView)rootView.findViewById(R.id.Wed4);
            TextView Wed5=(TextView)rootView.findViewById(R.id.Wed5);
            TextView Wed6=(TextView)rootView.findViewById(R.id.Wed6);
            TextView Wed7=(TextView)rootView.findViewById(R.id.Wed7);
            TextView Wed8=(TextView)rootView.findViewById(R.id.Wed8);
            TextView Thu1=(TextView)rootView.findViewById(R.id.Thu1);
            TextView Thu2=(TextView)rootView.findViewById(R.id.Thu2);
            TextView Thu3=(TextView)rootView.findViewById(R.id.Thu3);
            TextView Thu4=(TextView)rootView.findViewById(R.id.Thu4);
            TextView Thu5=(TextView)rootView.findViewById(R.id.Thu5);
            TextView Thu6=(TextView)rootView.findViewById(R.id.Thu6);
            TextView Thu7=(TextView)rootView.findViewById(R.id.Thu7);
            TextView Thu8=(TextView)rootView.findViewById(R.id.Thu8);
            TextView Fri1=(TextView)rootView.findViewById(R.id.Fri1);
            TextView Fri2=(TextView)rootView.findViewById(R.id.Fri2);
            TextView Fri3=(TextView)rootView.findViewById(R.id.Fri3);
            TextView Fri4=(TextView)rootView.findViewById(R.id.Fri4);
            TextView Fri5=(TextView)rootView.findViewById(R.id.Fri5);
            TextView Fri6=(TextView)rootView.findViewById(R.id.Fri6);
            TextView Fri7=(TextView)rootView.findViewById(R.id.Fri7);
            TextView Fri8=(TextView)rootView.findViewById(R.id.Fri8);
            TextView Sat1=(TextView)rootView.findViewById(R.id.Sat1);
            TextView Sat2=(TextView)rootView.findViewById(R.id.Sat2);
            TextView Sat3=(TextView)rootView.findViewById(R.id.Sat3);
            TextView Sat4=(TextView)rootView.findViewById(R.id.Sat4);
            TextView Sat5=(TextView)rootView.findViewById(R.id.Sat5);
            TextView Sat6=(TextView)rootView.findViewById(R.id.Sat6);
            TextView Sat7=(TextView)rootView.findViewById(R.id.Sat7);
            TextView Sat8=(TextView)rootView.findViewById(R.id.Sat8);

            if(timetable.exists())
            {
                if(osApi>=21)
                    TimeTable= SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable", null, SQLiteDatabase.OPEN_READONLY);
                else
                    TimeTable= SQLiteDatabase.openDatabase("/sdcard/timetable", null, SQLiteDatabase.OPEN_READONLY);
                Stream=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin", null, SQLiteDatabase.OPEN_READONLY);
                Cursor c0=Stream.rawQuery("SELECT * FROM user",null);
                c0.moveToFirst();
                Cursor c=TimeTable.rawQuery("SELECT * FROM " + c0.getString(2), null);
                Stream.close();
                c.moveToFirst();
                Mon1.setText(c.getString(1) + " ");
                Mon2.setText(c.getString(2) + " ");
                Mon3.setText(c.getString(3)+" ");
                Mon4.setText(c.getString(4)+" ");
                Mon5.setText(c.getString(5)+" ");
                Mon6.setText(c.getString(6)+" ");
                Mon7.setText(c.getString(7)+" ");
                Mon8.setText(c.getString(8)+" ");
                c.moveToNext();
                Tue1.setText(c.getString(1) + " ");
                Tue2.setText(c.getString(2) + " ");
                Tue3.setText(c.getString(3)+" ");
                Tue4.setText(c.getString(4)+" ");
                Tue5.setText(c.getString(5)+" ");
                Tue6.setText(c.getString(6)+" ");
                Tue7.setText(c.getString(7)+" ");
                Tue8.setText(c.getString(8)+" ");
                c.moveToNext();
                Wed1.setText(c.getString(1) + " ");
                Wed2.setText(c.getString(2) + " ");
                Wed3.setText(c.getString(3)+" ");
                Wed4.setText(c.getString(4)+" ");
                Wed5.setText(c.getString(5)+" ");
                Wed6.setText(c.getString(6)+" ");
                Wed7.setText(c.getString(7)+" ");
                Wed8.setText(c.getString(8)+" ");
                c.moveToNext();
                Thu1.setText(c.getString(1) + " ");
                Thu2.setText(c.getString(2) + " ");
                Thu3.setText(c.getString(3)+" ");
                Thu4.setText(c.getString(4)+" ");
                Thu5.setText(c.getString(5)+" ");
                Thu6.setText(c.getString(6)+" ");
                Thu7.setText(c.getString(7)+" ");
                Thu8.setText(c.getString(8)+" ");
                c.moveToNext();
                Fri1.setText(c.getString(1) + " ");
                Fri2.setText(c.getString(2) + " ");
                Fri3.setText(c.getString(3)+" ");
                Fri4.setText(c.getString(4)+" ");
                Fri5.setText(c.getString(5)+" ");
                Fri6.setText(c.getString(6)+" ");
                Fri7.setText(c.getString(7)+" ");
                Fri8.setText(c.getString(8)+" ");
                c.moveToNext();
                Sat1.setText(c.getString(1) + " ");
                Sat2.setText(c.getString(2) + " ");
                Sat3.setText(c.getString(3)+" ");
                Sat4.setText(c.getString(4)+" ");
                Sat5.setText(c.getString(5) + " ");
                Sat6.setText(c.getString(6)+" ");
                Sat7.setText(c.getString(7)+" ");
                Sat8.setText(c.getString(8)+" ");
                TimeTable.close();
            }

            return rootView;
        }
    }

    public static class FragmentDayWise extends Fragment
    {
        TextView nextClass,p1,p2,p3,p4,p5,p6,p7,p8;

        int osApi=android.os.Build.VERSION.SDK_INT;

        public FragmentDayWise()
        {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_day_wise, container,false);

            nextClass=(TextView)rootView.findViewById(R.id.nextClass);
            p1=(TextView)rootView.findViewById(R.id.p1);
            p2=(TextView)rootView.findViewById(R.id.p2);
            p3=(TextView)rootView.findViewById(R.id.p3);
            p4=(TextView)rootView.findViewById(R.id.p4);
            p5=(TextView)rootView.findViewById(R.id.p5);
            p6=(TextView)rootView.findViewById(R.id.p6);
            p7=(TextView)rootView.findViewById(R.id.p7);
            p8=(TextView)rootView.findViewById(R.id.p8);
            Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
            String [] values = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
            ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
            LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spinner.setAdapter(LTRadapter);
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if(day!=1)
                spinner.setSelection(day-2);
            else
                spinner.setSelection(2);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    SQLiteDatabase timetable,stream=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin", null, SQLiteDatabase.OPEN_READONLY);;
                    if(osApi>=21)
                        timetable= SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable", null, SQLiteDatabase.OPEN_READONLY);
                    else
                        timetable= SQLiteDatabase.openDatabase("/sdcard/timetable", null, SQLiteDatabase.OPEN_READONLY);
                    Cursor c0=stream.rawQuery("SELECT * FROM user",null),c=null;
                    c0.moveToFirst();
                    switch (position)
                    {
                        case 0:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Monday" + "'", null);
                            break;
                        case 1:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Tuesday" + "'", null);
                            break;
                        case 2:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Wednesday" + "'", null);
                            break;
                        case 3:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Thursday" + "'", null);
                            break;
                        case 4:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Friday" + "'", null);
                            break;
                        case 5:
                            c = timetable.rawQuery("SELECT * FROM "+c0.getString(2)+" WHERE day='" + "Saturday" + "'", null);
                            break;
                    }
                    c.moveToFirst();
                    p1.setText(c.getString(1));
                    p2.setText(c.getString(2));
                    p3.setText(c.getString(3));
                    p4.setText(c.getString(4));
                    p5.setText(c.getString(5));
                    p6.setText(c.getString(6));
                    p7.setText(c.getString(7));
                    p8.setText(c.getString(8));
                    stream.close();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            return rootView;
        }
    }
}
