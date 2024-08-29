package com.entrolabs.sampleapplication.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Spinner
import android.widget.TextView
import android.graphics.Color
import com.entrolabs.sampleapplication.R

class TestActivity : AppCompatActivity() {
    private lateinit var numberGrid: GridLayout
    private lateinit var ruleSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        numberGrid = findViewById(R.id.number_grid)
        ruleSpinner = findViewById(R.id.rule_spinner)

        // Set up the spinner with rules
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.rules_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ruleSpinner.adapter = adapter

        // Initialize grid
        initializeGrid()

        // Set up spinner listener
        ruleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                applyRule(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }
    private fun initializeGrid() {
        numberGrid.removeAllViews()
        numberGrid.rowCount = 10
        numberGrid.columnCount = 10

        for (i in 1..100) {
            val textView = TextView(this)
            textView.text = i.toString()
            textView.textSize = 16f
            textView.setPadding(8, 8, 8, 8)
            textView.gravity = Gravity.CENTER
            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                rowSpec = GridLayout.spec((i - 1) / 10)
                columnSpec = GridLayout.spec((i - 1) % 10)
                setMargins(4, 4, 4, 4)
            }
            textView.layoutParams = params
            numberGrid.addView(textView)
        }
    }

    private fun applyRule(rulePosition: Int) {
        for (i in 0 until numberGrid.childCount) {
            val textView = numberGrid.getChildAt(i) as TextView
            val number = textView.text.toString().toInt()

            when (rulePosition) {
                0 -> textView.setBackgroundColor(Color.TRANSPARENT) // Default
                1 -> if (number % 3 == 0) textView.setBackgroundColor(Color.YELLOW) // Highlight multiples of 3
                2 -> if (isPrime(number)) textView.setBackgroundColor(Color.GREEN) // Highlight primes
            }
        }
    }

    private fun isPrime(number: Int): Boolean {
        if (number <= 1) return false
        for (i in 2..Math.sqrt(number.toDouble()).toInt()) {
            if (number % i == 0) return false
        }
        return true
    }


}