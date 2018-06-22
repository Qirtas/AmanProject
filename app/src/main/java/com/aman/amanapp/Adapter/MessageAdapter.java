package com.aman.amanapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aman.amanapp.Model.MessageModel;
import com.aman.amanapp.R;

import java.util.List;

/**
 * Created by Qirtas.Malik on 6/20/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>
{
    private List<MessageModel> messagesListList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, text, time;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            text = (TextView) view.findViewById(R.id.tv_text);
            time = (TextView) view.findViewById(R.id.tv_time);
        }
    }

    public MessageAdapter(List<MessageModel> moviesList) {
        this.messagesListList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message , parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        MessageModel message = messagesListList.get(position);
        holder.title.setText(message.getTitle());
        holder.text.setText(message.getText());
        holder.time.setText(message.getTime());

    }

    @Override
    public int getItemCount() {
        return messagesListList.size();
    }
}
