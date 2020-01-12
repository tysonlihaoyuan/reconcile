package com.example.reconcile.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reconcile.R;
import com.example.reconcile.repository.Friend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddfriendActivity extends AppCompatActivity {
    private Button addfriendButton;
    private EditText username;
    private EditText usermail;
    private static int userid =0;
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
                Friend newfriend = addFriend();
                ref.push().setValue(newfriend);
                Toast.makeText(AddfriendActivity.this,"data is added",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddfriendActivity.this,FriendListActivity.class);
                startActivity(intent);
            }
        });

    }
   private Friend addFriend(){
     userid+=1;
     String usernamestring = username.getText().toString();
     String usermailstring = username.getText().toString();
     String statu = "single"; // if exist in list is single, else "couple"
     Friend newFriend = new Friend(userid,usernamestring,usermailstring,statu);
     return newFriend;
   }


}
