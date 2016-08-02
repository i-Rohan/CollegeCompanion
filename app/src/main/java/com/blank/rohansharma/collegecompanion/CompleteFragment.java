package com.blank.rohansharma.collegecompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import static com.blank.rohansharma.collegecompanion.AppCommons.API_URL;
import static com.blank.rohansharma.collegecompanion.AppCommons.SIGN_IN;

public class CompleteFragment extends Fragment {
    int j = 1;
    TableLayout tableLayout;
    SQLiteDatabase timeTable;
    ProgressBar progressBar;
    TextView updated;
    boolean timeTableExists;

    public CompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_complete, container, false);

        tableLayout = (TableLayout) v.findViewById(R.id.main_table);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        updated = (TextView) v.findViewById(R.id.updated);

        File file = new File("/data/data/com.blank.rohansharma.collegecompanion/databases/" +
                "timetable");
        if (file.exists()) {
            timeTableExists = true;
            progressBar.setVisibility(View.GONE);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SIGN_IN,
                    Context.MODE_PRIVATE);
            long temp = ((System.currentTimeMillis() - sharedPreferences.getLong("updated", 0))
                    / (1000 * 3600 * 24));
            if (temp == 0)
                updated.setText("Last synced: Today");
            else
                updated.setText("Last synced: " + temp + " days ago");

            TableRow tr_head = new TableRow(getActivity());
            tr_head.setId(j++);
            tr_head.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));


            TextView dayHeader = new TextView(getActivity());
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

            TextView p1Header = new TextView(getActivity());
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

            TextView p2Header = new TextView(getActivity());
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

            TextView p3Header = new TextView(getActivity());
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

            TextView p4Header = new TextView(getActivity());
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

            TextView p5Header = new TextView(getActivity());
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

            TextView p6Header = new TextView(getActivity());
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

            TextView p7Header = new TextView(getActivity());
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

            TextView p8Header = new TextView(getActivity());
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

            tableLayout.addView(tr_head, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            getTimeTableData();
        } else {
            timeTableExists = false;

            getTimeTableData();
        }

        return v;
    }

    void getTimeTableData() {
        String url = API_URL + "getTimeTable.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        timeTable = getActivity().openOrCreateDatabase("timetable",
                                Context.MODE_PRIVATE, null);
                        timeTable.execSQL("CREATE TABLE IF NOT EXISTS TimeTable(" +
                                "STREAM VARCHAR(10),DAY INT,P1 VARCHAR(50)," +
                                "P2 VARCHAR(50),P3 VARCHAR(50),P4 VARCHAR(50)," +
                                "P5 VARCHAR(50),P6 VARCHAR(50),P7 VARCHAR(50)," +
                                "P8 VARCHAR(50));");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            Log.d("Length", String.valueOf(jsonArray.length()));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject c = jsonArray.getJSONObject(i);
                                timeTable.execSQL("INSERT INTO TimeTable VALUES('" +
                                        c.getString("stream") + "','" + c.getInt("day") + "','" +
                                        c.getString("p1") + "','" + c.getString("p2") + "','" +
                                        c.getString("p3") + "','" + c.getString("p4") + "','" +
                                        c.getString("p5") + "','" + c.getString("p6") + "','" +
                                        c.getString("p7") + "','" + c.getString("p8") + "');");
                            }
//                            Calendar c = Calendar.getInstance();
//                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                            String formattedDate = df.format(c.getTime());
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(
                                    "SignIn", Context.MODE_PRIVATE).edit();
                            editor.putLong("updated", System.currentTimeMillis());
                            editor.apply();
                            updated.setText("Last Synced: Today");
                            if (!timeTableExists) {
                                startActivity(getActivity().getIntent());
                                getActivity().finish();
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

                        Toast.makeText(getActivity(), "Connection Problem! :(", Toast.LENGTH_LONG)
                                .show();
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}