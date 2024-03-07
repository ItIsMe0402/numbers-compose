package com.github.itisme0402.numberscompose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.itisme0402.numberscompose.core.HistoryUseCase
import com.github.itisme0402.numberscompose.core.LoadFactUseCase
import com.github.itisme0402.numberscompose.core.NumberInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadFactUseCase: LoadFactUseCase,
    private val historyUseCase: HistoryUseCase,
) : ViewModel() {

    private val _isGetFactEnabled = MutableStateFlow(false)
    val isGetFactEnabled: StateFlow<Boolean> = _isGetFactEnabled

    val history = historyUseCase.observeFacts()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _openFactScreenEvent = MutableSharedFlow<NumberInfo>()
    val openFactScreenEvent: Flow<NumberInfo> = _openFactScreenEvent

    fun onInputTextChanged(inputText: String) {
        _isGetFactEnabled.value = inputText.toIntOrNull() != null
    }

    fun onGetFactClicked(numberText: String) = viewModelScope.async {
        try {
            val number = numberText.toInt()
            _isLoading.value = true
            val numberInfo = loadFactUseCase.getFact(number)
            _openFactScreenEvent.emit(numberInfo)
        } catch (e: Exception) {
            // TODO: Add error handling
            Log.e(LOG_TAG, "Oh-no-no-no!", e)
            null
        } finally {
            _isLoading.value = false
        }
    }

    fun onGetRandomFactClicked() = viewModelScope.async {
        try {
            val numberInfo = loadFactUseCase.getFactForRandomNumber()
            _openFactScreenEvent.emit(numberInfo)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Oh noes!", e)
        } finally {
            _isLoading.value = false
        }
    }

    fun onHistoryItemClicked(numberInfo: NumberInfo) = viewModelScope.async {
        _openFactScreenEvent.emit(numberInfo)
    }

    private companion object {
        const val LOG_TAG = "HomeViewModel"
    }
}