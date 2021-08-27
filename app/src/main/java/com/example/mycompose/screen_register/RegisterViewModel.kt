package com.example.mycompose.screen_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycompose.networking.configureClient
import io.ktor.client.request.forms.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel constructor():ViewModel() {
    private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState:StateFlow<LoginUiState> = _loginUiState

    sealed class LoginUiState{
        object Success:LoginUiState()
        data class Error(val message:String):LoginUiState()
        object Empty:LoginUiState()
    }

    fun connect() = viewModelScope.launch {
        val client = configureClient()

        var answer:String? = null
        var answer2:String? = null

        try{
            answer = client.submitFormWithBinaryData(
                "http://192.168.178.33:8080/customer",
                formData = formData{
                    append("first_name", "Jet")
                })


            _loginUiState.value = LoginUiState.Success
            //listOf(answer2)
        }catch (e: Exception){
            _loginUiState.value = LoginUiState.Error("Connection Failed")
        }

    }
}