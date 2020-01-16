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
    private var records = emptyList<Cnh>()

    interface OnItemClickListener {
        fun onItemClick(texto: String, objeto: Cnh)
        fun onTelephoneClick(texto: String, objeto: Cnh)
        fun onShareClick(texto: String, objeto: Cnh)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class CnhViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cnhItemView: TextView = itemView.findViewById(R.id.textView)
        val telephoneImageView: ImageView = itemView.findViewById(R.id.imageView)
        val compartilharImageView: ImageView = itemView.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CnhViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CnhViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CnhViewHolder, position: Int) {

        val current = records[position]

        val cnh_expired = Date(current.cnh_expired)
        val format = SimpleDateFormat("dd/MM/yyyy")
        val dataVencimentoFormatada = format.format(cnh_expired)

        val texto = "${current.full_name} - Vencimento: $dataVencimentoFormatada"
        holder.cnhItemView.text = texto

        holder.cnhItemView.setOnClickListener {
            mListener?.onItemClick(texto, records[position])
        }

        holder.telephoneImageView.setOnClickListener {
            mListener?.onTelephoneClick(texto, records[position])
        }

        holder.compartilharImageView.setOnClickListener {
            mListener?.onShareClick(texto, records[position])
        }

    }

    internal fun setCnhs(records: List<Cnh>) {
        this.records = records
        notifyDataSetChanged()
    }

    override fun getItemCount() = records.size

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        this.records = this.records.drop(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    fun getId(position: Int): String {
        return records[position].cnh_number
    }


}