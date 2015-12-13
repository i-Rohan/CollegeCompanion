package com.blank.rohansharma.collegecompanion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

//from external Library
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.io.File;

public class IntroActivity extends AppIntro
{
    File dir=new File("/data/data/com.blank.rohansharma.collegecompanion/introskip");
    File intro=new File(dir,"introskip");

    @Override
    protected void onStart()
    {
        super.onStart();
        if(intro.exists())
            startLoginActivity();
    }

    @Override
    public void init(Bundle savedInstanceState)
    {
        addSlide(AppIntroFragment.newInstance("Welcome to College Companion", "Attendance and time table management made simple", R.drawable.graduation, Color.parseColor("#222222")));
        addSlide(AppIntroFragment.newInstance("Attendance Management", "Students can mark their attendance via their smart phone. Also they can check their attendance record at any time", R.drawable.attendance, Color.parseColor("#FFA500")));
        addSlide(AppIntroFragment.newInstance("Time Table Management","The time table will be downloaded from server and the app will notify 15 minutes before every class. And also the app will switch to 'Vibrate Only' mode during class hours",R.drawable.timetable,Color.parseColor("#5B6BC0")));
        addSlide(AppIntroFragment.newInstance("All done!","You are all set to go!",R.drawable.correct,Color.parseColor("#01B8FC")));
        //setBarColor(Color.parseColor("#3F51B5"));
        // setSeparatorColor(Color.parseColor("#d3d3d3"));
    }
    private void startLoginActivity()    //to start login activity
    {
        if(!dir.exists())
        {
            dir.mkdir();
            try
            {
                intro.createNewFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();   //to finish intro activity after starting login activity
    }

    @Override
    public void onSkipPressed() //on skip button pressed
    {
        startLoginActivity();
    }

    @Override
    public void onDonePressed() //on done button pressed
    {
        startLoginActivity();
    }
}