package com.apa.alumnidirectory.ui.base

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

open class BaseViewModel : ViewModel() {
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

    fun validateEmail(email: String): String? {
        return if(email.isBlank()) "Email cannot be empty."
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email."
        else null
    }

    fun validatePasswords(pass: String, pass2: String): String? {
        return if(pass.isBlank() || pass2.isBlank()) "Password fields cannot be empty."
        else if (pass.length < 8) "Password must be at least 8 characters."
        else if (pass != pass2) "Password and Confirm Password must match."
        else null
    }

    fun registerValidate(email: String?, pass: String?): Boolean {
        if (email != null) {
            Log.d("debug", email)
            return false
        }
        if (pass != null) {
            Log.d("debug", pass)
            return false
        }
        return true
    }
}
