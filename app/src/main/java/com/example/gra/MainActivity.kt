package com.example.gra

import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val grid = findViewById<GridLayout>(R.id.myGrid)

        setupGame(grid)
    }

    private fun setupGame(grid: GridLayout) {

        for (i in 0 until grid.childCount) {
            val square = grid.getChildAt(i)

            square.setBackgroundColor(colors.random())
        }
    }
}