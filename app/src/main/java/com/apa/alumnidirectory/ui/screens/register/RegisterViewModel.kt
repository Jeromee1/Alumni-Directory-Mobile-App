package com.apa.alumnidirectory.ui.screens.register

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.apa.alumnidirectory.data.model.forms.RegisterForm
import com.apa.alumnidirectory.data.model.ui.Country
import com.apa.alumnidirectory.data.model.ui.State
import com.apa.alumnidirectory.data.repo.AuthRepo
import com.apa.alumnidirectory.data.utils.loadCountries
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
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val repo: AuthRepo
) : BaseViewModel() {

    private var _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()
    val countries: List<Country> = loadCountries(context)
    private val _selectedCountry = MutableStateFlow<Country?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    private val _selectedState = MutableStateFlow<State?>(null)
    val selectedState = _selectedState.asStateFlow()

    val availableStates = _selectedCountry
        .map { it?.states ?: emptyList() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _techStacks = MutableStateFlow<List<String>>(emptyList())
    val techStacks = _techStacks.asStateFlow()
    private val _departments = MutableStateFlow<List<String>>(emptyList())
    val departments = _departments.asStateFlow()

    fun onCountrySelected(country: Country) {
        _selectedCountry.value = country
        _selectedState.value = null
    }

    fun onStateSelected(state: State) {
        _selectedState.value = state
    }

    init {
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


    fun register(userReq: RegisterForm) {
        if (!validateRegisterFields(userReq)) return
        viewModelScope.launch {
            val success = safeApiCall {
                repo.register(userReq)
            }
            if (success != null) {
                _toast.emit("Registration Successful")
                _finish.emit(Unit)
            }
        }
    }
}