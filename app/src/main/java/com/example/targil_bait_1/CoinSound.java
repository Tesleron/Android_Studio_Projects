package com.example.targil_bait_1;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class CoinSound extends AsyncTask<Void, Void, Void> {
    private Context context;
    public CoinSound (Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(context, R.raw.coin_sound);
        //player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();

        return null;
    }
}
