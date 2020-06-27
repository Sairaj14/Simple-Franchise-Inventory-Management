package com.example.hp.franchisehelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {


    EditText emailId,Mpassword,cFmpassword;
    Button register,logins,admins;
    String userMail,userPassword,cmPassword;
    ProgressDialog progressBar;
    TextView forgot;
    static int flag = 0;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        forgot=findViewById(R.id.forgotpassword);
        mAuth = FirebaseAuth.getInstance();
        emailId= findViewById(R.id.email);
        Mpassword=findViewById(R.id.password);
        logins=findViewById(R.id.login);
        admins=findViewById(R.id.admin);
        progressBar=new ProgressDialog(SignUpActivity.this);
        progressBar.setMessage("Signing you in..");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);


        logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userMail = emailId.getText().toString();
                userPassword = Mpassword.getText().toString();
                progressBar.show();
                if(!TextUtils.isEmpty(userMail))
                {
                    if(!TextUtils.isEmpty(userPassword))
                    {
                        mAuth.signInWithEmailAndPassword(userMail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful() ) {

                                    //  Toast.makeText(SignUpActivity.this,""+user,Toast.LENGTH_LONG).show();
                                    progressBar.dismiss();
                                    Intent intent = new Intent(SignUpActivity.this, UserAct.class);
                                    intent.putExtra("shop_Name",userMail);
                                    startActivity(intent);
                                }


                                if (!task.isSuccessful()) {
                                    progressBar.dismiss();
                                    Toast.makeText(SignUpActivity.this, "signInWithEmail:failed" + task.getException(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(SignUpActivity.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else
                    {
                        Mpassword.setError("Password field cannot be empty");
                    }
                }
                else
                {
                    emailId.setError("Field cannot be empty");
                    Mpassword.setError("Field cannot be empty");
                }
            }


        });

        admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(SignUpActivity.this,AdminLoginPanel.class);
                startActivity(intent);


            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPassword fragment = new  ForgetPassword();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainhello, fragment);
                transaction.commit();
                transaction.addToBackStack(null);

            }
        });

    }
}
