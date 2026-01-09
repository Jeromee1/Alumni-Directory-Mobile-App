package com.apa.alumnidirectory.ui.base

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.forms.RegisterForm
import com.apa.alumnidirectory.data.model.request.LoginReq
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@HiltViewModel
open class BaseViewModel : ViewModel() {
    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()
    suspend fun <T> safeApiCall(func: suspend () -> T?): T? {
        return try {
            val result = withContext(Dispatchers.IO) {
                func.invoke()
            }
            result
        } catch (e: Exception) {
            Log.d("debug", e.message.toString())
            null
        }
    }

    fun validateLogin(form: LoginReq): Boolean {
        if (form.email.isBlank() || form.password.isBlank()) {
            emitToast("Fields cannot be blank")
            return false
        }
        return true
    }

    fun validateAdminEditProfileForm(form: AdminEditProfileForm): Boolean {
        if (form.fullName.isBlank()) {
            emitToast("Full Name cannot be empty")
            return false

        }
        return true
    }

    fun validateEditProfileForm(form: EditProfileForm): Boolean {
        form.apply {
            val fieldsWithMessages = listOf(
                department to "Department is required",
                position to "Position is required",
                company to "Company is required",
                primaryStack to "Tech stack is required",
                location.state to "State is required",
                location.country to "Country is required",
                preferredContact to "Preferred contact is required"
            )
            for ((value, message) in fieldsWithMessages) {
                if (value.isBlank()) {
                    emitToast(message)
                    return false
                }
            }
        }
        return true
    }

    fun validateRegisterFields(form: RegisterForm): Boolean {
        form.apply {
            val fieldsWithMessages = listOf(
                fullName to "Full name cannot be blank",
                email to "Email cannot be blank",
                password to "Password cannot be blank",
                graduationYear to "Graduation year is required",
                department to "Department is required",
                position to "Position is required",
                company to "Company is required",
                techStack to "Tech stack is required",
                state to "State is required",
                country to "Country is required"
            )
            for ((value, message) in fieldsWithMessages) {
                if (value.isBlank()) {
                    emitToast(message)
                    return false
                }
            }
            if (!validateEmailFormat(email)) return false
            if (!validatePassword(password, password2)) return false
        }
        return true
    }

    fun validateEmailFormat(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emitToast("Invalid email format")
            return false
        }
        return true
    }

    fun validatePassword(pass: String, pass2: String): Boolean {
        if (pass.length < 8) {
            emitToast("Password must be at least 8 characters")
            return false
        }

        if (pass != pass2) {
            emitToast("Passwords do not match")
            return false
        }
        return true
    }

    private fun emitToast(msg: String) {
        viewModelScope.launch {
            _toast.emit(msg)
        }
    }
}
