package com.example.targil_bait_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.targil_bait_1.Fragments.Fragment_List;
import com.example.targil_bait_1.Fragments.Fragment_Map;
import com.example.targil_bait_1.Interfaces.CallBack_UserRegistrationToList;
import com.google.android.material.button.MaterialButton;

public class Activity_TopTen extends AppCompatActivity {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;
    private AppCompatImageView space_IMG_background;
    private MaterialButton panel_BTN_menu;

    CallBack_UserRegistrationToList callBack_userRegistrationToList = new CallBack_UserRegistrationToList() {

        @Override
        public void setMapLocation(double lat, double lon, String namePlayer) {
            fragment_map.setMapLocation(lat, lon,namePlayer);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        findViews();

        Glide
                .with(this)
                .load(R.drawable.starfield6)
                .into(space_IMG_background);

        fragment_list = new Fragment_List();
        fragment_map = new Fragment_Map();
        fragment_list.setCallBack_UserRegistrationToList(callBack_userRegistrationToList);

        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_list, fragment_list).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.panel_LAY_map, fragment_map).commit();
    }

    private void findViews()
    {
        space_IMG_background = findViewById(R.id.space_IMG_background);
        panel_BTN_menu = findViewById(R.id.panel_BTN_menu);
        panel_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}