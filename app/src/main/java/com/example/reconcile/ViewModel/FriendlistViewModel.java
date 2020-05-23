package com.example.reconcile.ViewModel;


import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.reconcile.DI.Component.DaggerViewModelComponent;
import com.example.reconcile.ViewModel.data.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class FriendlistViewModel extends ViewModel {

    @Inject
    @Named("users")
    CollectionReference database;
    FirebaseUser user;
    MutableLiveData<ArrayList<User>> mFriendList = new MutableLiveData<>();
    ArrayList<User> friendList = new ArrayList<>();
    ArrayList<User> currentUsers = new ArrayList<>();
    List<String> targetIds = new ArrayList<>();
    static final String TAG = FriendlistViewModel.class.getSimpleName();



    public void init(Context context) {
        DaggerViewModelComponent.create().inject(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        loadFriend();
        Log.d(TAG,"init load time");
    }

    public MutableLiveData<ArrayList<User>> getFriendlist() {
        mFriendList.setValue(friendList);
        Log.d(TAG,"mutableListSize is" + mFriendList.getValue().size());
        return mFriendList;
    }


    public void loadFriend() {
        String currentUserEmail = user.getEmail();
        friendList.clear();
        currentUsers.clear();
        targetIds.clear();
        database.whereEqualTo("useremail", currentUserEmail).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot qc : queryDocumentSnapshots) {
                            User currentUser = qc.toObject(User.class);
                            currentUsers.add(currentUser);
                        }
                        targetIds = currentUsers.get(0).getFriendsUID();
                        Log.d(TAG, targetIds.toString());
                        for (String id : targetIds) {
                            database.whereEqualTo("uid", id).get().addOnSuccessListener(
                                    new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot qc : queryDocumentSnapshots) {
                                        User targetUser = qc.toObject(User.class);
                                        friendList.add(targetUser);
                                        mFriendList.postValue(friendList);
                                        Log.d(TAG,"" + friendList.size());
                                        Log.d(TAG,"mutableListSize is" + mFriendList.getValue().get(0).getUseremail());
                                    }
                                    Log.d(TAG,"mutableListSize is" + mFriendList.getValue().size());
                                }
                            });
                        }
                    }

                });
    }
}