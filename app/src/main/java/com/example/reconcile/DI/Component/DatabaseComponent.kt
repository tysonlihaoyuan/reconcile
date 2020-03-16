package com.example.reconcile.DI.Component

import com.example.reconcile.Activities.NewRoomActivity
import com.example.reconcile.DI.Module.DatabaseModule
import com.example.reconcile.ViewModel.ChatRoomViewModel
import dagger.Component

@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {

    fun inject(ViewModel: ChatRoomViewModel)

    fun inject(NewrRoom: NewRoomActivity)
}