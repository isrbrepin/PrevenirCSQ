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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pregunta, parent, false)
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
        private val descripcionImageView: ImageView = view.findViewById(R.id.descripcionImageView)
        private val descripcionImageView2: ImageView = view.findViewById(R.id.descripcionImageView2)
        private val expandArrow: ImageView = view.findViewById(R.id.expandArrow)

        fun bind(pregunta: FaqItem) {
            tituloTextView.text = pregunta.titulo
            descripcionTextView.text = pregunta.descripcion

            if (pregunta.imageResource != null) {
                descripcionTextView.visibility = View.GONE
                descripcionImageView.setImageResource(pregunta.imageResource)
                descripcionImageView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
            } else {
                descripcionTextView.text = pregunta.descripcion
                descripcionTextView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
                descripcionImageView.visibility = View.GONE
            }

            if (pregunta.imageResource2 != null) {
                descripcionTextView.visibility = View.GONE
                descripcionImageView2.setImageResource(pregunta.imageResource2)
                descripcionImageView2.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
            } else {
                descripcionTextView.text = pregunta.descripcion
                descripcionTextView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
                descripcionImageView2.visibility = View.GONE
            }

            // Mostrar/ocultar la descripción sincronizando con la rotación
            if (pregunta.expandida) {
                expandArrow.rotation = 0f
                descripcionTextView.isVisible = true
                animateArrowRotation(0f, 180f)
                animateDescriptionAlpha(0f, 1f)
                animateExpandCollapse(descripcionTextView, true)
            } else {
                animateArrowRotation(180f, 0f)
                animateDescriptionAlpha(1f, 0f)
                animateExpandCollapse(descripcionTextView, false)
            }
        }

        private fun animateExpandCollapse(view: View, expand: Boolean) {
            if (expand) {
                view.visibility = View.VISIBLE
                view.alpha = 0f // Inicialmente transparente
            }

            // Aseguramos que medimos correctamente el ancho disponible antes de medir la altura
            val widthSpec = View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.AT_MOST)
            val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(widthSpec, heightSpec)

            val targetHeight = view.measuredHeight // Medimos la altura final del contenido

            // Evitar que animemos vistas con altura 0 o excesivamente grandes
            if (targetHeight == 0 || targetHeight > 1000) {
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                view.alpha = 1f // Aparece completamente sin animación
                return
            }

            // Creamos la animación de altura
            val animator = ValueAnimator.ofInt(if (expand) 0 else targetHeight, if (expand) targetHeight else 0).apply {
                duration = 300 // Ajustamos la duración a 300ms para mayor fluidez
                interpolator = AccelerateDecelerateInterpolator()

                addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    view.layoutParams.height = value
                    view.requestLayout() // Actualizamos el layout durante la animación
                }

                addListener(onEnd = {
                    // Cuando la animación de colapsar termina, escondemos la vista
                    if (!expand) {
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT // Al expandir, restablecemos WRAP_CONTENT
                    }
                })
            }

            // Iniciamos las dos animaciones al mismo tiempo
            animator.start()

            // Ahora animamos el alpha para hacer la transición más suave
            if (expand) {
                view.animate()
                    .alpha(1f) // Aparece
                    .setDuration(300) // Duración de la animación
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            } else {
                view.animate()
                    .alpha(0f) // Desaparece
                    .setDuration(300) // Hacemos que la desaparición sea un poco más rápida
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            }
        }




        // Método para animar la rotación de la flecha
        private fun animateArrowRotation(from: Float, to: Float) {
            val animator = ValueAnimator.ofFloat(from, to).apply {
                duration = 500 // Duración de la animación
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    expandArrow.rotation = value
                }
                start()
            }
        }

        // Método para animar la aparición/desaparición de la descripción
        private fun animateDescriptionAlpha(from: Float, to: Float) {
            descripcionTextView.alpha = from
            descripcionTextView.animate()
                .alpha(to)
                .setDuration(0) // Misma duración que la rotación
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }
}