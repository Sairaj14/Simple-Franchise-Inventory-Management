package com.example.hp.franchisehelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItem extends Fragment {

    private static final int PICK_IMAGE_REQUEST =1 ;
    EditText nameOfItem,priceOfItem;
    Button addTo,chooseImage;
    DatabaseReference dRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView mImageView;
    Uri mImageUri;
    ProgressDialog progressDialog;
    public AddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_item, container, false);
        nameOfItem=view.findViewById(R.id.name_Item);
        priceOfItem=view.findViewById(R.id.price_item);
        addTo=view.findViewById(R.id.addToList);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait... while we add item to your list");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        dRef=FirebaseDatabase.getInstance().getReference();
        addTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String id;
                String name=nameOfItem.getText().toString();
                String price=priceOfItem.getText().toString();
                ItemInfo info=new ItemInfo(name,price);
                progressDialog.show();
                // mUser=FirebaseAuth.getInstance().getCurrentUser();


                dRef.child("Items").child(name).setValue(info, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        progressDialog.setMessage("Thank you for your patience");
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();

                    }
                });







            }
        });
        return view;
    }





    }

