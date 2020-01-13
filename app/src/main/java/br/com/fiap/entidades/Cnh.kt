package br.com.fiap.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "cnh")
class Cnh(
    @ColumnInfo(name = "nome_completo") val nomeCompleto: String,
    @ColumnInfo(name = "data_nascimento") val dataNascimento: Long,
    @PrimaryKey @ColumnInfo(name = "numero_cnh") val numeroCnh: String,
    @ColumnInfo(name = "data_cnh") val dataCnh: Long,
    @ColumnInfo(name = "data_venc_cnh") val dataVencCnh: Long,
    @ColumnInfo(name = "telefone") val telefone: String
)