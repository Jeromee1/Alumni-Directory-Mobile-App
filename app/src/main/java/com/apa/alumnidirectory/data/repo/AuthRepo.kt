package com.apa.alumnidirectory.data.repo

import com.apa.alumnidirectory.data.model.auth.LoginReq
import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.model.auth.UpdateProfileReq
import com.apa.alumnidirectory.data.model.user.UserData

interface AuthRepo {
    suspend fun register(user: RegisterUserReq)

//    suspend fun login(user: LoginReq): UserData
//    suspend fun fetchProfile(id: String): UserData
//    suspend fun fetchMyProfile(id: String): UserData
//    suspend fun updateMyProfile(profileData: UpdateProfileReq)
//    suspend fun logout()
}