package com.blank.rohansharma.collegecompanion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Receiver0 extends BroadcastReceiver
{
    public Receiver0()
    {

    }

    @Override
    public void onReceive(final Context context, Intent intent)
    {
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss"),sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
            String time = sdf1.format(new Date());
            sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
            long t=0;
            if(time.compareTo("00:00:00")>=0&&time.compareTo("09:30:00")<=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"09:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            if (time.compareTo("09:30:00") > 0 && time.compareTo("10:30:00") <= 0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"10:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("10:30:00") >0 && time.compareTo("11:30:00") <= 0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"11:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("11:30:00") >0 && time.compareTo("12:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"12:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("12:30:00") >0 && time.compareTo("13:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"13:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("13:30:00") >0 && time.compareTo("14:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"14:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("14:30:00") >0 && time.compareTo("15:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"15:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("15:30:00") >0 && time.compareTo("16:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"10:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else if (time.compareTo("16:30:00") >0 && time.compareTo("17:30:00") <=0)
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"17:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    t=sdf2.parse("1970-01-01 "+"23:59:59").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        /*try {
            t=sdf2.parse("1970-01-01 "+"09:30:00").getTime()-sdf2.parse("1970-01-01 "+time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
            AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent("changeProfileToNormal");
            PendingIntent pi = PendingIntent.getBroadcast(context,1, i,0);
            Log.d("abc", "Alarm");
            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 15, pi);
            /*PugNotification.with(context)
                .load()
                .ongoing(false)
                .title(String.valueOf(t))
                .smallIcon(R.drawable.ic_launcher)
                .largeIcon(R.mipmap.ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .message("abc")
                .color(R.color.colorPrimary)
                .simple()
                .build();*/
    }
}