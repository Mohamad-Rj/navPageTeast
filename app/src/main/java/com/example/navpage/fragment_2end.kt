package com.example.navpage
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_2end.*

class fragment_2end : Fragment(R.layout.fragment_2end){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_next_end2.setOnClickListener{
          val intent =Intent(context,sing_up::class.java)
            startActivity(intent)
        }

    }
}