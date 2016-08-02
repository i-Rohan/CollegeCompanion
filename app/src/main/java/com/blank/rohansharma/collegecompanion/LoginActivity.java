package com.blank.rohansharma.collegecompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

import static com.blank.rohansharma.collegecompanion.AppCommons.SIGNED_IN;
import static com.blank.rohansharma.collegecompanion.AppCommons.SIGN_IN;
import static com.blank.rohansharma.collegecompanion.AppCommons.STREAM;
import static com.blank.rohansharma.collegecompanion.AppCommons.STREAMS;
import static com.blank.rohansharma.collegecompanion.AppCommons.stream;

public class LoginActivity extends Activity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        spinner = (Spinner) findViewById(R.id.spinner);

        SharedPreferences sharedPreferences = getSharedPreferences(SIGN_IN, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SIGNED_IN, false)) {
            stream = sharedPreferences.getInt(STREAM, 0);
            startActivity(new Intent(this, TimeTableActivity.class));
            finish();
            return;
        }

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, STREAMS);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void onClickContinue(View v) {
        SharedPreferences.Editor editor = getSharedPreferences(SIGN_IN, MODE_PRIVATE).edit();
        editor.putBoolean(SIGNED_IN, true);
        editor.putInt(STREAM, spinner.getSelectedItemPosition());
        editor.apply();

        startActivity(new Intent(this, TimeTableActivity.class));
        finish();
    }
}