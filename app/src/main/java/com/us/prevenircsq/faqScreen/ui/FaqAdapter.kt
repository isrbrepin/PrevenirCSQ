package com.us.prevenircsq.faqScreen.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.us.prevenircsq.R
import com.us.prevenircsq.faqScreen.ui.model.FaqItem

class PreguntaAdapter(
    private var preguntas: List<FaqItem>,  // Cambiar a var para permitir actualizar la lista
    private val onPreguntaClick: (Int) -> Unit
) : RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pregunta, parent, false)
        return PreguntaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        holder.bind(preguntas[position])
        holder.itemView.setOnClickListener {
            onPreguntaClick(position)
        }
    }

    override fun getItemCount(): Int = preguntas.size

    // Función para actualizar la lista de preguntas
    fun updateData(nuevasPreguntas: List<FaqItem>) {
        preguntas = nuevasPreguntas
        notifyDataSetChanged()  // Notifica al adaptador que los datos han cambiado
    }

    class PreguntaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tituloTextView: TextView = view.findViewById(R.id.tituloTextView)
        private val descripcionTextView: TextView = view.findViewById(R.id.descripcionTextView)
        private val container: View = view.findViewById(R.id.preguntaContainer)
        private val expandArrow: ImageView = view.findViewById(R.id.expandArrow)  // Flecha de expandir/colapsar

        fun bind(pregunta: FaqItem) {
            tituloTextView.text = pregunta.titulo
            descripcionTextView.text = pregunta.descripcion

            // Mostrar/ocultar la descripción
            descripcionTextView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE


            // Rotar la flecha hacia abajo o hacia arriba
            expandArrow.rotation = if (pregunta.expandida) 180f else 0f
        }
    }
}



