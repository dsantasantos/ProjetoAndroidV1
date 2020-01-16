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
import kotlinx.android.synthetic.main.activity_list_cnh.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ListCnhActivity : AppCompatActivity(), CnhListAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onItemClick(texto: String, objeto: Cnh) {

        val format = SimpleDateFormat("dd/MM/yyyy")

        val intent = Intent(this, NewCnhActivity::class.java)
        intent.putExtra("full_name", objeto.full_name)
        intent.putExtra("birthdate", format.format(objeto.birthdate))
        intent.putExtra("cnh_number", objeto.cnh_number)
        intent.putExtra("cnh_date", format.format(objeto.cnh_date))
        intent.putExtra("cnh_expired", format.format(objeto.cnh_expired))
        intent.putExtra("telephone", objeto.telephone)

        startActivity(intent)
    }

    override fun onTelephoneClick(texto: String, objeto: Cnh) {
        val uri = Uri.parse("tel:" + objeto.telephone)
        val intent = Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }

    override fun onShareClick(texto: String, objeto: Cnh) {
        val sendIntent = Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        val textoCompartilhado =
            "${resources.getString(R.string.screen_new_cnh_full_name)}: ${objeto.full_name} - ${resources.getString(
                R.string.screen_new_cnh_number
            )}: ${objeto.cnh_number} - ${resources.getString(R.string.screen_new_cnh_telephone)}: ${objeto.telephone}"
        sendIntent.putExtra(Intent.EXTRA_TEXT, textoCompartilhado);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val adapter = CnhListAdapter(this, this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cnh)
        setSupportActionBar(toolbar)

        setUpList(adapter)

        mainViewModel.allCnhs.observe(this, Observer { cnhs ->
            cnhs?.let { adapter.setCnhs(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewCnhActivity::class.java)
            intent.putExtra("cnh_number", "")
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

