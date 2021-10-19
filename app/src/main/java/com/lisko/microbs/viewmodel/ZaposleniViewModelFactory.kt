package com.lisko.microbs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lisko.microbs.model.database.MicroRepository
import java.lang.IllegalArgumentException

class ZaposleniViewModelFactory(private val repository: MicroRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ZaposleniViewModel::class.java))
            return ZaposleniViewModel(repository) as T
        throw IllegalArgumentException("Unknown class")
    }

}