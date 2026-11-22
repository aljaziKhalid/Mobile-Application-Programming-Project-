
package com.mystiq.ui.screens.Spread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mystiq.data.repository.SpreadRepository

class SpreadViewModelFactory(
    private val repository: SpreadRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpreadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpreadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
