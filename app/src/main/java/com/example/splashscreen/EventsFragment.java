package com.example.splashscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventsFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private ArrayList<EventHelper> events = new ArrayList<>();

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    EventHelper event = snapshot1.getValue(EventHelper.class);
                    events.add(event);
                }

                expandableListDetail = ExpandableListDataPump.getData(events);
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                expandableListTitle.get(groupPosition), Toast.LENGTH_SHORT).show();
                    }
                });

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.
                                        get(expandableListTitle.get(groupPosition)).get(childPosition),
                                Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}