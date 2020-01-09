package br.com.fiap

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TesteTabelaRepository(private val testeTabelaDao: TesteTabelaDao) {

    val allWords: LiveData<List<TesteTabela>> = testeTabelaDao.getAllWords()

    @WorkerThread
    suspend fun insert(testeTabela: TesteTabela) {
        testeTabelaDao.insert(testeTabela)
    }
}