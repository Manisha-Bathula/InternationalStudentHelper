package com.gbcish.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.internationalstudenthelper.R;

public class HelpFragment extends Fragment {

    private TextView textView;

    public HelpFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        textView = view.findViewById(R.id.text_displayhelp);
        String message = getArguments().getString("message");
        textView.setText(message);
        return view;
    }
}