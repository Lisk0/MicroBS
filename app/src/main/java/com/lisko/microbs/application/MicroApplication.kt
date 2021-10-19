package com.lisko.microbs.application

import android.app.Application
import com.lisko.microbs.model.database.MicroDatabase
import com.lisko.microbs.model.database.MicroRepository

class MicroApplication: Application() {
    private val database by lazy { MicroDatabase.getDatabase(this@MicroApplication)}
    val repository by lazy { MicroRepository(database.korisnikDao(),
        database.kupacDao(), database.zaposleniDao()) }
}