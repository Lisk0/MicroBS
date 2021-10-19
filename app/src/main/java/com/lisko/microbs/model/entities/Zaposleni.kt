package com.lisko.microbs.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "zaposleni_database")
data class Zaposleni (
    @ColumnInfo val ime: String,
    @ColumnInfo val prezime: String,
    @PrimaryKey val sifra: String,
    @ColumnInfo val grad: String,
    @ColumnInfo val magacin: String): Parcelable