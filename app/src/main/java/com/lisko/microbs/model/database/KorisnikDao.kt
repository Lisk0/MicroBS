package com.lisko.microbs.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lisko.microbs.model.entities.Korisnik
import kotlinx.coroutines.flow.Flow

@Dao
interface KorisnikDao {

    @Query("select * from korisnik_database")
    fun getKorisnik(): Flow<List<Korisnik>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKorisnik(korisnik: Korisnik)

    @Query("select username from korisnik_database")
    fun getUsernames(): Flow<List<String>>

}