package com.example.hp.franchisehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginPanel extends AppCompatActivity {



    EditText userName,Passwords;
    Button sign;
    String user_Name,user_Passwords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_panel);
        userName=findViewById(R.id.email);
        Passwords=findViewById(R.id.password);
        sign=findViewById(R.id.signin);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_Name = userName.getText().toString();
                user_Passwords = Passwords.getText().toString();
                if(!TextUtils.isEmpty(user_Name))
                {
                    if(!TextUtils.isEmpty(user_Passwords))
                    {
                        if (user_Name.equals("admin@gmail.com") && user_Passwords.equals("admin1234")) {

                            Intent intent = new Intent(AdminLoginPanel.this, AdminActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Invalid credentialas",Toast.LENGTH_LONG).show();
                        }

                    }
                    else
                    {
                        Passwords.setError("Sir -Password field cannot be empty");
                    }
                }
                else
                {
                    userName.setError("Sir - Field cannot be empty");
                    Passwords.setError("Sir - Field cannot be empty");
                }


            }
        });
    }
}
