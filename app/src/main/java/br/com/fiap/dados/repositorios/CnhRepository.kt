package br.com.fiap.dados.repositorios

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.com.fiap.entidades.Cnh

class CnhRepository(private val cnhDao: CnhDao) {

    val allCnhs: LiveData<List<Cnh>> = cnhDao.getAllCnhs()

    @WorkerThread
    suspend fun insert(cnh: Cnh) {
        cnhDao.insert(cnh)
    }

    fun update(
        id: String,
        nomeCompleto: String,
        dataNascimento: Long,
        dataCnh: Long,
        dataVencCnh: Long,
        telephone: String
    ) {
        cnhDao.update(id, nomeCompleto, dataNascimento, dataCnh, dataVencCnh, telephone)
    }

    fun deleteById(id: String) {
        cnhDao.deleteById(id)
    }

}