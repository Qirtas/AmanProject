package com.aman.amanapp;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter{
OnLongClickListener mListener;
    Context context;
    ArrayList<AppItem> values;
    public String TAG = "AMANAPP";
    MainActivity mainActivity;

    private static LayoutInflater inflater=null;
    public GridAdapter(Context mainActivity, ArrayList<AppItem> values , MainActivity mainn) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListener = (OnLongClickListener) context;
        this.values = new ArrayList<>(values);
        mainActivity = mainn;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_item, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.texts);
        holder.os_img =(ImageView) rowView.findViewById(R.id.image);

        holder.os_text.setText(values.get(position).getName());
        holder.os_img.setImageDrawable(values.get(position).getIcon());

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Log.v(TAG , "Clicked: " + values.get(position).getName());

                if(values.get(position).getName().equalsIgnoreCase("Messages"))
                {
                   // mainActivity.addFragment(MessagesFragment.newInstance(), true);
                    ((MainActivity)context).addFragment(MessagesFragment.newInstance(), true);

                }
                else
                {
                    Toast.makeText(context, "You Clicked "+values.get(position).getName(), Toast.LENGTH_SHORT).show();
                    try {
                        DatabaseForApps database = new DatabaseForApps(context);
                        database.open();
                        database.insertRecord(values.get(position));
                        database.close();
                        Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(values.get(position).getPacakgeName());
                        context.startActivity(LaunchIntent);
                    }
                    catch(Exception ex)
                    {
                        Toast.makeText(context,ex.toString(),Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onLongClicked(position);
                return false;
            }
        });

        return rowView;
    }
    public interface OnLongClickListener
    {
        public void onLongClicked(int index);
    }

}