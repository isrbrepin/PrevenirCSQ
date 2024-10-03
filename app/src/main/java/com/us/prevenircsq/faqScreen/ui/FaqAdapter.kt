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
            // Actualizamos el estado de expansión de la pregunta
            val pregunta = preguntas[position]
            pregunta.expandida = !pregunta.expandida
            notifyItemChanged(position) // Notificamos que este ítem ha cambiado
        }
    }

    override fun getItemCount(): Int = preguntas.size

    // Actualizar la lista de preguntas
    fun updateData(nuevasPreguntas: List<FaqItem>) {
        preguntas = nuevasPreguntas
        notifyDataSetChanged() // Notificar que los datos han cambiado
    }

    class PreguntaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tituloTextView: TextView = view.findViewById(R.id.tituloTextView)
        private val descripcionTextView: TextView = view.findViewById(R.id.descripcionTextView)
        private val expandArrow: ImageView = view.findViewById(R.id.expandArrow)

        fun bind(pregunta: FaqItem) {
            tituloTextView.text = pregunta.titulo
            descripcionTextView.text = pregunta.descripcion

            // Si está expandido, mostramos la descripción con su altura completa y rotamos la flecha
            if (pregunta.expandida) {
                expandArrow.rotation = 180f
                if (descripcionTextView.visibility == View.GONE) {
                    expandTextViewWithAnimation(descripcionTextView)
                }
            } else {
                // Si no está expandido, ocultamos la descripción y restauramos la flecha
                expandArrow.rotation = 0f
                collapseTextViewWithAnimation(descripcionTextView)
            }
        }

        private fun expandTextViewWithAnimation(view: View) {
            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val targetHeight = view.measuredHeight

            view.layoutParams.height = 0
            view.isVisible = true
            val animator = ValueAnimator.ofInt(0, targetHeight)
            animator.addUpdateListener { valueAnimator ->
                val layoutParams = view.layoutParams
                layoutParams.height = valueAnimator.animatedValue as Int
                view.layoutParams = layoutParams
            }
            animator.duration = 300  // Duración de la animación
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }

        private fun collapseTextViewWithAnimation(view: View) {
            val initialHeight = view.measuredHeight

            val animator = ValueAnimator.ofInt(initialHeight, 0)
            animator.addUpdateListener { valueAnimator ->
                val layoutParams = view.layoutParams
                layoutParams.height = valueAnimator.animatedValue as Int
                view.layoutParams = layoutParams
            }
            animator.addListener(onEnd = {
                view.isVisible = false
            })
            animator.duration = 300  // Duración de la animación
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }
    }
}

