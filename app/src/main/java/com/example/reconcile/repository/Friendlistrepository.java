package com.example.reconcile.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Friendlistrepository {
    private static Friendlistrepository instance;
    private ArrayList<Friend> dataset = new ArrayList<>();
    private DatabaseReference mDatabas = FirebaseDatabase.getInstance().getReference();

    public static Friendlistrepository getInstance(){
        if (instance == null){
            instance = new Friendlistrepository();
        }
        return instance;
    }
    public MutableLiveData<List<Friend>> getFriendlist(){
        setFriendlist();
        MutableLiveData<List<Friend>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setFriendlist(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
    }

}
