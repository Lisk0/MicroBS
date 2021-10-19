package com.lisko.microbs.model.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "kupac_database",
    foreignKeys = [
        ForeignKey(entity = Zaposleni::class,
            parentColumns = ["sifra"],
            childColumns = ["zaposleni_sifra"],
            onDelete = CASCADE)])
data class Kupac (
    @ColumnInfo val naziv: String,
    @ColumnInfo val pib: String,
    @PrimaryKey val sifra: String,
    @ColumnInfo val zaposleni_sifra: String):Parcelable