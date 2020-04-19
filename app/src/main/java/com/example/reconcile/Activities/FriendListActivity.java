package com.example.reconcile.Activities;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.reconcile.R;
import com.example.reconcile.ViewModel.FriendlistViewModel;
import com.example.reconcile.repository.Friend;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;

public class FriendListActivity extends AppCompatActivity  implements View.OnClickListener {

    ListView friendlistview1; // the dynamic list view
    FriendlistViewModel friendlistViewModel;
    @Inject
    FirebaseAuth auth;
    TextView textView;
    ImageButton profileButton;
    FloatingActionButton addNewFriendButton;

    @Override
    public void onClick(View v) {
        if(v == profileButton){
            Intent intent = new Intent(FriendListActivity.this, AuthEditActivity.class );
            startActivity(intent);
        }
        if(v == addNewFriendButton){
            Log.d("friend","click");
            Intent intent = new Intent(FriendListActivity.this,ChatRoomActivity.class);
            startActivity(intent);
        }
    }

    private  ArrayList<Friend> friendlist;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        //Dagger.create().inject(this);

        profileButton = findViewById(R.id.profileImageButton);
        addNewFriendButton = findViewById(R.id.fb1);
        profileButton.setOnClickListener(this);
        addNewFriendButton.setOnClickListener(this);
        //friendlistview1 = findViewById(R.id.friendlistview1);
        friendlistViewModel = ViewModelProviders.of(this).get(FriendlistViewModel.class);
        /*friendlistViewModel.getFriendlist().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {

            }
       });*/
    }


}
