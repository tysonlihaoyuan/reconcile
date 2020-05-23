package com.example.reconcile.ViewModel;


import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reconcile.DI.Component.DaggerViewModelComponent;

import com.example.reconcile.ViewModel.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class AddFriendViewModel extends ViewModel {
    private MutableLiveData<List<User>> mFriendList=new MutableLiveData<>();
    private  MutableLiveData<Boolean> misUpdateList = new MutableLiveData<>();
    private List<User> friendList = new ArrayList<>( );
    private  Boolean isFinished;
    @Inject
    @Named("users")
    CollectionReference database;
    private FirebaseUser user;
    private String currentUserid;
    private static final String TAG = AddFriendViewModel.class.getSimpleName() ;
    private Context context;
    private Query query;


    public void init(Context context){
        this.context = context;
        DaggerViewModelComponent.create().inject(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        isFinished = false;
    }

    public MutableLiveData<List<User>> getFriendlist(){
        mFriendList.setValue(friendList);
        return mFriendList;
    }

    public MutableLiveData<Boolean> getIsUpdated() {
        misUpdateList.setValue(isFinished);
        return misUpdateList;
    }



    public void loadFriend(Query searchQuery) {
        searchQuery.get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              friendList.clear();
                for(QueryDocumentSnapshot qc : queryDocumentSnapshots){
                    User f = qc.toObject(User.class);
                    friendList.add(f);
                }
                mFriendList.postValue(friendList);
            }
        } ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "no result, try again", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void updateQuery( String searchQuery){
        this.query = database.whereEqualTo("useremail",searchQuery);
        loadFriend(query);
    }
    public  Query getQuery(){
        return this.query;
    }


    public void addNewFriend(){
        //TODo: duplicate the list
        database.whereEqualTo("useremail",user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<String> friendlist = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        currentUserid = document.getId();
                        User u = document.toObject(User.class);
                        friendlist = u.getFriendsUID();
                        friendlist.add(friendList.get(0).getUid());
                    }

                    database.document(currentUserid).update("friendsUID",friendlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,"DocumentSnapshot successfully updated!");
                            isFinished = true;
                            misUpdateList.postValue(isFinished);
                            Log.d(TAG,"process is end " + isFinished);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });

                }
            }
        });

    }

}

