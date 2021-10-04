package org.rsschool.android2021task5.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.rsschool.android2021task5.model.ImageDTO
import org.rsschool.android2021task5.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val imagesFlow: Flow<PagingData<ImageDTO>> =
        repository.fetchImagesFlow().cachedIn(viewModelScope)
}
