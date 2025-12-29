package com.apa.alumnidirectory.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun register(email: String, password: String): String {
        val result =
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .await()
            } catch (e: FirebaseAuthUserCollisionException) {
                throw IllegalStateException("Email in-use")
            } catch (e: Exception) {
                throw e
            }
        return result.user?.uid ?: throw IllegalStateException("User creation failed")
    }

    suspend fun login(email: String, password: String): FirebaseUser {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

        return result.user ?: throw java.lang.IllegalStateException("Login failed")
    }
}