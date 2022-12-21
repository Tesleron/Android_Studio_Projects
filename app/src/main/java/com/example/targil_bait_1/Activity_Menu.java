package com.example.targil_bait_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.example.targil_bait_1.utils.MySignal;
import com.google.android.material.button.MaterialButton;

public class Activity_Menu extends AppCompatActivity {
    private AppCompatImageView space_IMG_background;
    private MaterialButton menu_BTN_2btnslw;
    private MaterialButton menu_BTN_2btnfst;
    private MaterialButton menu_BTN_snsr;
    private MaterialButton menu_BTN_topten;
    private EditText menu_EDT_inputname;


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
                openTopTen();
            }
        });



        menu_EDT_inputname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final boolean isEmpty = charSequence.toString().length() != 0;
                setButtons(isEmpty);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void startGame(int delay, boolean isSensorMode)
    {
        if (menu_EDT_inputname.getText().length() != 0)
        {
            Intent intent = new Intent(this, Activity_Game.class);
            intent.putExtra(Activity_Game.KEY_DELAY, delay);
            intent.putExtra(Activity_Game.KEY_MODE, isSensorMode);
            intent.putExtra(Activity_Game.KEY_NAME, menu_EDT_inputname.getText().toString());
            startActivity(intent);
        }
        else
        {
            MySignal.getInstance().toastLong("Player name cannot be empty");
        }
    }

    private void openTopTen()
    {
        Intent intent = new Intent(this, Activity_TopTen.class);
        startActivity(intent);
    }

    private void findViews()
    {
        space_IMG_background = findViewById(R.id.space_IMG_background);

        menu_BTN_2btnslw = findViewById(R.id.menu_BTN_2btnslw);
        menu_BTN_2btnfst = findViewById(R.id.menu_BTN_2btnfst);
        menu_BTN_snsr = findViewById(R.id.menu_BTN_snsr);
        menu_BTN_topten = findViewById(R.id.menu_BTN_topten);

        menu_EDT_inputname = findViewById(R.id.menu_EDT_inputname);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtons(false);
    }

    private void setButtons(boolean isEnabled)
    {
        if (isEnabled == false)
        {
            menu_BTN_2btnfst.setAlpha(.5f);
            menu_BTN_2btnslw.setAlpha(.5f);
            menu_BTN_snsr.setAlpha(.5f);
        }
        else
        {
            menu_BTN_2btnfst.setAlpha(1);
            menu_BTN_2btnslw.setAlpha(1);
            menu_BTN_snsr.setAlpha(1);
        }

        menu_BTN_2btnfst.setClickable(isEnabled);
        menu_BTN_2btnslw.setClickable(isEnabled);
        menu_BTN_snsr.setClickable(isEnabled);

    }


}