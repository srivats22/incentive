package com.srivats.incentive.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srivats.incentive.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView dateTextView;
    FloatingActionButton fab;
    String[] monthArray = {"January", "February", "March" ,
    "April", "May", "June", "July", "August", "September",
    "October", "November", "December"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Calendar c = Calendar.getInstance();
        int mon = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String month = monthArray[mon + 1];
        dateTextView = root.findViewById(R.id.dateTv);
        dateTextView.setText(month + ", " + date);

        fab = root.findViewById(R.id.createTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });



        return root;
    }
}