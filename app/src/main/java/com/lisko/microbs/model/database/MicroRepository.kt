package com.lisko.microbs.model.database


import androidx.annotation.WorkerThread
import com.lisko.microbs.model.entities.Korisnik
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.model.entities.Zaposleni
import kotlinx.coroutines.flow.Flow

class MicroRepository(private val korisnikDao: KorisnikDao, private val kupacDao: KupacDao,
private val zaposleniDao: ZaposleniDao) {

    val allKorisnik: Flow<List<Korisnik>> = korisnikDao.getKorisnik()
    val allUsername: Flow<List<String>> = korisnikDao.getUsernames()

    val allZaposleni: Flow<List<Zaposleni>> = zaposleniDao.getZaposleni()
    val zaposleniSifre: Flow<List<String>> = zaposleniDao.getSifre()

    val allKupac: Flow<List<Kupac>> = kupacDao.getKupac()
    val kupacSifre: Flow<List<String>> = kupacDao.getSifreKupac()


    @WorkerThread
    suspend fun insertZaposleni(zaposleni: Zaposleni){
        zaposleniDao.insertZaposleni(zaposleni)
    }

    @WorkerThread
    suspend fun insertKorisnik(korisnik: Korisnik){
        korisnikDao.insertKorisnik(korisnik)
    }

    @WorkerThread
    suspend fun insertKupac(kupac:Kupac){
        kupacDao.insertKupac(kupac)
    }

    @WorkerThread
    suspend fun updateKupac(kupac: Kupac){
        kupacDao.updateKupac(kupac)
    }

    @WorkerThread
    suspend fun deleteKupac(kupac: Kupac){
        kupacDao.deleteKupac(kupac)
    }

    @WorkerThread
    fun filterKupac(sifra: String):Flow<List<Kupac>>{
       return kupacDao.filterKupac(sifra)
    }

}