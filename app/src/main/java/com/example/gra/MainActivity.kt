package com.example.gra

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
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

            square.setOnClickListener {
                changeColor(it)
                checkWin(grid)
            }
        }
    }

    private fun changeColor(view: View) {
        val background = view.background as ColorDrawable
        val currentColor = background.color
        val currentIndex = colors.indexOf(currentColor)
        val nextIndex = (currentIndex + 1) % colors.size
        view.setBackgroundColor(colors[nextIndex])
    }

    private fun checkWin(grid: GridLayout) {
        val firstSquare = grid.getChildAt(0)
        val firstColor = (firstSquare.background as ColorDrawable).color

        var allMatch = true
        for (i in 0 until grid.childCount) {
            val currentColor = (grid.getChildAt(i).background as ColorDrawable).color
            if (currentColor != firstColor) {
                allMatch = false
                break
            }
        }

        if (allMatch) {
            showRestartDialog(grid)
        }
    }

    private fun showRestartDialog(grid: GridLayout) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Перемога!")
        builder.setMessage("Усі квадрати стали одного кольору!")
        builder.setCancelable(false)
        builder.setPositiveButton("Restart") { _, _ ->
            setupGame(grid)
        }
        builder.show()
    }
}