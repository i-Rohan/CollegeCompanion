package com.blank.rohansharma.collegecompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

public class CompleteFragment extends Fragment {
    int j = 1;
    TableLayout tableLayout;

    public CompleteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_complete, container, false);

        tableLayout = (TableLayout) v.findViewById(R.id.main_table);

        File file = new File("/data/data/com.blank.rohansharma.collegecompanion/databases/timetable");
        if (file.exists()) {
            //TODO
        } else {
            getTimeTableData();
        }

        return v;
    }

    void getTimeTableData() {
        String url = AppCommons.API_URL + "getTimeTable.php?stream=" + AppCommons.STREAMS[AppCommons.stream];

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", "error");
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TimeTableActivity.context);
        requestQueue.add(stringRequest);
    }
}
