package com.apa.alumnidirectory.data.repo.impls

import android.util.Log
import com.apa.alumnidirectory.data.model.auth.LoginReq
import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.model.user.UserData
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

    override suspend fun login(req: LoginReq): UserData {
        return authService.login(req.email, req.password).let { user ->
            Log.d("debug", user.isEmailVerified.toString())
            dbRef.document(user.uid)
                .get()
                .await()
                .toObject(UserData::class.java)
                ?: throw IllegalStateException("User doesn't exist")
        }
    }

    override suspend fun fetchAllUsers(): List<UserData> {
        val snapshot = dbRef
            .get()
            .await()
        return snapshot.documents.mapNotNull {
            it.toObject(UserData::class.java)
        }
    }

    override suspend fun fetchPendingUsers(): List<UserData> {
        val snapshot = dbRef
            .whereEqualTo("status", "pending")
            .get()
            .await()
        return snapshot.documents.mapNotNull {
            it.toObject(UserData::class.java)
        }
    }

    override suspend fun fetchApprovedUsers(): List<UserData> {
        val snapshot = dbRef
            .whereEqualTo("status", "approved")
            .get()
            .await()
        return snapshot.documents.mapNotNull {
            it.toObject(UserData::class.java)
        }
    }

//    override suspend fun fetchRejectedUsers(): List<UserData> {
//        val snapshot = dbRef
//            .whereEqualTo("status", "rejected")
//            .get()
//            .await()
//        return snapshot.documents.mapNotNull {
//            it.toObject(UserData::class.java)
//        }
//    }
}