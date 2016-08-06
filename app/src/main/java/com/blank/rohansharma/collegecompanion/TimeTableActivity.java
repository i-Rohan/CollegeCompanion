package com.blank.rohansharma.collegecompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.blank.rohansharma.collegecompanion.AppCommons.API_URL;
import static com.blank.rohansharma.collegecompanion.AppCommons.DAYS;
import static com.blank.rohansharma.collegecompanion.AppCommons.DAY_IDS;
import static com.blank.rohansharma.collegecompanion.AppCommons.PERIOD_IDS;
import static com.blank.rohansharma.collegecompanion.AppCommons.SIGN_IN;
import static com.blank.rohansharma.collegecompanion.AppCommons.STREAMS;
import static com.blank.rohansharma.collegecompanion.AppCommons.stream;

public class TimeTableActivity extends AppCompatActivity {

    int j = 1;
    TableLayout tableLayout;
    SQLiteDatabase timeTable;
    ProgressBar progressBar;
    TextView updated;
    boolean timeTableExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableLayout = (TableLayout) findViewById(R.id.main_table);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        updated = (TextView) findViewById(R.id.updated);

        File file = new File("/data/data/com.blank.rohansharma.collegecompanion/databases/" +
                "timetable");
        if (file.exists()) {
            timeTableExists = true;
            progressBar.setVisibility(View.GONE);
            SharedPreferences sharedPreferences = this.getSharedPreferences(SIGN_IN,
                    Context.MODE_PRIVATE);
            long temp = ((System.currentTimeMillis() - sharedPreferences.getLong("updated", 0))
                    / (1000 * 3600 * 24));
            if (temp == 0)
                updated.setText("Last synced: Today");
            else if (temp == 1)
                updated.setText("Last synced: " + temp + " day ago");
            else
                updated.setText("Last synced: " + temp + " days ago");

            TableRow tr_head = new TableRow(this);
            tr_head.setId(j++);
            tr_head.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            TextView dayHeader = new TextView(this);
            dayHeader.setId(j++);
            dayHeader.setText("Day");
            dayHeader.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dayHeader.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                dayHeader.setTextColor(Color.WHITE);
            }
            dayHeader.setPadding(16, 16, 16, 16);
            tr_head.addView(dayHeader);

            TextView p1Header = new TextView(this);
            p1Header.setId(j++);
            p1Header.setText("09:30 AM - 10:30 AM");
            p1Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p1Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p1Header.setTextColor(Color.WHITE);
            }
            p1Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p1Header);
            Log.d("j", String.valueOf(j));

            TextView p2Header = new TextView(this);
            p2Header.setId(j++);
            p2Header.setText("10:30 AM - 11:30 AM");
            p2Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p2Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p2Header.setTextColor(Color.WHITE);
            }
            p2Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p2Header);
            Log.d("j", String.valueOf(j));

            TextView p3Header = new TextView(this);
            p3Header.setId(j++);
            p3Header.setText("11:30 AM - 12:30 PM");
            p3Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p3Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p3Header.setTextColor(Color.WHITE);
            }
            p3Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p3Header);
            Log.d("j", String.valueOf(j));

            TextView p4Header = new TextView(this);
            p4Header.setId(j++);
            p4Header.setText("12:30 PM - 01:30 AM");
            p4Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p4Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p4Header.setTextColor(Color.WHITE);
            }
            p4Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p4Header);
            Log.d("j", String.valueOf(j));

            TextView p5Header = new TextView(this);
            p5Header.setId(j++);
            p5Header.setText("01:30 PM - 02:30 PM");
            p5Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p5Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p5Header.setTextColor(Color.WHITE);
            }
            p5Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p5Header);
            Log.d("j", String.valueOf(j));

            TextView p6Header = new TextView(this);
            p6Header.setId(j++);
            p6Header.setText("02:30 PM - 03:30 PM");
            p6Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p6Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p6Header.setTextColor(Color.WHITE);
            }
            p6Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p6Header);
            Log.d("j", String.valueOf(j));

            TextView p7Header = new TextView(this);
            p7Header.setId(j++);
            p7Header.setText("03:30 PM - 04:30 PM");
            p7Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p7Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p7Header.setTextColor(Color.WHITE);
            }
            p7Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p7Header);
            Log.d("j", String.valueOf(j));

            TextView p8Header = new TextView(this);
            p8Header.setId(j++);
            p8Header.setText("04:30 PM - 05:30 PM");
            p8Header.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                p8Header.setBackground(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.border6, null));
                p8Header.setTextColor(Color.WHITE);
            }
            p8Header.setPadding(16, 16, 16, 16);
            tr_head.addView(p8Header);
            Log.d("j", String.valueOf(j));

            tableLayout.addView(tr_head, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            for (int i = 0; i < 5; i++) {
                SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase("/data/data/" +
                                this.getPackageName() + "/databases/timetable", null,
                        SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM TimeTable WHERE STREAM='" +
                        STREAMS[stream] + "' AND DAY=" + i, null);
                cursor.moveToFirst();

                TableRow tr = new TableRow(this);
                tr.setId(j++);
                tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                TextView day = new TextView(this);
                day.setId(j++);
                day.setGravity(Gravity.CENTER_HORIZONTAL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    day.setBackground(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.border6, null));
                }
                day.setTextColor(Color.WHITE);
                day.setText(DAYS[i]);
                day.setPadding(16, 16, 16, 16);
                tr.addView(day);

                for (int k = 2; k < 10; k++) {
                    TextView period = new TextView(this);
                    period.setId(j++);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        period.setBackground(ResourcesCompat.getDrawable(
                                getResources(), R.drawable.border2, null));
                    }
                    period.setTextColor(Color.BLACK);
                    period.setText(cursor.getString(k));
                    period.setPadding(16, 16, 16, 16);
                    tr.addView(period);
                }
                cursor.close();
                tableLayout.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            getTimeTableData();
        } else {
            timeTableExists = false;

            getTimeTableData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (timeTableExists) {
            for (int i = 0; i < 8; i++) {
                TextView textView = (TextView) findViewById(PERIOD_IDS[i]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setBackground(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.border6, null));
                }
            }

            for (int i = 0; i < 5; i++) {
                TextView textView = (TextView) findViewById(DAY_IDS[i]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setBackground(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.border6, null));
                }
            }

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            String time = simpleDateFormat.format(calendar.getTime());

            Log.d("TAG", time);

            int temp1 = 0;

            for (int i = 9; i <= 16; i++) {
                if (i == 9) {
                    if (time.compareTo("09:30") >= 0 && time.compareTo((i + 1) + ":30") <= 0) {
                        TextView textView = (TextView) findViewById(PERIOD_IDS[i - 9]);
                        temp1 = PERIOD_IDS[i - 9];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            textView.setBackground(ResourcesCompat.getDrawable(
                                    getResources(), R.drawable.border3, null));
                            textView.setTextColor(Color.WHITE);
                        }
                    }
                }
                if (i >= 12) {
                    if (time.compareTo("0" + (i - 12) + ":30") >= 0 && time.compareTo("0" + (i - 12 + 1) + ":30") <= 0) {
                        TextView textView = (TextView) findViewById(PERIOD_IDS[i - 9]);
                        temp1 = PERIOD_IDS[i - 9];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            textView.setBackground(ResourcesCompat.getDrawable(
                                    getResources(), R.drawable.border3, null));
                            textView.setTextColor(Color.WHITE);
                        }
                    }
                } else if (time.compareTo(i + ":30") >= 0 && time.compareTo((i + 1) + ":30") <= 0) {
                    TextView textView = (TextView) findViewById(PERIOD_IDS[i - 9]);
                    temp1 = PERIOD_IDS[i - 9];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        textView.setBackground(ResourcesCompat.getDrawable(
                                getResources(), R.drawable.border3, null));
                        textView.setTextColor(Color.WHITE);
                    }
                }
            }

            int temp2 = 0;

            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < 5; i++) {
                if (day == i + 2) {
                    TextView textView = (TextView) findViewById(DAY_IDS[i]);
                    temp2 = DAY_IDS[i];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        textView.setBackground(ResourcesCompat.getDrawable(
                                getResources(), R.drawable.border3, null));
                        textView.setTextColor(Color.WHITE);
                    }
                }
            }

            if (temp1 >= 3 && temp1 <= 10 && temp2 >= 12 && temp2 <= 52) {
                TextView textView = (TextView) findViewById(temp2 + temp1 - 2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setBackground(ResourcesCompat.getDrawable(
                            getResources(), R.drawable.border3, null));
                    textView.setTextColor(Color.WHITE);
                }
            }
        }
    }

    void getTimeTableData() {
        String url = API_URL + "getTimeTable.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        timeTable = openOrCreateDatabase("timetable",
                                Context.MODE_PRIVATE, null);
                        timeTable.execSQL("CREATE TABLE IF NOT EXISTS TimeTable(" +
                                "STREAM VARCHAR(10),DAY INT,P1 VARCHAR(50)," +
                                "P2 VARCHAR(50),P3 VARCHAR(50),P4 VARCHAR(50)," +
                                "P5 VARCHAR(50),P6 VARCHAR(50),P7 VARCHAR(50)," +
                                "P8 VARCHAR(50));");
                        timeTable.execSQL("DELETE FROM TimeTable");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                timeTable.execSQL("INSERT INTO TimeTable VALUES('" +
                                        c.getString("stream") + "','" + c.getInt("day") + "','" +
                                        c.getString("p1") + "','" + c.getString("p2") + "','" +
                                        c.getString("p3") + "','" + c.getString("p4") + "','" +
                                        c.getString("p5") + "','" + c.getString("p6") + "','" +
                                        c.getString("p7") + "','" + c.getString("p8") + "');");
                            }
                            SharedPreferences.Editor editor = getSharedPreferences(
                                    "SignIn", Context.MODE_PRIVATE).edit();
                            editor.putLong("updated", System.currentTimeMillis());
                            editor.apply();
                            updated.setText("Last Synced: Today");
                            if (!timeTableExists) {
                                startActivity(getIntent());
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", "error");

                        Toast.makeText(TimeTableActivity.this, "Connection Problem! :(",
                                Toast.LENGTH_LONG).show();
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}