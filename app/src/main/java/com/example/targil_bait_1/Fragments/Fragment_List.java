package com.example.targil_bait_1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.targil_bait_1.DataBase.DataBase;
import com.example.targil_bait_1.Interfaces.CallBack_UserRegistrationToList;
import com.example.targil_bait_1.R;
import com.example.targil_bait_1.DataBase.User;
import com.example.targil_bait_1.utils.MySPV;
import com.google.gson.Gson;

public class Fragment_List extends Fragment {

    private ListView listView;
    private DataBase allRecords;
    private CallBack_UserRegistrationToList callBack_list;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                double lat = allRecords.getResults().get(position).getLat();
                double lon = allRecords.getResults().get(position).getLon();
                String namePlayer = allRecords.getResults().get(position).getName();
                callBack_list.setMapLocation(lat,lon,namePlayer);
            }
        });

        return view;
    }

    private void findViews(View view)
    {
        listView = view.findViewById(R.id.fargment_LST_list);
    }

    private void initViews()
    {
        allRecords = new Gson().fromJson(MySPV.getInstance().getStrSP("records", ""), DataBase.class);
        if (allRecords != null) {
            ArrayAdapter<User> adapter = new ArrayAdapter<User>(getActivity(), R.layout.list_item, allRecords.getResults());
            listView.setAdapter(adapter);

        }
    }

    public void setCallBack_UserRegistrationToList(CallBack_UserRegistrationToList callBack_list){
        this.callBack_list = callBack_list;
    }

}

