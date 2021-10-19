package com.lisko.microbs.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "korisnik_database")
data class Korisnik (
    @ColumnInfo val ime: String,
    @PrimaryKey val username: String,
    @ColumnInfo val password: String)