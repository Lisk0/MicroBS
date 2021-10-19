package com.lisko.microbs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisko.microbs.model.database.MicroRepository
import java.lang.IllegalArgumentException

class KupacViewModelFactory(private val repository: MicroRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(KupacViewModel::class.java))
            return KupacViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }

}