package br.com.fiap

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "teste_tabela")
class TesteTabela(
    @PrimaryKey @ColumnInfo(name = "nome_completo") val nomeCompleto: String,
    @ColumnInfo(name = "data_nascimento") val dataNascimento: Long,
    @ColumnInfo(name = "numero_cnh") val numeroCnh: String,
    @ColumnInfo(name = "data_cnh") val dataCnh: Long,
    @ColumnInfo(name = "data_venc_cnh") val dataVencCnh: Long
)