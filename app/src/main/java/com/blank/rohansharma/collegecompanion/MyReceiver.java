package com.blank.rohansharma.collegecompanion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver
{
    public static String customIntent="changeProfileToNormal";
    String p = " ";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyReceiver");
        //Acquire the lock
        wl.acquire();
        File tmp = new File("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable");
        if(tmp.exists())
        {
            SQLiteDatabase TimeTable,Stream;
            TimeTable = SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable", null, SQLiteDatabase.OPEN_READONLY);
            Stream=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin", null, SQLiteDatabase.OPEN_READONLY);
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String Day = null;
            switch (day) {
                case 1:
                    Day = "Sunday";
                    break;
                case 2:
                    Day = "Monday";
                    break;
                case 3:
                    Day = "Tuesday";
                    break;
                case 4:
                    Day = "Wednesday";
                    break;
                case 5:
                    Day = "Thursday";
                    break;
                case 6:
                    Day = "Friday";
                    break;
                case 7:
                    Day = "Saturday";
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());
            if (time.compareTo("09:30:00") >= 0 && time.compareTo("10:30:00") == -1)
                p = "p1";
            else if (time.compareTo("10:30:00") >= 0 && time.compareTo("11:30:00") <= -1)
                p = "p2";
            else if (time.compareTo("11:30:00") >= 0 && time.compareTo("12:30:00") <= -1)
                p = "p3";
            else if (time.compareTo("12:30:00") >= 0 && time.compareTo("13:30:00") <= -1)
                p = "p4";
            else if (time.compareTo("13:30:00") >= 0 && time.compareTo("14:30:00") <= -1)
                p = "p5";
            else if (time.compareTo("14:30:00") >= 0 && time.compareTo("15:30:00") <= -1)
                p = "p6";
            else if (time.compareTo("15:30:00") >= 0 && time.compareTo("16:30:00") <= -1)
                p = "p7";
            else if (time.compareTo("16:30:00") >= 0 && time.compareTo("17:30:00") <= -1)
                p = "p8";
            else if(time.compareTo("17:30:00") >= 0 && time.compareTo("17:45:00") <= -1)
                p="blah";
            if (p.compareTo(" ")!=0&&p.compareTo("blah")!=0)
            {
                Cursor c0=Stream.rawQuery("SELECT * FROM user",null);
                c0.moveToFirst();
                Cursor c = TimeTable.rawQuery("SELECT " + p + " FROM "+c0.getString(2)+" WHERE day='" + Day + "'", null);
                Stream.close();
                c.moveToFirst();
                if (c.getString(0).compareTo("Break") != 0)
                {
                    changeProfile(context,0);
                    Log.d("SoundProfile", "Profile changed");
                }
                else
                {
                    changeProfile(context,1);
                    Log.d("SoundProfile","Profile changed");
                    //Toast.makeText(context,"Normal Mode", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
            else if(p.compareTo("blah")==0)
            {
                changeProfile(context,1);
                Log.d("SoundProfile", "Profile changed");
                //Toast.makeText(context,"Normal Mode", Toast.LENGTH_SHORT).show();
            }
            else if(p.compareTo(" ")==0)
            {
                Log.d("Alarm", "Running");
            }
            /*else
            {
                changeProfile(context, 1);
                Log.d("SoundProfile", "Profile changed");
                //Toast.makeText(context,"Normal Mode", Toast.LENGTH_SHORT).show();
            }*/
            TimeTable.close();

            //Release the lock
            wl.release();
        }
    }

    public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,0);
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        long t;
        Date d1,d2=null;
        d1=new Date();
        if(time.compareTo("00:00:00")>=0&&time.compareTo("09:30:00")<=0)
        {
            try {
                d2=sdf.parse("09:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("09:30:00") >0 && time.compareTo("10:30:00") <=0)
        {
            try {
                d2=sdf.parse("10:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("10:30:00") >0 && time.compareTo("11:30:00") <= 0)
        {
            try {
                d2=sdf.parse("11:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("11:30:00") >0 && time.compareTo("12:30:00") <= 0)
        {
            try {
                d2=sdf.parse("12:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("12:30:00") >0 && time.compareTo("13:30:00") <= 0)
        {
            try {
                d2=sdf.parse("13:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("13:30:00") >0 && time.compareTo("14:30:00") <= 0)
        {
            try {
                d2=sdf.parse("14:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("14:30:00") >0 && time.compareTo("15:30:00") <= 0)
        {
            try {
                d2=sdf.parse("15:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("15:30:00") >0 && time.compareTo("16:30:00") <=0)
        {
            try {
                d2=sdf.parse("16:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else if (time.compareTo("16:30:00") >0 && time.compareTo("17:30:00") <= 0)
        {
            try {
                d2=sdf.parse("17:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime());
        }
        else
        {
            try {
                d2=sdf.parse("23:59:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t=System.currentTimeMillis()+Math.abs(d1.getTime()-d2.getTime())+1;
            try
            {
                d1=sdf.parse("00:00:00");
                d2=sdf.parse("09:30:00");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }*/
        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000*60*5,pi);
    }

    public void changeProfile(Context context,int n)
    {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (n)
        {
            case 0:
                Log.i("SoundProfile", "Vibrate Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                Intent intent = new Intent(customIntent);
                //PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
                break;
            case 1:
                Log.i("SoundProfile", "Normal Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                //am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2,0);
        }
    }
}