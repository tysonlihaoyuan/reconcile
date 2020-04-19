package com.example.reconcile.DI.Component

import com.example.reconcile.Activities.FriendListActivity
import com.example.reconcile.Activities.LoginActivity
import com.example.reconcile.Activities.NewRoomActivity
import com.example.reconcile.DI.Module.DatabaseModule
import com.example.reconcile.DI.Module.UserAuthModule
import com.example.reconcile.RegisterActivity
import com.example.reconcile.ViewModel.AuthViewModel
import com.example.reconcile.ViewModel.ChatRoomViewModel
import dagger.Component

@Component(modules = [UserAuthModule::class, DatabaseModule::class])
interface ViewModelComponent {
    fun inject(authViewModel: AuthViewModel)
    fun inject(ViewModel: ChatRoomViewModel)
}