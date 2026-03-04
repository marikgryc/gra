package com.example.gra

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)

    private var currentLevel = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val grid = findViewById<GridLayout>(R.id.myGrid)
        setupGame(grid)
    }

    private fun setupGame(grid: GridLayout) {
        Toast.makeText(this, "Рівень $currentLevel почався!", Toast.LENGTH_SHORT).show()

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
        val isWin = when (currentLevel) {
            1 -> checkLevel1(grid)
            2 -> checkLevel2(grid)
            3 -> checkLevel3(grid)
            4 -> checkLevel4(grid)
            else -> false
        }

        if (isWin) {
            showLevelCompleteDialog(grid)
        }
    }


    private fun checkLevel1(grid: GridLayout): Boolean {
        val firstColor = (grid.getChildAt(0).background as ColorDrawable).color
        for (i in 1 until grid.childCount) {
            val currentColor = (grid.getChildAt(i).background as ColorDrawable).color
            if (currentColor != firstColor) return false
        }
        return true
    }

    private fun checkLevel2(grid: GridLayout): Boolean {
        for (i in 0 until grid.childCount) {
            val color = (grid.getChildAt(i).background as ColorDrawable).color
            when (i % 3) {
                0 -> if (color != Color.YELLOW) return false
                1 -> if (color != Color.RED) return false
                2 -> if (color != Color.GREEN) return false
            }
        }
        return true
    }
    private fun checkLevel3(grid: GridLayout): Boolean {
        for (row in 0 until 5) {
            var redCount = 0
            for (col in 0 until 3) {
                val index = row * 3 + col
                val color = (grid.getChildAt(index).background as ColorDrawable).color
                if (color == Color.RED) {
                    redCount++
                }
            }
            if (redCount != 1) return false
        }
        return true
    }
    private fun checkLevel4(grid: GridLayout): Boolean {
        for (i in 0 until grid.childCount) {
            val color = (grid.getChildAt(i).background as ColorDrawable).color
            val row = i / 3
            val col = i % 3

            if (col < 2) {
                val rightColor = (grid.getChildAt(i + 1).background as ColorDrawable).color
                if (color == rightColor) return false
            }

            if (row < 4) {
                val bottomColor = (grid.getChildAt(i + 3).background as ColorDrawable).color
                if (color == bottomColor) return false
            }
        }
        return true
    }
    private fun showLevelCompleteDialog(grid: GridLayout) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Рівень $currentLevel пройдено!")
        builder.setMessage("Чудова робота! Що далі?")
        builder.setCancelable(false)


        builder.setPositiveButton("Наступний") { _, _ ->
            if (currentLevel < 4) {
                currentLevel++
                setupGame(grid)
            } else {
                Toast.makeText(this, "Вітаємо! Ви пройшли всі рівні!", Toast.LENGTH_LONG).show()
                currentLevel = 1
                setupGame(grid)
            }
        }

        builder.setNeutralButton("Спочатку") { _, _ ->
            setupGame(grid)
        }

        builder.setNegativeButton("Вийти") { _, _ ->
            finish()
        }

        builder.show()
    }
}