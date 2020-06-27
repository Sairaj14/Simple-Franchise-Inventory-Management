package com.example.hp.franchisehelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */


interface FirebaseCallback2 {
    void onCallback2(String value);
}
public class Daily_Entry extends Fragment {

    Spinner itemOfShop,unit_1,unit_2,unit_3;
    EditText alelaa,viklelaa,urlelaa;
    Button submission,showItem,calc;
    FirebaseUser users;
    DatabaseReference dbRef;
    ArrayAdapter adap,adap1,adap2,adap3;
    String recievedValue,soldValue,leftoverValue;
    String id,recieved,leftover,sold,item,shop_Name,price,Mprice;
    ArrayList<String> list;
    int leftovers,solds,recieve;
    String units[]={"kg","gm","no/packets"};
    public Daily_Entry() {
        // Required empty public constructor
        list=new ArrayList<String>();
        price=" ";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_daily_entry, container, false);


        itemOfShop=view.findViewById(R.id.items);
        alelaa=view.findViewById(R.id.ajalela);
        viklelaa=view.findViewById(R.id.viklela);
        urlelaa=view.findViewById(R.id.urlela);
        submission=view.findViewById(R.id.submit);
        showItem=view.findViewById(R.id.show);
        unit_1=view.findViewById(R.id.unit1);
        unit_2=view.findViewById(R.id.unit2);
        unit_3=view.findViewById(R.id.unit3);
        calc=view.findViewById(R.id.calculate_btn);

        alelaa.setEnabled(false);
        viklelaa.setEnabled(false);
        urlelaa.setEnabled(false);

        dbRef=FirebaseDatabase.getInstance().getReference("Items");
        //  db12=FirebaseDatabase.getInstance().getReference("shop");

        adap=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,list);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemOfShop.setAdapter(adap);
        adap1=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,units);
        adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_1.setAdapter(adap1);
        adap2=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,units);
        adap2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_2.setAdapter(adap2);
        adap3=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,units);
        adap3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit_3.setAdapter(adap3);

        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItem.setEnabled(false);
                alelaa.setEnabled(true);
                viklelaa.setEnabled(true);
                urlelaa.setEnabled(false);

                if(isOnline()) {


                    dbRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String data = dataSnapshot.child("name").getValue(String.class);
                            list.add(data);
                            adap.notifyDataSetChanged();

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            adap.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                            adap.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            adap.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            adap.notifyDataSetChanged();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }


        });


        //
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recieveUnits=unit_1.getSelectedItem().toString();
                String leftoverUnits=unit_2.getSelectedItem().toString();
                String soldUnits= unit_3.getSelectedItem().toString();

                recieved=alelaa.getText().toString();
                int re =Integer.parseInt(recieved);
                //recieve=Integer.parseInt(recieved);
                sold=viklelaa.getText().toString();
                int sld=Integer.parseInt(sold);
                int left=re-sld;
                leftover=String.valueOf(left);
                urlelaa.setText(leftover);
                recievedValue=recieved+recieveUnits;
                soldValue=sold+soldUnits;
                leftoverValue=leftover+leftoverUnits;

            }
        });

        submission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showItem.setEnabled(true);

                users=FirebaseAuth.getInstance().getCurrentUser();
                id=users.getUid();
                shop_Name=users.getEmail();
                int index=shop_Name.indexOf("@");
                shop_Name=shop_Name.substring(0,index);
                Toast.makeText(getContext(),""+shop_Name,Toast.LENGTH_LONG).show();
                if(itemOfShop.getSelectedItem()==null)
                {
                    showItem.setError("Please Select An Item");
                }
                else																		
                {
                    item = itemOfShop.getSelectedItem().toString();
                }




                if(id!=null)
                {
                    if(!TextUtils.isEmpty(recieved))
                    {

                        if(!TextUtils.isEmpty(sold))
                        {
                            if(!TextUtils.isEmpty(leftover))
                            {
                                readData(new FirebaseCallback2() {
                                    @Override
                                    public void onCallback2(String value) {
                                        //Mprice=value;
                                        addition(value);

                                    }
                                });
                            }
                            else
                            {
                                urlelaa.setError("Field cannot be empty");
                            }
                        }
                        else
                        {
                            viklelaa.setError("Field cannot be empty");
                        }

                    }
                    else
                    {
                        alelaa.setError("Field cannot be empty");
                    }

                }
                else {
                    Toast.makeText(getContext(),"Contact Support through email",Toast.LENGTH_LONG).show();
                }



            }
        });


        return view;
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;

        }



    }
    public void addition(String Mprice)
    {
        final DatabaseReference db=FirebaseDatabase.getInstance().getReference("shop");
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        final Item_Daily_Entry ide=new Item_Daily_Entry(item,recievedValue,leftoverValue,soldValue,Mprice);

        final ProgressDialog progress = new ProgressDialog(getContext());

        progress.setMessage("Submitting your precious data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(id).child(date).hasChild(item))
                {
                    Toast.makeText(getContext(),"Item already added",Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
                else
                {
                    final DatabaseReference db1=FirebaseDatabase.getInstance().getReference();
                    final String id1=db1.push().getKey();
                    db.child(id).child(date).child(item).setValue(ide, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            progress.dismiss();
                        }
                    });


                    db1.child("pdate").child(date).child("dates").setValue(date);
                    db1.child("shopList").child(shop_Name).setValue(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void readData(final FirebaseCallback2 firebaseCallback2)
    {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                price =dataSnapshot.child(item).child("price").getValue().toString();
                firebaseCallback2.onCallback2(price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
