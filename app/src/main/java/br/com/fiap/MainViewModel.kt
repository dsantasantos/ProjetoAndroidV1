package br.com.fiap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
class MainViewModel(application: Application, private val wordRepository: WordRepository) : AndroidViewModel(application) {

    val allWords: LiveData<List<Word>> = wordRepository.allWords

    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        wordRepository.insert(word)
    }
}
 */

class MainViewModel(
    application: Application,
    private val testeTabelaRepository: TesteTabelaRepository
) : AndroidViewModel(application) {

    val allWords: LiveData<List<TesteTabela>> = testeTabelaRepository.allWords

    fun insert(testeTabela: TesteTabela) = viewModelScope.launch(Dispatchers.IO) {
        testeTabelaRepository.insert(testeTabela)
    }

    fun deleteById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        testeTabelaRepository.deleteById(id)
    }

    fun update(
        id: String,
        nomeCompleto: String,
        dataNascimento: Long,
        dataCnh: Long,
        dataVencCnh: Long
    ) = viewModelScope.launch(Dispatchers.IO) {
        testeTabelaRepository.update(id, nomeCompleto, dataNascimento, dataCnh, dataVencCnh)
    }
}

