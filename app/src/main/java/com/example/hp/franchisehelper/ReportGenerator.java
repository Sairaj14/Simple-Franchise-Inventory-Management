package com.example.hp.franchisehelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportGenerator extends Fragment {

    ListView list;
    EditText datas;
    DatabaseReference dbRef;
    ArrayList<String> shops;
    ArrayAdapter<String> shopAdap;
    Bundle shopId;
    public ReportGenerator() {
        // Required empty public constructor


    }

    @Override
    public void onStart() {
        super.onStart();
        dbRef.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_report_generator, container, false);




        dbRef=FirebaseDatabase.getInstance().getReference("shop");
        list=view.findViewById(R.id.listview);
        shopId=new Bundle();
        shops=new ArrayList<String>();
        shopAdap=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,shops);
        list.setAdapter(shopAdap);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String shopname=((TextView)view).getText().toString();
                Toast.makeText(getContext(), "Shop ="+shopname, Toast.LENGTH_SHORT).show();
                DateWiseFragment someFragment = new DateWiseFragment();
                shopId.putString("shopName",shopname);
                someFragment.setArguments(shopId);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //  transaction.hide(ReportGenerator.this);
                //ReportGenerator rp=new ReportGenerator();
                //transaction.show(someFragment);
                transaction.replace(R.id.main_screen, someFragment,someFragment.getTag() ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });





        // Display Data To Listview From Firebase//


        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data=dataSnapshot.child("shopName").getValue(String.class);

                //datas.setText(data);
                shops.add(data);
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
