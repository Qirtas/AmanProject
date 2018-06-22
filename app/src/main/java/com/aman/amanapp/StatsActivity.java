package com.aman.amanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ListView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ListView listView = (ListView) findViewById(R.id.listView);
        DatabaseForApps database = new DatabaseForApps(this);
        ArrayList<AppItem> values = new ArrayList<>();
        database.open();
        values = database.getData();
        database.close();
        StatsAdapter statsAdapter = new StatsAdapter(this,values);
        listView.setAdapter(statsAdapter);
    }
}
