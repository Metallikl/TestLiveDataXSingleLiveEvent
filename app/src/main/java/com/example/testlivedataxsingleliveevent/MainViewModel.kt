package com.example.testlivedataxsingleliveevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<MainState>().apply {
        value = MainState()
    }
    val state = _state as LiveData<MainState>

    private val _event = SingleLiveEvent<MainEvent>()
    val event = _event as LiveData<MainEvent>

    fun callApi() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(MainState(isLoading = true, text = "Carregando"))
            delay(500)
            _state.postValue(MainState(isError = true, text = "Erro"))
            _event.postValue(
                MainEvent(
                    message = "Error: SingleLiveEvent nao re-emit ao rotacionar tela."
                )
            )
        }
    }

    fun resetState() {
        _state.value = MainState()
    }


    data class MainState(
        val isError: Boolean = false,
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val text: String = "Text"
    )

    data class MainEvent(
        val message: String = ""
    )
}

