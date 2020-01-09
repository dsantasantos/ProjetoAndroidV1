package br.com.fiap

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "teste_tabela")
class TesteTabela(@PrimaryKey @ColumnInfo(name = "campo") val campo: String, @ColumnInfo(name = "first_name") val firstName: Long)