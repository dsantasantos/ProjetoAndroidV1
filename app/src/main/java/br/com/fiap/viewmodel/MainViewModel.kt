package br.com.fiap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.fiap.entidades.Cnh
import br.com.fiap.dados.repositorios.CnhRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val cnhRepository: CnhRepository
) : AndroidViewModel(application) {

    val allCnhs: LiveData<List<Cnh>> = cnhRepository.allCnhs

    fun insert(cnh: Cnh) = viewModelScope.launch(Dispatchers.IO) {
        cnhRepository.insert(cnh)
    }

    fun deleteById(id: String) = viewModelScope.launch(Dispatchers.IO) {
        cnhRepository.deleteById(id)
    }

    fun update(
        id: String,
        nomeCompleto: String,
        dataNascimento: Long,
        dataCnh: Long,
        dataVencCnh: Long,
        telefone: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        cnhRepository.update(id, nomeCompleto, dataNascimento, dataCnh, dataVencCnh, telefone)
    }
}

