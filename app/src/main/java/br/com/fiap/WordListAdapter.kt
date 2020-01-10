package br.com.fiap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Date
import java.text.SimpleDateFormat

class WordListAdapter internal constructor(
    context: Context, private val mListener: OnItemClickListener?
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var testes = emptyList<TesteTabela>() // Cached copy of words

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = testes[position]

        val dataVencimento = Date(current.dataVencCnh)
        val format = SimpleDateFormat("dd/MM/yyyy")
        val dataVencimentoFormatada = format.format(dataVencimento)

        val texto = "${current.nomeCompleto} - Vencimento: $dataVencimentoFormatada"
        holder.wordItemView.text = texto

        holder.wordItemView.setOnClickListener {
            mListener?.onItemClick(texto, testes[position])
        }

        //holder.wordItemView.text = current.firstName
    }

    internal fun setWords(testes: List<TesteTabela>) {
        this.testes = testes
        notifyDataSetChanged()
    }


    override fun getItemCount() = testes.size

    interface OnItemClickListener {
        fun onItemClick(texto: String, objeto: TesteTabela)
    }


}