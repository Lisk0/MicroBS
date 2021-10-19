package com.lisko.microbs.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.lisko.microbs.model.database.MicroRepository
import com.lisko.microbs.model.entities.Korisnik
import com.lisko.microbs.model.entities.Kupac
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class KupacViewModel(private val repository: MicroRepository) : ViewModel() {

    fun insert(kupac: Kupac) = viewModelScope.launch {
        repository.insertKupac(kupac)
    }

    fun update(kupac: Kupac) = viewModelScope.launch {
        repository.updateKupac(kupac)
    }

    fun delete(kupac: Kupac) = viewModelScope.launch {
        repository.deleteKupac(kupac)
    }

    fun filter(sifraZaposlenog: String):LiveData<List<Kupac>> =
        repository.filterKupac(sifraZaposlenog).asLiveData()


    val allKupac: LiveData<List<Kupac>> = repository.allKupac.asLiveData()
    val allSifreKupac: LiveData<List<String>> = repository.kupacSifre.asLiveData()

}
