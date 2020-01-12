package com.example.reconcile.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reconcile.repository.Friend;
import com.example.reconcile.repository.Friendlistrepository;

import java.util.List;

public class FriendlistViewModel extends ViewModel {

    private MutableLiveData<List<Friend>> mfriendlist;
    private Friendlistrepository mRepo;
    private MutableLiveData<Boolean> mIsUpdate =  new MutableLiveData<>();
    public void init(){
        if(mfriendlist != null){
            return;
        }
        mRepo = Friendlistrepository.getInstance();
        mfriendlist = mRepo.getFriendlist();
    }
    public LiveData<List<Friend>> getFriendlist(){
        return mfriendlist;
    }

    public void addNewValue (final Friend friend){
        mIsUpdate.setValue(true);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                List<Friend> currentList = mfriendlist.getValue();
                currentList.add(friend);
                mfriendlist.postValue(currentList);
                mIsUpdate.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    public LiveData<Boolean> getIsUpdate(){
        return mIsUpdate;
    }
}
