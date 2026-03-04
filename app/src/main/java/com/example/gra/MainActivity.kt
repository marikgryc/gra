package com.example.gra

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Список доступних кольорів
    private val colors = listOf(Color.RED, Color.YELLOW, Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val grid = findViewById<GridLayout>(R.id.myGrid)

        // Ініціалізуємо гру
        setupGame(grid)
    }

    private fun setupGame(grid: GridLayout) {
        // Проходимо по всіх дочірніх View у нашому GridLayout
        for (i in 0 until grid.childCount) {
            val square = grid.getChildAt(i)

            // 1. При запуску фарбуємо в рандомний колір
            square.setBackgroundColor(colors.random())

            // 2. Додаємо обробник кліку
            square.setOnClickListener {
                changeToNextColor(it)
                checkWin(grid)
            }
        }
    }

    private fun changeToNextColor(view: View) {
        // Отримуємо поточний колір фону
        val background = view.background as? ColorDrawable
        val currentColor = background?.color ?: Color.GRAY

        // Знаходимо індекс наступного кольору
        val currentIndex = colors.indexOf(currentColor)
        val nextIndex = (currentIndex + 1) % colors.size

        // Встановлюємо новий колір
        view.setBackgroundColor(colors[nextIndex])
    }

    private fun checkWin(grid: GridLayout) {
        // Беремо колір першого квадрата для порівняння
        val firstSquareColor = (grid.getChildAt(0).background as? ColorDrawable)?.color

        var allMatch = true
        for (i in 1 until grid.childCount) {
            val currentColor = (grid.getChildAt(i).background as? ColorDrawable)?.color
            if (currentColor != firstSquareColor) {
                allMatch = false
                break
            }
        }

        if (allMatch) {
            showRestartDialog(grid)
        }
    }

    private fun showRestartDialog(grid: GridLayout) {
        AlertDialog.Builder(this)
            .setTitle("Перемога!")
            .setMessage("Всі квадрати одного кольору. Бажаєте почати спочатку?")
            .setCancelable(false) // Щоб не закрили випадково
            .setPositiveButton("Restart") { _, _ ->
                setupGame(grid) // Перезапуск логіки
            }
            .show()
    }
}