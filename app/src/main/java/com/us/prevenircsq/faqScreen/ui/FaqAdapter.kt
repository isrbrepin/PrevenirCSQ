package com.us.prevenircsq.faqScreen.ui

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.us.prevenircsq.R
import com.us.prevenircsq.faqScreen.ui.model.FaqItem

class PreguntaAdapter(
    private var preguntas: List<FaqItem>,
    private val onPreguntaClick: (Int) -> Unit
) : RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pregunta, parent, false)
        return PreguntaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        holder.bind(preguntas[position])

        // Manejar el click en el item para expandir o colapsar
        holder.itemView.setOnClickListener {
            val pregunta = preguntas[position]
            pregunta.expandida = !pregunta.expandida
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = preguntas.size

    fun updateData(nuevasPreguntas: List<FaqItem>) {
        preguntas = nuevasPreguntas
        notifyDataSetChanged()
    }

    class PreguntaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tituloTextView: TextView = view.findViewById(R.id.tituloTextView)
        private val descripcionTextView: TextView = view.findViewById(R.id.descripcionTextView)
        private val expandArrow: ImageView = view.findViewById(R.id.expandArrow)

        fun bind(pregunta: FaqItem) {
            tituloTextView.text = pregunta.titulo
            descripcionTextView.text = pregunta.descripcion

            // Establecemos alpha a 0 si está oculto para evitar que aparezca de golpe al expandir
            if (!pregunta.expandida) {
                descripcionTextView.alpha = 0f
                descripcionTextView.visibility = View.GONE
                expandArrow.rotation = 0f
            }

            // Mostrar u ocultar con animación dependiendo del estado expandido
            if (pregunta.expandida) {
                expandArrow.rotation = 180f
                descripcionTextView.visibility = View.VISIBLE
                descripcionTextView.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            } else {
                expandArrow.rotation = 0f
                descripcionTextView.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        descripcionTextView.visibility = View.GONE
                    }
                    .start()
            }
        }
    }
}
