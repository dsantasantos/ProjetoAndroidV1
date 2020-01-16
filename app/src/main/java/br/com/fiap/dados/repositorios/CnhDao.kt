package br.com.fiap.dados.repositorios

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fiap.entidades.Cnh

@Dao
interface CnhDao {

    @Query("SELECT * from cnh ORDER BY cnh_expired DESC")
    fun getAllCnhs(): LiveData<List<Cnh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cnh: Cnh)

    @Query("DELETE FROM cnh WHERE cnh_number = :id")
    fun deleteById(id: String)

    @Query("UPDATE cnh SET full_name=:full_name, birthdate=:birthdate, cnh_date=:cnh_date, cnh_expired=:cnh_expired, telephone=:telephone WHERE cnh_number = :id")
    fun update(
        id: String,
        full_name: String,
        birthdate: Long,
        cnh_date: Long,
        cnh_expired: Long,
        telephone: String
    )

    @Query("DELETE FROM cnh")
    fun deleteAll()
}