package com.apa.alumnidirectory.data.repo

import com.apa.alumnidirectory.data.model.auth.RegisterUserReq

interface AuthRepo {
    suspend fun register(user: RegisterUserReq)

//    suspend fun login(user: LoginReq): UserData
//    suspend fun fetchProfile(id: String): UserData
//    suspend fun fetchMyProfile(id: String): UserData
//    suspend fun updateMyProfile(profileData: UpdateProfileReq)
//    suspend fun logout()
}