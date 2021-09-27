package org.rsschool.android2021task5.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.rsschool.android2021task5.model.Image
import org.rsschool.android2021task5.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _imagesListFlow = MutableStateFlow<List<Image>>(emptyList())
    val imagesListFlow: Flow<List<Image>> = _imagesListFlow

    fun getImages() {
        viewModelScope.launch {
            try {
                _imagesListFlow.value = repository.getImages()
            } catch (e: Exception) {
                _imagesListFlow.value = emptyList()
            }
        }
    }
}
