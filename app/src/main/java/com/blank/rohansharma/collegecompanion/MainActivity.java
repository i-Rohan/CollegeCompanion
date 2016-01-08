package com.blank.rohansharma.collegecompanion;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.CircularProgressButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity
{
    Boolean update=false;
    ProgressDialog pDialog;
    TextView u;
    Button Logout,update1;
    String[] file_url={"http://10.7.1.125/collegecompanion/timetable","timetable"};
    SQLiteDatabase Loggedin,Update;
    String SorT,version;
    ImageView dp;
    File f = new File("/data/data/com.blank.rohansharma.collegecompanion/images");
    Bitmap bmp;
    //public static String customIntent="changeProfileToNormal";
    Intent intent;
    PendingIntent pi;
    int osApi=android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update1=(Button)findViewById(R.id.update);

        PackageInfo pInfo = null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        version = pInfo.versionName;

        new DownloadFileFromURL().execute(new String[]{"http://10.7.1.125/collegecompanion/timetable", "timetable"});

        if(!f.exists())
            f.mkdir();
        f=new File("/data/data/com.blank.rohansharma.collegecompanion/images/dp.jpg");
        dp=(ImageView)findViewById(R.id.dp);
        if(f.exists())
        {
            bmp= BitmapFactory.decodeFile("/data/data/com.blank.rohansharma.collegecompanion/images/dp.jpg");
            dp.setImageBitmap(bmp);
        }
        else
            dp.setImageDrawable(getResources().getDrawable(R.drawable.flatface));
        registerForContextMenu(dp);

        f=new File("/data/data/com.blank.rohansharma.collegecompanion/timetable");
        if(!f.exists())
            f.mkdir();

        MyReceiver soundProfile;
        soundProfile=new MyReceiver();

        /*intent = new Intent("abc");
        pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);*/

        final WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
            new DownloadFileFromURL().execute(file_url);

        Loggedin=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin",null,SQLiteDatabase.OPEN_READONLY);
        Cursor c=Loggedin.rawQuery("SELECT * FROM user",null);
        c.moveToFirst();
        u=(TextView)findViewById(R.id.User);
        u.setText(c.getString(0).toUpperCase());
        u.setText(version);
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String inputString = "09:30:00";
        Date date=null;
        try {date = sdf.parse("1970-01-01 " + inputString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        u.setText(String.valueOf(date.getTime()));*/
        SorT=c.getString(1);
        c.close();
        Loggedin.close();

        Logout=(CircularProgressButton)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                File file=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin");
                file.delete();
                file= new File("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable");
                file.delete();
                file = new File("/data/data/com.blank.rohansharma.collegecompanion/images/dp.jpg");
                file.delete();
                file=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/attendance");
                file.delete();
                file=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/studentinfo");
                file.delete();
                intent = new Intent("abc");
                pi = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);
                AlarmManager am=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                am.cancel(pi);
                Log.d("abc", "End Alarm");
                startLoginActivity();
                finish();
            }
        });

        Context context = this.getApplicationContext();
        if(soundProfile!= null)
            soundProfile.SetAlarm(context);

        /*t=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                File tmp = new File("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable");
                while(tmp.exists())
                {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                        SQLiteDatabase TimeTable;
                        TimeTable = SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable", null, SQLiteDatabase.OPEN_READONLY);
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
                        String p = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String time = sdf.format(new Date());
                        if (time.compareTo("09:30:00") >= 1 && time.compareTo("10:30:00") <= -1)
                            p = "p1";
                        else if (time.compareTo("10:30:00") >= 1 && time.compareTo("11:30:00") <= -1)
                            p = "p2";
                        else if (time.compareTo("11:30:00") >= 1 && time.compareTo("12:30:00") <= -1)
                            p = "p3";
                        else if (time.compareTo("12:30:00") >= 1 && time.compareTo("13:30:00") <= -1)
                            p = "p4";
                        else if (time.compareTo("13:30:00") >= 1 && time.compareTo("14:30:00") <= -1)
                            p = "p5";
                        else if (time.compareTo("14:30:00") >= 1 && time.compareTo("15:30:00") <= -1)
                            p = "p6";
                        else if (time.compareTo("15:30:00") >= 1 && time.compareTo("16:30:00") <= -1)
                            p = "p7";
                        else if (time.compareTo("16:30:00") >= 1 && time.compareTo("17:30:00") <= -1)
                            p = "p8";
                        else
                            p = null;
                        if (p != null) {
                            Cursor c = TimeTable.rawQuery("SELECT " + p + " FROM timetable WHERE day='" + Day + "'", null);
                            c.moveToFirst();
                            if (c.getString(0).compareTo("Break") != 0)
                                changeProfile(0);
                            else
                                changeProfile(1);
                            c.close();
                        } else
                            changeProfile(1);
                        TimeTable.close();
                        Thread.dumpStack();
                }
            }
        });*/
    }

    public void onClickUpdate(View v)
    {
        Uri uri = Uri.parse("https://drive.google.com/file/d/0B9x5a0yDpekhM2xSN2E2STFESXM/view?usp=sharing");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(null);
        menu.add(0, v.getId(), 0, "Change");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Remove and set to default");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getTitle()=="Change")
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choose Picture"), 1);
        }
        else if(item.getTitle()=="Remove and set to default")
        {
            dp.setImageDrawable(getResources().getDrawable(R.drawable.flatface));
            f = new File("/data/data/com.blank.rohansharma.collegecompanion/images/dp.jpg");
            if(f.exists())
                f.delete();
        }
        else
            return false;
        return true;
    }

    /*public void changeProfile(int n)
    {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        switch (n)
        {
            case 0:
                Log.i("MyApp", "Vibrate Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                break;
            case 1:
                Log.i("MyApp", "Normal Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                am.setStreamVolume(AudioManager.STREAM_RING,am.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2,0);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==RESULT_CANCELED)
        {
            // action cancelled
        }
        if(resultCode==RESULT_OK)
        {
            Uri selectedimg = data.getData();
            try
            {
                bmp=MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                dp.setImageBitmap(bmp);
                try {
                    f = new File("/data/data/com.blank.rohansharma.collegecompanion/images/dp.jpg");
                    FileOutputStream out = new FileOutputStream(f);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch(Exception e) {}
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startLoginActivity()    //to start login activity
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();   //to finish intro activity after starting login activity
    }

    public void onClickLayout(View v)   //on click anywhere empty on the screen
    {
        YoYo.with(Techniques.RubberBand).playOn(findViewById(R.id.ll1)); //'Welcome!' animation
    }

    public void onAttendanceClick(View v)   //o attendance button click
    {
        if(SorT.compareTo("S")==0)
        {
            Intent i = new Intent(MainActivity.this, AttendanceActivityS.class);
            startActivity(i);   //start attendance activity
        }
        else if(SorT.compareTo("T")==0)
        {
            Intent i = new Intent(MainActivity.this, AttendanceActivityT.class);
            startActivity(i);   //start attendance activity
        }
    }

    public void onTimeTableClick(View v)    //on time table button click
    {
        Intent i=new Intent(MainActivity.this,TimeTableActivity.class);
        startActivity(i);   //start timetable activity
    }

    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case 0: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(false);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String>
    {
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url)
        {
            int count;
            try
            {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream("sdcard/"+f_url[1]);
                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1)
                    // writing data to file
                    output.write(data, 0, count);
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

                if(osApi>=21)
                {
                    InputStream in = null;
                    OutputStream out = null;
                    String outPath = "/data/data/com.blank.rohansharma.collegecompanion/databases/";
                    String inPath = "/storage/emulated/legacy/";
                    String inFile = f_url[1];
                    in = new FileInputStream(inPath + inFile);
                    out = new FileOutputStream(outPath + inFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    in = null;

                    // write the output file
                    out.flush();
                    out.close();
                    out = null;

                    // delete the original file
                    new File(inPath + inFile).delete();
                }
            }
            catch (Exception e)
            {
                Log.d("DOWNLOAD", "Error Downloading!");
            }
            return null;
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url)
        {
            File f=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/update");
            if(f.exists())
            {
                Update=SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/update",null,SQLiteDatabase.OPEN_READONLY);
                Cursor c=Update.rawQuery("SELECT * FROM version", null);
                c.moveToFirst();
                String s=c.getString(0);
                if(s.compareTo(version)>0)
                {
                    update=true;
                    update1.setVisibility(View.VISIBLE);
                }
                c.close();
            }
        }
    }
}