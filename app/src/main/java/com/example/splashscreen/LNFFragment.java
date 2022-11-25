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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class LNFFragment extends Fragment {

    private EditText itemLost, lostDate , lastSeen, description;
    private int year,month,day;

    private RadioGroup radioLnF;

    public LNFFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_l_n_f, container, false);

        final Calendar calendar = Calendar.getInstance();

        Button login_button = view.findViewById(R.id.login_button);
        description = view.findViewById(R.id.description);
        itemLost = view.findViewById(R.id.item_lost);
        lostDate = view.findViewById(R.id.lost_date);
        lastSeen = view.findViewById(R.id.last_seen);
        radioLnF = view.findViewById(R.id.radio_group_lnf);
        final int lostId = (R.id.lost_radio);
        final int foundId = (R.id.found_radio);
        final String user = ((HomeActivity)getActivity()).currentUser.getEmail();

        lostDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(container.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        lostDate.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textDescription = description.getText().toString();
                String textItemLost = itemLost.getText().toString();
                String textLostDate = lostDate.getText().toString();
                String textLastSeen = lastSeen.getText().toString();
                int selectedId = radioLnF.getCheckedRadioButtonId();
                String infoType = "";
                if(selectedId == -1){
                    Toast.makeText(getActivity(), "Choose info type!", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(textItemLost)) {
                    Toast.makeText(getActivity(), "Item field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(textLostDate)){
                    Toast.makeText(getActivity(), "Date field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(textLastSeen)){
                    Toast.makeText(getActivity(), "Last Seen field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(textDescription)) {
                    Toast.makeText(getActivity(), "Description field can't be empty!", Toast.LENGTH_SHORT).show();
                } else{
                    if(selectedId == lostId) infoType = "lost";
                    else if(selectedId == foundId) infoType = "found";
                    Toast.makeText(getActivity(), "Successfully registered ".concat(infoType).concat(" item!")
                            , Toast.LENGTH_LONG).show();
                    LostAndFoundHelper ticket = new LostAndFoundHelper(textItemLost, infoType, textLostDate,
                            textLastSeen, textDescription, user);
                    // TODO: 25-11-2022 put this ticket in LNF database
                }
            }
        });

        return view;
    }
}