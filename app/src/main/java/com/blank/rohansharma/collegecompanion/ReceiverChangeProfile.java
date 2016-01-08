package com.blank.rohansharma.collegecompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

public class ReceiverChangeProfile extends BroadcastReceiver {
    public ReceiverChangeProfile() {}

    @Override
    public void onReceive(Context context, Intent intent)
    {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        Log.d("abc2", String.valueOf(am.getRingerMode()));
        int a=am.getRingerMode();
        switch (a) {
            case 1:
                changeProfile(context, 1);
                break;
            case 2:
                changeProfile(context, 0);
                break;
        }
    }

    public void changeProfile(Context context,int n)
    {
        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (n)
        {
            case 0:
                Log.i("Sound Profile", "Vibrate Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                break;
            case 1:
                Log.i("Sound Profile", "Normal Mode");
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
                //am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 2, 0);
        }
    }
}