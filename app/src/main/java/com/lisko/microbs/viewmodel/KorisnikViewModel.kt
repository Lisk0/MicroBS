package com.lisko.microbs.viewmodel

import androidx.lifecycle.*
import com.lisko.microbs.model.database.MicroRepository
import com.lisko.microbs.model.entities.Korisnik
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class KorisnikViewModel(private val repository: MicroRepository) : ViewModel() {
    fun insert(korisnik: Korisnik)= viewModelScope.launch {
        repository.insertKorisnik(korisnik)
    }

    val allKorisnik: LiveData<List<Korisnik>> = repository.allKorisnik.asLiveData()
    val allUsername: LiveData<List<String>> = repository.allUsername.asLiveData()
}

