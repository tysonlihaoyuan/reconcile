package com.example.reconcile.repository;


import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.reconcile.ViewModel.DataLoadListener;
import com.example.reconcile.ViewModel.data.Friend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class Friendlistrepository {
    private static Friendlistrepository instance;

    private ArrayList<Friend> friendlist = new ArrayList<>();
    private DatabaseReference mDatabas = FirebaseDatabase.getInstance().getReference();
    private MutableLiveData<ArrayList<Friend>> friend = new MutableLiveData<>();
    static Context mcontext;
    static DataLoadListener mdataLoadListener;

    public static Friendlistrepository getInstance(Context context){
        mcontext = context;
        if (instance == null){
            instance = new Friendlistrepository();
        }
        mdataLoadListener = (DataLoadListener) mcontext;
        return instance;
    }



    public MutableLiveData<ArrayList<Friend>> getFriendlist(){
        if (friendlist.size() ==0){
            loadFriend();
        }

        friend.setValue(friendlist);
        return friend;
    }

    public void loadFriend(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        Query query = ref.child("Friend");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    friendlist.add(snapshot.getValue(Friend.class));

                }
                mdataLoadListener.onNameLoad();
                friend.postValue(friendlist);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
