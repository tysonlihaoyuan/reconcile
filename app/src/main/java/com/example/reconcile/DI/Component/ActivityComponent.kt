package com.example.reconcile.DI.Component

import com.example.reconcile.Activities.AddfriendActivity
import com.example.reconcile.Activities.FriendListActivity
import com.example.reconcile.Activities.LoginActivity
import com.example.reconcile.Activities.NewRoomActivity
import com.example.reconcile.DI.Module.DatabaseModule
import com.example.reconcile.DI.Module.UserAuthModule
import com.example.reconcile.RegisterActivity
import com.example.reconcile.ViewModel.ChatRoomViewModel
import dagger.Component

@Component(modules = [DatabaseModule::class, UserAuthModule::class])
interface ActivityComponent {
    fun inject(NewRoomActivity: NewRoomActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(friendListActivity: FriendListActivity)
    fun inject(AddFriendAcivity:AddfriendActivity)
}