package br.com.fiap.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "cnh")
class Cnh(
    @ColumnInfo(name = "full_name") val full_name: String,
    @ColumnInfo(name = "birthdate") val birthdate: Long,
    @PrimaryKey @ColumnInfo(name = "cnh_number") val cnh_number: String,
    @ColumnInfo(name = "cnh_date") val cnh_date: Long,
    @ColumnInfo(name = "cnh_expired") val cnh_expired: Long,
    @ColumnInfo(name = "telephone") val telephone: String
)