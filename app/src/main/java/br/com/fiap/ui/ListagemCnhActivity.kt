package br.com.fiap.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.R
import br.com.fiap.entidades.Cnh
import br.com.fiap.utilitarios.CnhListAdapter
import br.com.fiap.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_listagem_cnh.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ListagemCnhActivity : AppCompatActivity(), CnhListAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onItemClick(texto: String, objeto: Cnh) {

        val format = SimpleDateFormat("dd/MM/yyyy")

        val intent = Intent(this, CadastroCnhActivity::class.java)
        intent.putExtra("nome_completo", objeto.nomeCompleto)
        intent.putExtra("data_nascimento", format.format(objeto.dataNascimento))
        intent.putExtra("numero_cnh", objeto.numeroCnh)
        intent.putExtra("data_cnh", format.format(objeto.dataCnh))
        intent.putExtra("data_venc_cnh", format.format(objeto.dataVencCnh))
        intent.putExtra("telefone", objeto.telefone)

        startActivity(intent)
    }

    override fun onTelefoneClick(texto: String, objeto: Cnh) {
        val uri = Uri.parse("tel:" + objeto.telefone)
        val intent = Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }

    override fun onCompartilharClick(texto: String, objeto: Cnh) {
        val sendIntent = Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        val textoCompartilhado =
            "Nome: ${objeto.nomeCompleto} - CNH: ${objeto.numeroCnh} - Telefone: ${objeto.nomeCompleto}"
        sendIntent.putExtra(Intent.EXTRA_TEXT, textoCompartilhado);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val adapter = CnhListAdapter(this, this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_cnh)
        setSupportActionBar(toolbar)

        setUpList(adapter)

        mainViewModel.allCnhs.observe(this, Observer { cnhs ->
            cnhs?.let { adapter.setCnhs(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, CadastroCnhActivity::class.java)
            intent.putExtra("numero_cnh", "")
            startActivity(intent)
        }

        ivNewBack.setOnClickListener {
            onBackPressed()
        }

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val id = adapter.getId(viewHolder.adapterPosition)
                    mainViewModel.deleteById(id)
                    adapter.removeItem(viewHolder)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)

    }

    private fun setUpList(adapter: CnhListAdapter) {
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
    }
}

