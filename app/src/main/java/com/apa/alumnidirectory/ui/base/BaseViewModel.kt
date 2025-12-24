package com.apa.alumnidirectory.ui.base

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
            null
        }
    }
}
