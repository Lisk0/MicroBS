package com.lisko.microbs.viewmodel

import androidx.lifecycle.*
import com.lisko.microbs.model.database.MicroRepository
import com.lisko.microbs.model.entities.Zaposleni
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ZaposleniViewModel(private val repository: MicroRepository) : ViewModel() {
    fun insert(zaposleni: Zaposleni)= viewModelScope.launch {
        repository.insertZaposleni(zaposleni)
    }

    val allZaposleni:LiveData<List<Zaposleni>> = repository.allZaposleni.asLiveData()
    val allSifre: LiveData<List<String>> = repository.zaposleniSifre.asLiveData()
}

