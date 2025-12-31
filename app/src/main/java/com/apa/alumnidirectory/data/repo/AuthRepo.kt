package com.apa.alumnidirectory.data.repo

import com.apa.alumnidirectory.data.model.auth.LoginReq
import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
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
}