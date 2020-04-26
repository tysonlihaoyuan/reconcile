package com.example.reconcile.ViewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reconcile.ViewModel.ChatViewModel

class ChatViewModelFactory (
    private val uid: String
): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return ChatViewModel(uid) as T
    }
}
