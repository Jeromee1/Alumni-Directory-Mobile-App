package com.apa.alumnidirectory.ui.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable object Home: Screen()
    @Serializable object Login: Screen()
    @Serializable object Register: Screen()
    @Serializable object Pending: Screen()
    @Serializable object Dashboard: Screen()
    @Serializable object AdminPending: Screen()
    @Serializable object AdminManage: Screen()
    @Serializable object AdminAppeals: Screen()
    @Serializable data class AdminAppealsDetails(val appealId: String): Screen()
    @Serializable data class Profile(val userUid: String, val isAdmin: Boolean): Screen()
    @Serializable data class EditProfile(val userUid: String): Screen()

}