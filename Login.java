package com.example.rezeqee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applitools.eyes.appium.Eyes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.session.SessionManager;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {


        SessionManager sessionManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextView creatednewAccount = findViewById(R.id.createdNewAccount);



        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.txtpasswordlogin);
        final Button btnlogin = findViewById(R.id.btn_login);


            btnlogin.setOnClickListener((view -> {

                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter email & password", Toast.LENGTH_SHORT);
                }
                final FirebaseAuth mFirebaseAuth;
                mFirebaseAuth = FirebaseAuth.getInstance();
                if (!(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())) {


                    mFirebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener((task) -> {

                        if (task.isSuccessful()) {


                            String uid = task.getResult().getUser().getUid();

                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int usertype = snapshot.getValue(Integer.class);
                                    if (usertype == 0) {

                                        Intent in = new Intent(Login.this, MainDonator.class);
                                        startActivity(in);

                                     Toast.makeText(Login.this, "Login as Donator", Toast.LENGTH_SHORT).show();

                                    }
                                    if (usertype == 1) {

                                        Intent in = new Intent(Login.this, mainNgo.class);
                                        startActivity(in);
                                        Toast.makeText(Login.this, "Login as NGO", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(Login.this, "Incorrect Details, Please input again!", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
            ));





        creatednewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, mainregister.class));
            }
        });
    }


}