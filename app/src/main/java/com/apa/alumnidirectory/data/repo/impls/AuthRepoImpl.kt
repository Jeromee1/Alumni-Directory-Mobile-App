package com.apa.alumnidirectory.data.repo.impls

import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.data.utils.buildUserData
import com.apa.alumnidirectory.service.FirebaseAuthService
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRepoImpl @Inject constructor(
    private val authService: FirebaseAuthService,
    private val firestore: FirebaseFirestore
) : AuthRepo {
    private val dbRef = firestore
        .collection("alumni_directory_db")
        .document("directory")
        .collection("users")

    override suspend fun register(user: RegisterUserReq) {
        val userUid = authService.register(user.email, user.password)
        val userData = buildUserData(user, userUid)

        dbRef.document(userUid).set(userData.toMap()).await()
    }
}