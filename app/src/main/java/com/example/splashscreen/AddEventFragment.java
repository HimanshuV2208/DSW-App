package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddEventFragment extends Fragment {

    private Button login_button;
    private EditText event_name,host_club,venue,event_date1,event_date2,event_time,description;

    private int year,month,day,hours,minutes;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_event, container, false);

        final Calendar calendar = Calendar.getInstance();

        login_button = rootView.findViewById(R.id.login_button);
        description = rootView.findViewById(R.id.description);
        event_name = rootView.findViewById(R.id.event_name);
        host_club = rootView.findViewById(R.id.host_club);
        venue = rootView.findViewById(R.id.venue);
        event_date1 = rootView.findViewById(R.id.event_date1);
        event_date2 = rootView.findViewById(R.id.event_date2);
        event_time = rootView.findViewById(R.id.event_time);

        event_date1.setOnClickListener(view -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    event_date1.setText(i2+"/"+(i1+1)+"/"+i);
                }
            }, year, month, day);
            datePickerDialog.show();
        });

        event_date2.setOnClickListener(view -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog1 = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i3, int i4, int i5) {
                    event_date2.setText(i5+"/"+(i4+1)+"/"+i3);
                }
            }, year, month, day);
            datePickerDialog1.show();
        });

        event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hours = calendar.get(Calendar.HOUR_OF_DAY);
                minutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(container.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int p, int q) {
                        calendar.set(Calendar.HOUR_OF_DAY,p);
                        calendar.set(Calendar.MINUTE,q);
                        calendar.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format= new SimpleDateFormat("k:mm");
                        String time= format.format(calendar.getTime());
                        event_time.setText(time);
                    }
                },hours,minutes,true);
                timePickerDialog.show();
            }
        });



        login_button.setOnClickListener(view -> {

            String text_description=description.getText().toString();
            String text_event_name=event_name.getText().toString();
            String text_event_date1=event_date1.getText().toString();
            String text_event_date2=event_date2.getText().toString();
            String text_host_club=host_club.getText().toString();
            String text_venue=venue.getText().toString();
            String text_event_time=event_time.getText().toString();

            if (TextUtils.isEmpty(text_event_name)) {
                Toast.makeText(getActivity(), "Event Name can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_host_club)){
                Toast.makeText(getActivity(), "Host Club field can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_venue)){
                Toast.makeText(getActivity(), "Venue can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_event_date1)){
                Toast.makeText(getActivity(), "From can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_event_date2)){
                Toast.makeText(getActivity(), "To can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_event_time)){
                Toast.makeText(getActivity(), "Event Time can't be empty!", Toast.LENGTH_SHORT).show();
            } else if(TextUtils.isEmpty(text_description)) {
                Toast.makeText(getActivity(), "Description field can't be empty!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Successfully registered", Toast.LENGTH_LONG).show();

        });
        // Inflate the layout for this fragment
        return rootView;
    }
}