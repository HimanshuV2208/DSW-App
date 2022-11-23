package com.example.splashscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class LNFFragment extends Fragment {

    private Button login_button;
    private EditText item_lost,lost_date,last_seen,description;
    private int year,month,day;

    public LNFFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_l_n_f, container, false);

        final Calendar calendar = Calendar.getInstance();

        login_button = view.findViewById(R.id.login_button);
        description = view.findViewById(R.id.description);
        item_lost = view.findViewById(R.id.item_lost);
        lost_date = view.findViewById(R.id.lost_date);
        last_seen = view.findViewById(R.id.last_seen);

        lost_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        lost_date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text_description=description.getText().toString();
                String text_item_lost=item_lost.getText().toString();
                String text_lost_date=lost_date.getText().toString();
                String text_last_seen=last_seen.getText().toString();

                if (TextUtils.isEmpty(text_item_lost)) {
                    Toast.makeText(getActivity(), "Item Lost field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(text_lost_date)){
                    Toast.makeText(getActivity(), "Lost Date field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(text_last_seen)){
                    Toast.makeText(getActivity(), "Last Seen field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(text_description)) {
                    Toast.makeText(getActivity(), "Description field can't be empty!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}