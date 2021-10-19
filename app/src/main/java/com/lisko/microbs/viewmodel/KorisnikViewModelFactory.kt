package com.lisko.microbs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisko.microbs.model.database.MicroRepository
import java.lang.IllegalArgumentException

class KorisnikViewModelFactory(private val repository: MicroRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(KorisnikViewModel::class.java))
            return KorisnikViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }

}