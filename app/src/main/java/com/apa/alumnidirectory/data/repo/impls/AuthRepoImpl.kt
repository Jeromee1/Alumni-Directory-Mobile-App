package com.apa.alumnidirectory.data.repo.impls

import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.forms.RegisterForm
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.model.request.LoginReq
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.data.utils.buildAdminEditReq
import com.apa.alumnidirectory.data.utils.buildEditReq
import com.apa.alumnidirectory.data.utils.buildUserData
import com.apa.alumnidirectory.service.FirebaseAuthService
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class AuthRepoImpl @Inject constructor(
    private val authService: FirebaseAuthService,
    firestore: FirebaseFirestore
) : AuthRepo {
    private val dbRef = firestore
        .collection("alumni_directory_db")
        .document("directory")
        .collection("users")

    private val dbAppealRef = firestore
        .collection("alumni_directory_db")
        .document("directory")
        .collection("appeals")

    private val dbMetadataRef = firestore
        .collection("alumni_directory_db")
        .document("metadata")

    override suspend fun register(user: RegisterForm) {
        val userUid = authService.register(user.email, user.password)
        val userData = buildUserData(user, userUid)

        dbRef.document(userUid).set(userData.toMap()).await()
    }

    override suspend fun login(req: LoginReq): UserData {
        return authService.login(req.email, req.password).let { user ->
            dbRef.document(user.uid)
                .get()
                .await()
                .toObject(UserData::class.java)
                ?: throw IllegalStateException("User doesn't exist")
        }
    }

    override suspend fun fetchProfile(uid: String): UserData {
        return dbRef.document(uid)
            .get()
            .await()
            .toObject(UserData::class.java)
            ?: throw java.lang.IllegalStateException("User doesn't exist")
    }

    override suspend fun updateProfile(uid: String, form: EditProfileForm) {
        val updates = buildEditReq(form).toMap()
        dbRef.document(uid).update(updates).await()
    }

    override suspend fun adminUpdateProfile(uid: String, form: AdminEditProfileForm) {
        val updates = buildAdminEditReq(form).toMap()
        dbRef.document(uid).update(updates).await()
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

    override suspend fun submitAppeal(appeal: AppealReq) {
        val ref = dbAppealRef.document()
        val newAppeal = appeal.copy(uid = ref.id)
        ref.set(newAppeal.toMap()).await()
    }

    override suspend fun fetchAppeals(): List<AppealReq> {
        val snapshot = dbAppealRef
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(AppealReq::class.java)
        }
    }

    override suspend fun fetchUnresolvedAppeal(): List<AppealReq> {
        val snapshot = dbAppealRef
            .whereEqualTo("resolved", false)
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(AppealReq::class.java)
        }
    }

    override suspend fun fetchAppealById(uid: String): AppealReq {
        return dbAppealRef.document(uid)
            .get()
            .await()
            .toObject(AppealReq::class.java)
            ?: throw java.lang.IllegalStateException("Appeal doesn't exist")
    }

    override suspend fun resolveAppeal(uid: String) {
        dbAppealRef.document(uid)
            .update(
                mapOf<String, Any>(
                    "resolved" to true
                )
            )
    }

    override suspend fun approveUser(uid: String) {
        dbRef.document(uid)
            .update(
                mapOf<String, Any>(
                    "status" to Status.APPROVED.value,
                    "approvedAt" to System.currentTimeMillis()
                )
            )
            .await()
    }

    override suspend fun rejectUser(uid: String, msg: String) {
        if (msg.isBlank()) {
            dbRef.document(uid)
                .update(
                    mapOf<String, Any>(
                        "status" to Status.REJECTED.value,
                    )
                )
                .await()
        } else {
            dbRef.document(uid)
                .update(
                    mapOf<String, Any>(
                        "status" to Status.REJECTED.value,
                        "rejectionMsg" to msg
                    )
                )
                .await()
        }
    }

    override suspend fun fetchMetadata(): DocumentSnapshot {
        return dbMetadataRef.get().await()
    }

    override suspend fun readMetadataDept(): List<String> {
        val snapshot = fetchMetadata()
        return snapshot.get("techStacks") as? List<String> ?: emptyList()
    }

    override suspend fun readMetadataStacks(): List<String> {
        val snapshot = fetchMetadata()
        return snapshot.get("departments") as? List<String> ?: emptyList()
    }
}