package org.rsschool.android2021task5.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.rsschool.android2021task5.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun foo() {
        viewModelScope.launch { repository.getImages()?.map { Log.d("IMAGE", it.toString()) } }

    }

}
