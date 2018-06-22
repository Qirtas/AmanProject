package com.aman.amanapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by aman on 11/19/2016.
 */

public class StatsAdapter  extends BaseAdapter {
    Context context;
    ArrayList<AppItem> values;
    LayoutInflater inflater;

    public StatsAdapter(Context context,ArrayList<AppItem> values)
    {
        this.context = context;
        this.values = values;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_item_stats,parent,false);
        }
        TextView appName = (TextView) convertView.findViewById(R.id.app_name);
        TextView appPackageName = (TextView) convertView.findViewById(R.id.app_package_name);
        TextView timesAccessed = (TextView) convertView.findViewById(R.id.access_times);
        ImageView icon = (ImageView) convertView.findViewById(R.id.list_image);
    appName.setText(values.get(position).getName());
        appPackageName.setText(values.get(position).getPacakgeName());
        timesAccessed.setText(values.get(position).getAccessTimes());
        icon.setImageDrawable(values.get(position).getIcon());

        return convertView;
    }

}

