package com.apa.alumnidirectory.data.repo

import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.forms.RegisterForm
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.model.request.LoginReq
import com.apa.alumnidirectory.data.model.user.UserData
import com.google.firebase.firestore.DocumentSnapshot

interface AuthRepo {
    suspend fun register(user: RegisterForm)
    suspend fun login(req: LoginReq): UserData
    suspend fun fetchProfile(uid: String): UserData
    suspend fun updateProfile(uid: String, form: EditProfileForm)
    suspend fun adminUpdateProfile(uid: String, form: AdminEditProfileForm)
    suspend fun fetchAllUsers(): List<UserData>
    suspend fun fetchPendingUsers(): List<UserData>
    suspend fun fetchApprovedUsers(): List<UserData>

    suspend fun submitAppeal(appeal: AppealReq)
    suspend fun fetchAppeals(): List<AppealReq>
    suspend fun fetchUnresolvedAppeal(): List<AppealReq>
    suspend fun fetchAppealById(uid: String): AppealReq
    suspend fun resolveAppeal(uid: String)
    suspend fun approveUser(uid: String)
    suspend fun rejectUser(uid: String, msg: String)

    suspend fun fetchMetadata(): DocumentSnapshot
    suspend fun readMetadataDept(): List<String>
    suspend fun readMetadataStacks(): List<String>
}