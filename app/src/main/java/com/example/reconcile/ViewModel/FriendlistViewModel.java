package com.example.reconcile.ViewModel;


import android.content.Context;
import android.provider.DocumentsContract;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.reconcile.Activities.FriendListActivity;
import com.example.reconcile.DI.Component.DaggerActivityComponent;
import com.example.reconcile.DI.Component.DaggerViewModelComponent;
import com.example.reconcile.ViewModel.data.Friend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.NoAllocation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

public class FriendlistViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Friend>> mFriendList=new MutableLiveData<>();
    private ArrayList<Friend> friendList = new ArrayList<>( );
    @Inject @Named("users")
    CollectionReference database;

    private DocumentReference fListDocumentReference;
    private static final String TAG = "friendviewmodel" ;
    public void init(Context context){
        DaggerViewModelComponent.create().inject(this );
        loadFriend(database);


    }
    public MutableLiveData<ArrayList<Friend>> getFriendlist(){
        if (friendList.size() ==0){
            loadFriend(database);
        }

        mFriendList.setValue(friendList);

        return mFriendList;
    }
    public void loadFriend(CollectionReference cf) {

        cf.get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        friendList.clear();
                        for(QueryDocumentSnapshot qc : queryDocumentSnapshots){
                            Friend f = qc.toObject(Friend.class);
                            friendList.add(f);

                        }

                        mFriendList.postValue(friendList);
                    }

                } );


    }


}
