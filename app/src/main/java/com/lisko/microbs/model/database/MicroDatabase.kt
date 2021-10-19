package com.lisko.microbs.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lisko.microbs.model.entities.Korisnik
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.model.entities.Zaposleni

@Database(entities = [Korisnik::class, Kupac::class, Zaposleni::class], version = 1)
abstract class MicroDatabase: RoomDatabase() {

    abstract fun korisnikDao(): KorisnikDao
    abstract fun kupacDao(): KupacDao
    abstract fun zaposleniDao(): ZaposleniDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MicroDatabase? = null

        fun getDatabase(context: Context): MicroDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MicroDatabase::class.java,
                    "micro_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}