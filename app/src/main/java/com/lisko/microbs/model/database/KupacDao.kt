package com.lisko.microbs.model.database

import androidx.room.*
import com.lisko.microbs.model.entities.Kupac
import kotlinx.coroutines.flow.Flow

@Dao
interface KupacDao {
    @Query("select * from kupac_database")
    fun getKupac(): Flow<List<Kupac>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKupac(kupac: Kupac)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateKupac(kupac: Kupac)

    @Delete
    suspend fun deleteKupac(kupac: Kupac)

    @Query("select * from kupac_database where zaposleni_sifra= :sifra")
    fun filterKupac(sifra: String): Flow<List<Kupac>>

    @Query("select sifra from kupac_database")
    fun getSifreKupac(): Flow<List<String>>


}