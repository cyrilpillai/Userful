package com.cyrilpillai.userful.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.cyrilpillai.userful.list.model.UsersListContract
import io.reactivex.disposables.CompositeDisposable

class UsersListViewModelFactory(
        private val repository: UsersListContract.Repository,
        private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersListViewModel(repository, compositeDisposable) as T
    }
}
