package com.example.hp.franchisehelper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddShop extends Fragment {

    Button addIt;
    EditText name,passwords;
    DatabaseReference dbRef;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    public AddShop() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_add_shop, container, false);
        addIt=view.findViewById(R.id.add);
        dbRef=FirebaseDatabase.getInstance().getReference("shop");
        mAuth = FirebaseAuth.getInstance();
        name =view.findViewById(R.id.shopName);
        passwords=view.findViewById(R.id.password);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...while we add your shop");
        addIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                final String shopname=name.getText().toString();
                String mail=shopname+"@gmail.com";
                String password=passwords.getText().toString();
                if(!TextUtils.isEmpty(shopname))
                {
                    if(!TextUtils.isEmpty(password))
                    {
                        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    // Sign in success, update UI with the signed-in user's information
                                    //  Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getContext(), "Authentication success : "+user,Toast.LENGTH_SHORT).show();
                                    String id=mAuth.getUid();
                                    Toast.makeText(getContext(),""+id,Toast.LENGTH_LONG).show();
                                    addShopToList(shopname,id);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getContext(),""+task.getException(),Toast.LENGTH_LONG).show();
                                    // Toast.makeText(getContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                            }
                        });
                    }
                    else
                    {
                        passwords.setError("Sir! Password field cannot be empty");
                    }

                }
                else
                {
                    name.setError("Sir! UserId field cannot be empty");
                }


            }
        });
        return view;
    }
    void addShopToList(String shopname,String id)
    {
        if(!TextUtils.isEmpty(shopname)) {

            ShopInfo info=new ShopInfo(shopname);
            dbRef.child(id).setValue(info);
        }

        Toast.makeText(getActivity(),"Shop has been added",Toast.LENGTH_LONG).show();
    }
}
