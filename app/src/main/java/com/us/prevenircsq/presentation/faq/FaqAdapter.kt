package com.us.prevenircsq.presentation.faq

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.us.prevenircsq.R
import com.us.prevenircsq.presentation.faq.model.FaqItem

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
        private var player: ExoPlayer? = null
        private val playerView: PlayerView = view.findViewById(R.id.playerView)
        private val btnFullScreen: Button = view.findViewById(R.id.btnFullScreen)

        fun bind(pregunta: FaqItem) {
            tituloTextView.text = pregunta.titulo
            descripcionTextView.text = pregunta.descripcion

            if (pregunta.imageResource != null) {
                descripcionTextView.visibility = View.GONE
                descripcionImageView.setImageResource(pregunta.imageResource)
                descripcionImageView.visibility =
                    if (pregunta.expandida) View.VISIBLE else View.GONE
            } else {
                descripcionTextView.text = pregunta.descripcion
                descripcionTextView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
                descripcionImageView.visibility = View.GONE
            }

            if (pregunta.imageResource2 != null) {
                descripcionTextView.visibility = View.GONE
                descripcionImageView2.setImageResource(pregunta.imageResource2)
                descripcionImageView2.visibility =
                    if (pregunta.expandida) View.VISIBLE else View.GONE
            } else {
                descripcionTextView.text = pregunta.descripcion
                descripcionTextView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
                descripcionImageView2.visibility = View.GONE
            }

            // Configuración del video
            if (pregunta.videoResource != null) {
                playerView.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE
                btnFullScreen.visibility = if (pregunta.expandida) View.VISIBLE else View.GONE

                if (player == null) {
                    player = ExoPlayer.Builder(itemView.context).build()
                    playerView.player = player
                }

                val videoUri =
                    Uri.parse("android.resource://" + itemView.context.packageName + "/" + pregunta.videoResource!!)
                val mediaItem = MediaItem.fromUri(videoUri)
                player?.setMediaItem(mediaItem)
                player?.prepare()
                player?.playWhenReady = false // Para que no inicie automáticamente
            } else {
                playerView.visibility = View.GONE
                btnFullScreen.visibility = View.GONE
            }

            btnFullScreen.setOnClickListener {
                val intent = Intent(itemView.context, VideoActivity::class.java)
                intent.putExtra("videoUri", pregunta.videoResource)
                itemView.context.startActivity(intent)
            }

        }
    }
}