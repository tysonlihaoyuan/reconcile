package com.example.reconcile.Activities;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reconcile.R;


import com.example.reconcile.ViewModel.data.Friend;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddfriendActivity extends AppCompatActivity {
    private Button addfriendButton;
    private EditText username;
    private EditText usermail;

    private EditText userStatus;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("Friend");

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate( saveInstanceState);
        setContentView(R.layout.activity_addfriend);
        addfriendButton = findViewById(R.id.addUser);

        username = findViewById(R.id.userName);
        usermail = findViewById(R.id.userEmail);
        addfriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = findViewById(R.id.userName);
                usermail = findViewById(R.id.userEmail);
                userStatus = findViewById(R.id.userStatus);
                addfriendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Friend newfriend = new Friend(username.getText().toString(), usermail.getText().toString(), userStatus.getText().toString());
                        ref.push().setValue(newfriend);
                        Toast.makeText(AddfriendActivity.this, "data is added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddfriendActivity.this, FriendListActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}







