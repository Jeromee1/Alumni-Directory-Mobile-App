package com.apa.alumnidirectory.data.repo

import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.data.model.request.LoginReq
import com.apa.alumnidirectory.data.model.request.RegisterUserReq
import com.apa.alumnidirectory.data.model.user.UserData

interface AuthRepo {
    suspend fun register(user: RegisterUserReq)
    suspend fun login(req: LoginReq): UserData
    suspend fun fetchProfile(uid: String): UserData

    //    suspend fun fetchMyProfile(id: String): UserData
//    suspend fun updateMyProfile(profileData: UpdateProfileReq)
//    suspend fun logout()
    suspend fun fetchAllUsers(): List<UserData>
    suspend fun fetchPendingUsers(): List<UserData>
    suspend fun fetchApprovedUsers(): List<UserData>

    //    suspend fun fetchRejectedUsers(): List<UserData>
    suspend fun submitAppeal(appeal: AppealReq)
    suspend fun fetchAppeal(): List<AppealReq>
    suspend fun fetchUnresolvedAppeal(): List<AppealReq>
    suspend fun approveUser(uid: String)
    suspend fun rejectUser(uid: String, msg: String)
}