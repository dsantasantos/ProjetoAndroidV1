package br.com.fiap.utilitarios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.R
import br.com.fiap.entidades.Cnh
import java.sql.Date
import java.text.SimpleDateFormat

class CnhListAdapter internal constructor(
    context: Context, private val mListener: OnItemClickListener?
) : RecyclerView.Adapter<CnhListAdapter.CnhViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var testes = emptyList<Cnh>()

    interface OnItemClickListener {
        fun onItemClick(texto: String, objeto: Cnh)
        fun onTelefoneClick(texto: String, objeto: Cnh)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class CnhViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cnhItemView: TextView = itemView.findViewById(R.id.textView)
        val telefoneImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CnhViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CnhViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CnhViewHolder, position: Int) {

        val current = testes[position]

        val dataVencimento = Date(current.dataVencCnh)
        val format = SimpleDateFormat("dd/MM/yyyy")
        val dataVencimentoFormatada = format.format(dataVencimento)

        val texto = "${current.nomeCompleto} - Vencimento: $dataVencimentoFormatada"
        holder.cnhItemView.text = texto

        holder.cnhItemView.setOnClickListener {
            mListener?.onItemClick(texto, testes[position])
        }

        holder.telefoneImageView.setOnClickListener {
            mListener?.onTelefoneClick(texto, testes[position])
        }

    }

    internal fun setCnhs(testes: List<Cnh>) {
        this.testes = testes
        notifyDataSetChanged()
    }

    override fun getItemCount() = testes.size

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        this.testes = this.testes.drop(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    fun getId(position: Int): String {
        return testes[position].numeroCnh
    }


}