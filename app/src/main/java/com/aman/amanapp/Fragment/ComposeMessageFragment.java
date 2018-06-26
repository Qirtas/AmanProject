package com.aman.amanapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman.amanapp.R;
import com.aman.amanapp.Utils;

public class ComposeMessageFragment extends Fragment {
    public ComposeMessageFragment() {
        // Required empty public constructor
    }

    public static ComposeMessageFragment newInstance() {
        Log.v(Utils.TAG, "ComposeMessageFragment newInstance");
        return new ComposeMessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(Utils.TAG, "MessagesFragment onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(Utils.TAG, "MessagesFragment onCreateView");

        View view = inflater.inflate(R.layout.fragment_compose_message, container, false);
        return  view;
    }
}