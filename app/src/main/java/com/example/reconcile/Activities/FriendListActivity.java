package com.example.reconcile.Activities;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.reconcile.R;
import com.example.reconcile.ViewModel.FriendlistViewModel;
import com.example.reconcile.repository.Friend;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity  {

    ListView friendlistview1; // the dynamic list view
    FriendlistViewModel friendlistViewModel;


    private  ArrayList<Friend> friendlist;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        FloatingActionButton fabutton1 = findViewById(R.id.fb1);
        fabutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("friend","click");
                Intent intent = new Intent(FriendListActivity.this,AddfriendActivity.class);
                startActivity(intent);
            }
        });
        friendlistview1 = findViewById(R.id.friendlistview1);
        friendlistViewModel = ViewModelProviders.of(this).get(FriendlistViewModel.class);
        /*friendlistViewModel.getFriendlist().observe(this, new Observer<List<Friend>>() {
            @Override
            public void onChanged(List<Friend> friends) {

            }
       });*/
    }


}
