package com.aman.amanapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman.amanapp.Adapter.MessageAdapter;
import com.aman.amanapp.Model.MessageModel;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {

    public static String TAG = "AMANAPP";
    private List<MessageModel> messagesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static MessagesFragment newInstance()
    {
        Log.v(TAG , "MessagesFragment newInstance");
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG , "MessagesFragment onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG , "MessagesFragment onCreateView");

        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new MessageAdapter(messagesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMessagesData();

        return view;
    }


    private void prepareMessagesData()
    {
        MessageModel movie = new MessageModel("Qirtas Malik", "Hello! how are you?", "03:44 AM");
        messagesList.add(movie);
        movie = new MessageModel("Aman SPS", "Coming. On my way.", "07:34 PM");
        messagesList.add(movie);
        movie = new MessageModel("Sir Adnan", "Assalamo alikum", "10:12 PM");
        messagesList.add(movie);movie = new MessageModel("Umer", "Assalamo alikum", "08:31 PM");
        messagesList.add(movie);movie = new MessageModel("Ali", "Hello! how are you?", "05:22 PM");
        messagesList.add(movie);movie = new MessageModel("Adeel", "Assalamo alikum", "11:23 AM");
        messagesList.add(movie);movie = new MessageModel("Hamza", "Hello! how are you?", "12:03 AM");
        messagesList.add(movie);
    }

}
