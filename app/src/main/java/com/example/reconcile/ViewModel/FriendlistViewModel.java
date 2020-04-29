package com.example.reconcile.ViewModel;


import android.content.Context;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.reconcile.ViewModel.data.Friend;
import com.example.reconcile.repository.Friendlistrepository;

import java.util.ArrayList;

public class FriendlistViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Friend>> mfriendlist;
    private Friendlistrepository mRepo;

    public void init(Context context){
        if(mfriendlist != null){
            return;
        }
        mfriendlist = mRepo.getInstance(context).getFriendlist();

    }
    public LiveData<ArrayList<Friend>> getFriendlist(){
        return mfriendlist;
    }


}
