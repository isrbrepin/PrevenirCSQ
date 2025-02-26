package com.us.prevenircsq.recommendationScreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.us.prevenircsq.R
import com.us.prevenircsq.bibliografiaScreen.ui.BibliografiaActivity

class TpnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tpn, container, false)

        val recommendationText: TextView = view.findViewById(R.id.recommendationTextView)
        val recommendationImage: ImageView = view.findViewById(R.id.recommendationImage)
        val recommendationImage2: ImageView = view.findViewById(R.id.recommendationImage2)
        val bibliografiaLink: TextView = view.findViewById(R.id.bibliografiaLink)
        val tipoPacienteTextView: TextView = view.findViewById(R.id.tipoPacienteTextView)
        val recommendationImageAposito: LinearLayout = view.findViewById(R.id.recommendationImageAposito)

        val recommendation = arguments?.getString("recommendation")
        val imageResource = arguments?.getInt("imageResource") ?: R.drawable.image_tpn

        recommendationText.text = recommendation
        recommendationImage.setImageResource(imageResource)

        if (recommendation == getString(R.string.tpn_de_un_solo_uso_durante_7_dias)) {
            bibliografiaLink.visibility = View.VISIBLE
            recommendationImage.visibility = View.VISIBLE
            recommendationImage2.visibility = View.VISIBLE
            tipoPacienteTextView.visibility = View.VISIBLE

            bibliografiaLink.setOnClickListener {
                startActivity(Intent(requireContext(), BibliografiaActivity::class.java))
            }
        } else {
            recommendationImageAposito.visibility = View.VISIBLE
        }

        return view
    }

    companion object {
        fun newInstance(recommendation: String, imageResource: Int): TpnFragment {
            val fragment = TpnFragment()
            val args = Bundle()
            args.putString("recommendation", recommendation)
            args.putInt("imageResource", imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}
