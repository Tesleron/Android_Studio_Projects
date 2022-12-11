package com.example.targil_bait_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class Activity_Menu extends AppCompatActivity {
    private AppCompatImageView space_IMG_background;
    private MaterialButton menu_BTN_2btnslw;
    private MaterialButton menu_BTN_2btnfst;
    private MaterialButton menu_BTN_snsr;
    private MaterialButton menu_BTN_topten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        initButtons();

        Glide
                .with(this)
                .load(R.drawable.starfield6)
                .into(space_IMG_background);

    }

    private void initButtons()
    {
        menu_BTN_2btnslw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(1000, false);
            }
        });

        menu_BTN_2btnfst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(500, false);
            }
        });

        menu_BTN_snsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(1000, true);
            }
        });

        menu_BTN_topten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openTopTen();
            }
        });
    }

    private void startGame(int delay, boolean isSensorMode)
    {
        Intent intent = new Intent(this, Activity_Game.class);
        intent.putExtra(Activity_Game.KEY_DELAY,delay);
        intent.putExtra(Activity_Game.KEY_MODE,isSensorMode);
        startActivity(intent);
    }

    private void findViews()
    {
        space_IMG_background = findViewById(R.id.space_IMG_background);

        menu_BTN_2btnslw = findViewById(R.id.menu_BTN_2btnslw);
        menu_BTN_2btnfst = findViewById(R.id.menu_BTN_2btnfst);
        menu_BTN_snsr = findViewById(R.id.menu_BTN_snsr);
        menu_BTN_topten = findViewById(R.id.menu_BTN_topten);
    }


}