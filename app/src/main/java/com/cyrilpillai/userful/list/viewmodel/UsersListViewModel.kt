package com.cyrilpillai.userful.list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.cyrilpillai.userful.db.entity.User
import com.cyrilpillai.userful.extensions.toLiveData
import com.cyrilpillai.userful.list.UserEntity
import com.cyrilpillai.userful.list.model.UsersListContract
import com.cyrilpillai.userful.networking.entity.Outcome
import io.reactivex.disposables.CompositeDisposable

class UsersListViewModel(
        private val repository: UsersListContract.Repository,
        private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val usersLiveData: LiveData<Outcome<List<UserEntity>>> by lazy {
        repository.usersOutcome.toLiveData(compositeDisposable)
    }

    fun getUsers(forceUpdate: Boolean = false) {
        repository.fetchUsers(forceUpdate)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
