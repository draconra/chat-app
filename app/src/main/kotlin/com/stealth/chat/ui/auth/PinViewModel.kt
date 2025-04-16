package com.stealth.chat.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stealth.chat.data.local.PinPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinPrefs: PinPreferenceManager
) : ViewModel() {

    private val _isPinVerified = MutableStateFlow(false)
    val isPinVerified: StateFlow<Boolean> = _isPinVerified

    init {
        viewModelScope.launch {
            pinPrefs.isPinVerified.collect {
                _isPinVerified.value = it
            }
        }
    }

    fun savePinVerified() {
        viewModelScope.launch {
            pinPrefs.savePinVerified()
        }
    }
}