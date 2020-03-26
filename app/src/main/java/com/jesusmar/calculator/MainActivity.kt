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

    fun onOperator(view: View){
        var negativeNumber = false
        var tvValue = tvInput.text.toString()
        val buttonText = (view as Button).text.toString()

        if (tvValue.startsWith('-')){
            negativeNumber = true
            tvValue = tvValue.substring(1, tvValue.length)
        } else

        if (tvValue.isEmpty()
            && buttonText.equals("-")){
            tvInput.append((view as Button).text)
            return
        }

        val operators = listOf<Char>('*','-','+','/')
        val hasOneInText: (CharSequence, Char) -> Boolean = { text: CharSequence, char: Char -> text.contains(char) }
        if (tvInput.text.isNotEmpty()
            && !operators.any { o -> hasOneInText(tvValue,o) }) {
            tvInput.append((view as Button).text)
        }

        if (negativeNumber) {
            tvValue = "-$tvValue"
        }
    }

    fun onEqual(view: View){
        var negativeNumber = false
        var tvValue = tvInput.text.toString()

        if (tvValue.startsWith('-')){
            negativeNumber = true
            tvValue = tvValue.substring(1, tvValue.length)
        }

        if (isValidCalculation(tvValue)){
            val match = "[-|+|*|/]{1}".toRegex().find(tvValue)!!
            val operator = match.value
            val tvSplited = tvValue.split(operator)

            val first = if (negativeNumber) "-" + tvSplited[0] else tvSplited[0]

            val result = doCalculation(first.toDouble(), tvSplited[1].toDouble(), operator)

            tvInput.text = result.toString()

        }



    }

    private fun doCalculation(valueLeft: Double, valueRight: Double, operator: String): Double{
        when (operator) {
            "-" -> return valueLeft - valueRight
            "+" -> return valueLeft + valueRight
            "*" -> return valueLeft * valueRight
            else -> return valueLeft / valueRight
        }
    }

    private fun isValidCalculation(tvValue: String): Boolean {
        val regExp = "^[-+]?\\d*\\.?\\d+[-|+|*|/]{1}[-+]?\\d*\\.?\\d+\$".toRegex()
        return regExp.matchEntire(tvValue)?.groupValues != null
    }

}
