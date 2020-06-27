package com.example.hp.franchisehelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassword extends Fragment {


    EditText registered_Email;
    Button send_Email;
    FirebaseAuth mAuth;
    String email;
    public ForgetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password, container, false);
        registered_Email=view.findViewById(R.id.registerId);
        send_Email=view.findViewById(R.id.sendEmail);
        mAuth=FirebaseAuth.getInstance();

        send_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=registered_Email.getText().toString();
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "You have been sent a reset password link ... Thank you", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "User id Problem", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }



        });
        return view;
    }

}
