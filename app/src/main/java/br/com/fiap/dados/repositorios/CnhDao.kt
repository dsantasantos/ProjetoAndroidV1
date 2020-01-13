package br.com.fiap.dados.repositorios

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fiap.entidades.Cnh

@Dao
interface CnhDao {

    @Query("SELECT * from cnh ORDER BY data_venc_cnh DESC")
    fun getAllCnhs(): LiveData<List<Cnh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cnh: Cnh)

    @Query("DELETE FROM cnh WHERE numero_cnh = :id")
    fun deleteById(id: String)

    @Query("UPDATE cnh SET nome_completo=:nomeCompleto, data_nascimento=:dataNascimento, data_cnh=:dataCnh, data_venc_cnh=:dataVencCnh, telefone=:telefone WHERE numero_cnh = :id")
    fun update(id: String, nomeCompleto: String, dataNascimento: Long, dataCnh: Long, dataVencCnh: Long, telefone: String)

    @Query("DELETE FROM cnh")
    fun deleteAll()
}