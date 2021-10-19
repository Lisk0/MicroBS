package com.lisko.microbs.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lisko.microbs.model.entities.Zaposleni
import kotlinx.coroutines.flow.Flow

@Dao
interface ZaposleniDao {
    @Query("select * from zaposleni_database")
    fun getZaposleni(): Flow<List<Zaposleni>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertZaposleni(zaposleni: Zaposleni)

    @Query("select sifra from zaposleni_database")
    fun getSifre(): Flow<List<String>>

}
