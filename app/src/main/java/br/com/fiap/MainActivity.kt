package br.com.fiap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), WordListAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onItemClick(texto: String, objeto: TesteTabela) {

        val format = SimpleDateFormat("dd/MM/yyyy")

        val intent = Intent(this, CadastroCnhActivity::class.java)
        intent.putExtra("nome_completo", objeto.nomeCompleto)
        intent.putExtra("data_nascimento", format.format(objeto.dataNascimento))
        intent.putExtra("numero_cnh", objeto.numeroCnh)
        intent.putExtra("data_cnh", format.format(objeto.dataCnh))
        intent.putExtra("data_venc_cnh", format.format(objeto.dataVencCnh))

        startActivity(intent)
        //finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val adapter = WordListAdapter(this, this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setUpList(adapter)

        mainViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
        })

        fab.setOnClickListener { view ->
            //val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            //startActivityForResult(intent, newWordActivityRequestCode)
            startActivity(Intent(this, CadastroCnhActivity::class.java))
        }
    }

    private fun setUpList(adapter: WordListAdapter) {
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                // val word = TesteTabela(it.getStringExtra(NewWordActivity.EXTRA_REPLY))
                // mainViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
    }
}

