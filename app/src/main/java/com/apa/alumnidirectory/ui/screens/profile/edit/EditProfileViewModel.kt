package com.apa.alumnidirectory.ui.screens.profile.edit

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.ui.Country
import com.apa.alumnidirectory.data.model.ui.State
import com.apa.alumnidirectory.data.model.user.CurrentUser
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.data.utils.loadCountries
import com.apa.alumnidirectory.service.FirebaseAuthService
import com.apa.alumnidirectory.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val repo: AuthRepo,
    val firebaseAuth: FirebaseAuthService,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val uid = savedStateHandle.get<String>("userUid")!!

    private var _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser = _currentUser.asStateFlow()
    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    private var _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()
    val countries: List<Country> = loadCountries(context)
    private val _selectedCountry = MutableStateFlow<Country?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    private val _selectedState = MutableStateFlow<State?>(null)
    val selectedState = _selectedState.asStateFlow()

    private val _techStacks = MutableStateFlow<List<String>>(emptyList())
    val techStacks = _techStacks.asStateFlow()
    private val _departments = MutableStateFlow<List<String>>(emptyList())
    val departments = _departments.asStateFlow()

    val availableStates = _selectedCountry
        .map { it?.states ?: emptyList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    init {
        fetchUser()
        fetchLoggedInUser()
        fetchMetadata()
    }

    fun fetchMetadata() {
        viewModelScope.launch {
            safeApiCall {
                repo.readMetadataDept().let { departments ->
                    _departments.update { departments }
                }
                repo.readMetadataStacks().let { stacks ->
                    _techStacks.update { stacks }
                }
            }
        }
    }

    fun fetchLoggedInUser() {
        viewModelScope.launch {
            safeApiCall {
                firebaseAuth.getCurrentUser()?.let { user ->
                    val userData = repo.fetchProfile(user.uid)
                    _currentUser.update {
                        CurrentUser(
                            firebaseData = user,
                            userData = userData
                        )
                    }
                }
            }
        }
    }

    fun onCountrySelected(country: Country) {
        _selectedCountry.value = country
        _selectedState.value = null
    }

    fun onStateSelected(state: State) {
        _selectedState.value = state
    }

    fun fetchUser() {
        viewModelScope.launch {
            safeApiCall {
                setUser(repo.fetchProfile(uid))
            }
        }
    }

    fun setUser(user: UserData) {
        _user.value = user
        val country = countries.firstOrNull {
            it.name == user.location.country
        }
        _selectedCountry.value = country
        val state = country?.states?.firstOrNull {
            it.name == user.location.state
        }
        _selectedState.value = state
    }

    fun updateUser(form: EditProfileForm) {
        if(!validateEditProfileForm(form)) return
        viewModelScope.launch {
            safeApiCall {
                repo.updateProfile(uid, form).let {
                    _finish.emit(Unit)
                }
            }
        }
    }

    fun adminUpdateUser(form: EditProfileForm, adminForm: AdminEditProfileForm) {
        if (!validateEditProfileForm(form) || !validateAdminEditProfileForm(adminForm)) return
        viewModelScope.launch {
            safeApiCall {
                repo.updateProfile(uid, form)
                repo.adminUpdateProfile(uid, adminForm).let {
                    _finish.emit(Unit)
                }
            }
        }
    }

    fun permissionCheck(): Boolean {
        val currentUser = currentUser.value
        return (currentUser?.userData?.role == Roles.ADMIN.value)
    }
}