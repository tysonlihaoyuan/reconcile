package com.example.reconcile.Activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reconcile.R;
import com.example.reconcile.ViewModel.data.Friend;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private ArrayList<Friend> friendlist;


    public FriendListAdapter(ArrayList<Friend> friendlist) {
        this.friendlist = friendlist;
    }

    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frienditem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(friendlist.get(position));

        holder.name.setText(friendlist.get(position).getuserName());

    }

    @Override
    public int getItemCount() {
        return friendlist.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friend);
        }
    }
}
