package com.apa.alumnidirectory.service

import com.google.firebase.auth.FirebaseAuth
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun register(email: String, password: String): String {
        val result = firebaseAuth
            .createUserWithEmailAndPassword(email, password).await()

        return result.user?.uid
            ?: throw IllegalStateException("User creation failed")
    }
}