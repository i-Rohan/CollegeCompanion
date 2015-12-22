package com.blank.rohansharma.collegecompanion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dd.CircularProgressButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends Activity
{
    int osApi=android.os.Build.VERSION.SDK_INT;
    boolean a=true;
    EditText Username,Password;
    CircularProgressButton Login;
    TextView Error;
    private ProgressDialog pDialog;
    SQLiteDatabase Database,Loggedin;
    boolean login;
    public static String[] file_url = {"http://10.7.1.125/collegecompanion/login","login"};
    CheckBox checkbox;
    File del;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        File tmp=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/");
        if(!tmp.exists())
            tmp.mkdir();

        File checkDB=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/loggedin");

        if(checkDB.exists())
        {
            Loggedin=openOrCreateDatabase("loggedin",Context.MODE_PRIVATE,null);
            Cursor c=Loggedin.rawQuery("SELECT * FROM user",null);
            if(c.moveToFirst())
            {
                Loggedin.close();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }

        final WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
        {
            Username = (EditText) findViewById(R.id.username);
            Password = (EditText) findViewById(R.id.password);
            Login = (CircularProgressButton) findViewById(R.id.login);
            Error = (TextView) findViewById(R.id.error);
            checkbox=(CheckBox)findViewById(R.id.checkBox);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked)
                        Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    else
                        Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            });
            Login.setOnClickListener(new View.OnClickListener()
            {
                    @Override
                    public void onClick(View v)
                    {
                        if(a)
                        {
                            if(osApi>=21)
                                Database = SQLiteDatabase.openDatabase("/data/data/com.blank.rohansharma.collegecompanion/databases/login", null, SQLiteDatabase.OPEN_READONLY);
                            else
                                Database = SQLiteDatabase.openDatabase("/sdcard/login", null, SQLiteDatabase.OPEN_READONLY);
                            Cursor c = Database.rawQuery("SELECT * FROM login WHERE username='" + Username.getText().toString().trim() + "'", null);
                            if (c.moveToFirst()) {
                                if (Password.getText().toString().equals(c.getString(1)))
                                    login = true;
                                else
                                    login = false;
                            }
                            if (login) {
                                Database.close();
                                Login.setProgress(100); //color-Green text-success
                                Loggedin = openOrCreateDatabase("loggedin", Context.MODE_PRIVATE, null);
                                Loggedin.execSQL("CREATE TABLE IF NOT EXISTS user(username TEXT,SorT CHAR,stream TEXT);");
                                Loggedin.execSQL("INSERT INTO user VALUES('" + c.getString(0) + "','" + c.getString(2) + "','" + c.getString(3) + "');");
                                if(c.getString(2).compareTo("T")==0)
                                    new DownloadFileFromURL().execute("http://10.7.1.125:8080/CollegeCompanion/studentinfo","studentinfo");
                                Loggedin.close();
                                Intent i=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();   //to finish login activity after starting main activity
                            } else if (Username.getText().toString().trim().length() == 0)   //if username is blank
                            {
                                Error.setText("Username can't be blank!");
                                Login.setProgress(-1);  //color-red
                                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.edit_area));
                                Login.setProgress(0);   //color-blue
                            } else {
                                Error.setText("Wrong username or password!");
                                Login.setProgress(-1);  //color-red
                                YoYo.with(Techniques.Shake).playOn(findViewById(R.id.edit_area));
                                Login.setProgress(0);   //color-blue
                            }
                        }
                        else {
                            Login.setProgress(-1);
                            Login.setText("Error");
                            YoYo.with(Techniques.Shake).playOn(findViewById(R.id.edit_area));
                            Error.setText("Server Down\nTry again later");
                        }
                    }
                });
        }
        else
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            finish();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("WiFi is off.\nWiFi needs to be switched on.").setPositiveButton("Open Settings", dialogClickListener)
                    .setNegativeButton("Exit", dialogClickListener).setCancelable(false).show();
        }
    }

    /*public void showMessage(String title,String message,boolean a)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(a);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }*/

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

    public void onDestroy()
    {
        super.onDestroy();
        if(osApi>=21)
            del=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/login");
        else
            del=new File("/sdcard/login");
        del.delete();
    }
    public void onPause()
    {
        super.onPause();
        if(osApi>=21)
            del=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/login");
        else
            del=new File("/sdcard/login");
        del.delete();
    }

    public void onStop()
    {
        super.onStop();
        if(osApi>=21)
            del=new File("/data/data/com.blank.rohansharma.collegecompanion/databases/login");
        else
            del=new File("/sdcard/login");
        del.delete();
    }

    public void onResume()
    {
        super.onResume();
        new DownloadFileFromURL().execute(file_url);
    }

    public void onRestart()
    {
        super.onRestart();
        new DownloadFileFromURL().execute(file_url);
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String>
    {
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showDialog(0);
        }

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
                a=false;
                Log.d("abcd","Error Downloading!");
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
            // dismiss the dialog after the file was downloaded
            dismissDialog(0);
        }
    }
}