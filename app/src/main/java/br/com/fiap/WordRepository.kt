package br.com.fiap

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.com.fiap.WordDao
import br.com.fiap.Word

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}