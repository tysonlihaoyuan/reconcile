package com.example.reconcile.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reconcile.R;
import com.example.reconcile.ViewModel.AddFriendViewModel;

import com.example.reconcile.ViewModel.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {

    private List<User> friendlist;
    private ButtonInterface buttonInterface;


    public AddFriendAdapter(List<User> friendlist) {
        this.friendlist = friendlist;

    }
    public interface ButtonInterface{
        public void onclick( View view,int position);
    }
    public void buttonSetOnclick(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addfrienditem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setTag(friendlist.get(position));

        holder.name.setText(friendlist.get(position).getUserName());
        holder.addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonInterface.onclick(v,position);
            }
        });
    }


    @Override
    public int getItemCount() {

        return (friendlist==null)?0:friendlist.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button addFriend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friend);
            addFriend = itemView.findViewById(R.id.button);
        }
    }
}
