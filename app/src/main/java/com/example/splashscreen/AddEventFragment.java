package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddEventFragment extends Fragment {

    private EditText eventName, venue, eventDate1, eventDate2, eventTime, description;
    private AutoCompleteTextView hostClub;
    private int year, month, day, hours, minutes;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    public AddEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_event, container, false);

        final Calendar calendar = Calendar.getInstance();

        Button loginButton = rootView.findViewById(R.id.login_button);
        description = rootView.findViewById(R.id.description);
        eventName = rootView.findViewById(R.id.event_name);
        hostClub = rootView.findViewById(R.id.host_club);
        venue = rootView.findViewById(R.id.venue);
        eventDate1 = rootView.findViewById(R.id.event_date1);
        eventDate2 = rootView.findViewById(R.id.event_date2);
        eventTime = rootView.findViewById(R.id.event_time);
        final String user = ((HomeActivity)getActivity()).currentUser.getEmail();

        String[] clubs = getActivity().getApplicationContext().getResources().getStringArray(R.array.clubs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, clubs);
        hostClub = (AutoCompleteTextView)(rootView.findViewById(R.id.host_club));
        hostClub.setAdapter(adapter);

        eventDate1.setOnClickListener(view -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    eventDate1.setText(i2+"/"+(i1+1)+"/"+i);
                }
            }, year, month, day);
            datePickerDialog.show();
        });

        eventDate2.setOnClickListener(view -> {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog1 = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i3, int i4, int i5) {
                    eventDate2.setText(i5+"/"+(i4+1)+"/"+i3);
                }
            }, year, month, day);
            datePickerDialog1.show();
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
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
                        eventTime.setText(time);
                    }
                },hours,minutes,true);
                timePickerDialog.show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textDescription = description.getText().toString();
                String textEventName = eventName.getText().toString();
                String textEventDate1 = eventDate1.getText().toString();
                String textEventDate2 = eventDate2.getText().toString();
                String textHostClub = hostClub.getText().toString();
                String textVenue = venue.getText().toString();
                String textEventTime = eventTime.getText().toString();

                if (TextUtils.isEmpty(textEventName)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "Event Name can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textHostClub)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "Host Club field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textVenue)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "Venue can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textEventDate1)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "From can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textEventDate2)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "To can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textEventTime)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "Event Time can't be empty!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textDescription)) {
                    Toast.makeText(AddEventFragment.this.getActivity(), "Description field can't be empty!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AddEventFragment.this.getActivity(), "Successfully registered event!", Toast.LENGTH_LONG).show();
                    EventHelper ticket = new EventHelper(user, textDescription, textEventDate2, textEventDate1, textEventName, textEventTime, textHostClub,
                            textVenue);

                    //Add event to database
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("events");
                    reference.child(removeSpecialChars(textEventName)).setValue(ticket);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    //remove special chars
    private String removeSpecialChars(String str)
    {
        int l = str.length();
        String finalStr = "";
        for(char c : str.toCharArray()){
            if(Character.isLetterOrDigit(c)|| c==32)
                finalStr+=c;
        }
        return finalStr;
    }
}