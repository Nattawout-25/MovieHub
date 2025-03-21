package com.arise.training.moviehub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arise.training.moviehub.domain.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _number = MutableLiveData<Int>()
    val number: LiveData<Int> = _number

    init {
        Timber.d("init")
    }

    fun setCounter(value: Int) {
        _number.value = value
    }

    fun counter() {
        _number.value = (_number.value ?: 0) + 1
    }

    fun executeGetPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .onStart {
                    Timber.d("testapp onStart")
                }
                .onCompletion {
                    Timber.d("testapp onCompletion")
                }
                .collect {
                    Timber.d("testapp movies $it")
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }
}