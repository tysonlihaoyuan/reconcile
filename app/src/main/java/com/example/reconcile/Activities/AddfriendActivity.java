package com.example.reconcile.Activities;


import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.view.inputmethod.EditorInfo;

import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.reconcile.R;


import com.example.reconcile.ViewModel.AddFriendViewModel;

import com.example.reconcile.ViewModel.data.User;

import java.util.List;



public class AddfriendActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    AddFriendViewModel addfriendlistViewModel;
    AddFriendAdapter myAdapter;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_addfriend);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        addfriendlistViewModel = ViewModelProviders.of(this).get(AddFriendViewModel.class);
        addfriendlistViewModel.init(AddfriendActivity.this);
        myAdapter = new AddFriendAdapter(addfriendlistViewModel.getFriendlist().getValue());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.buttonSetOnclick(new AddFriendAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                addfriendlistViewModel.addNewFriend();
                addfriendlistViewModel.getIsUpdated().observe(AddfriendActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true){
                            Toast.makeText(AddfriendActivity.this,"data is added",Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(AddfriendActivity.this,FriendListActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        addfriendlistViewModel.getFriendlist().observe(AddfriendActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> friends) {
                if (friends != null) {
                    myAdapter.notifyDataSetChanged();
                    }
            }
        });
    }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.search_menu, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);
                searchView = (SearchView) searchItem.getActionView();
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        addfriendlistViewModel.updateQuery(query);
                        return true;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                return true;
            }



}








