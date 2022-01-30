package com.example.navpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class Fragment_home : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_next.setOnClickListener {
            val action = Fragment_homeDirections.actionFragmentHomeToFragment2end()
            it.findNavController().navigate(action)

        }


    }
}
