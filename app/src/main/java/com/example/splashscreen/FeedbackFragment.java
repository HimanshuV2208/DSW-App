package com.example.splashscreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends Fragment {

    private Button login_button;
    private EditText subject, description;
    private RadioGroup radioFeedbackOrComplaint;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        login_button = view.findViewById(R.id.login_button);
        description = view.findViewById(R.id.description);
        subject = view.findViewById(R.id.subject);
        radioFeedbackOrComplaint = view.findViewById(R.id.radio_group_feedback);
        final int complaintId = (R.id.complaint_radio);
        final int feedbackId = (R.id.feedback_radio);
        final String user = ((HomeActivity)getActivity()).currentUser.getEmail();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textDescription=description.getText().toString();
                String textSubject=subject.getText().toString();
                int selectedId = radioFeedbackOrComplaint.getCheckedRadioButtonId();
                String contentType = "";

                //check type of request either complaint or feedback
                if(selectedId == -1) {
                    Toast.makeText(getActivity(), "Choose Feedback or Complaint!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(textSubject)) {
                    Toast.makeText(getActivity(), "Subject field can't be empty!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(textDescription)) {
                    Toast.makeText(getActivity(), "Description field can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    if(selectedId == complaintId) contentType = "complaint";
                    else if(selectedId == feedbackId) contentType = "feedback";
                    Toast.makeText(getActivity(), "Successfully registered ".concat(contentType).concat("!")
                            , Toast.LENGTH_LONG).show();
                    FeedbackHelper ticket = new FeedbackHelper(textSubject, contentType, textDescription, user);

                    //Add event to database
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference(contentType);
                    reference.child(removeSpecialChars(user)).setValue(ticket);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //remove @mnit.ac.in and special chars
    private String removeSpecialChars(String str)
    {
        int l = str.length();
        String nstr=str.substring(0, l-11).toLowerCase(), finalStr = "";
        for(char c : nstr.toCharArray()){
            if(Character.isLetterOrDigit(c))
                finalStr+=c;
        }
        return finalStr;
    }
}