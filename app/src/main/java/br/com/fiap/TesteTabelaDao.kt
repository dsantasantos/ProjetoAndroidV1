package br.com.fiap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TesteTabelaDao {

    @Query("SELECT * from teste_tabela ORDER BY data_venc_cnh DESC")
    fun getAllWords(): LiveData<List<TesteTabela>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(testeTabela: TesteTabela)

    @Query("DELETE FROM teste_tabela")
    fun deleteAll()
}