package com.jesusmar.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view:View){
        tvInput.append((view as Button).text)
    }

    fun onClear(view: View){
        tvInput.text = ""
    }

    fun onDot(view: View){
        if (tvInput.text.length > 0
            && tvInput.text.last() != '.'
            && !tvInput.text.contains('.', true)) {
                tvInput.append(".")
        }
    }
}
