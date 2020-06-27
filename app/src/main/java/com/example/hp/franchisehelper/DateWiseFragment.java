package com.example.hp.franchisehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class DateWiseFragment extends Fragment {
    String Sname;
    TableLayout tb;
    TableRow tr;
    TextView companyTV, valueTV;
    ListView dates;
    ArrayList<String> datelist;
    ArrayAdapter<String> shopAdap;
    FirebaseAuth mauth;
    DatabaseReference dbRef;
    static String mainReportId1, mainReportDate2;
    String id;
    //String mainReportId;
    Bundle bundle;

    public DateWiseFragment() {
        // Required empty public constructor
        bundle = new Bundle();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_wise, container, false);


        dbRef = FirebaseDatabase.getInstance().getReference("pdate");

        dates = view.findViewById(R.id.dateList);
        Sname = getArguments().getString("shopName");


        datelist = new ArrayList<String>();
        shopAdap = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, datelist);
        dates.setAdapter(shopAdap);

        //  String mainReportDate;
        //Listener on the listview

        dates.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bund = new Bundle();
                bund.putString("date", datelist.get(position));
                bund.putString("sname", Sname);

                Report report=new Report();
                report.setArguments(bund);
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_screen,report).commit();
                transaction.addToBackStack(null);


            }
        });


        // Display Data To Listview From Firebase//


        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.child("dates").getValue(String.class);
                //datas.setText(data);
                datelist.add(data);
                shopAdap.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                shopAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                shopAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                shopAdap.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                shopAdap.notifyDataSetChanged();
            }
        });
        return view;
    }
}