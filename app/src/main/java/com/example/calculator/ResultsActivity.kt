package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityResultsBinding
import java.text.DecimalFormat
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private val df = DecimalFormat("#.######")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = intent.getDoubleExtra("num1", Double.NaN)
        val b = intent.getDoubleExtra("num2", Double.NaN)
        val op = intent.getStringExtra("op")

        if (a.isNaN() || b.isNaN() || op.isNullOrEmpty()) {
            finish() // Datos inválidos: regresamos
            return
        }

        val (symbol, result) = calcular(op, a, b)

        binding.tvExpresion.text = "${df.format(a)} $symbol ${df.format(b)} ="
        binding.tvResultado.text = df.format(result)

        binding.btnVolver.setOnClickListener { finish() }
    }

    private fun calcular(op: String, a: Double, b: Double): Pair<String, Double> {
        return when (op) {
            "Suma" -> "+" to (a + b)
            "Resta" -> "−" to (a - b)
            "Multiplicación" -> "×" to (a * b)
            "División" -> "÷" to (a / b)
            "Exponente" -> "^" to (a.pow(b))
            else -> "?" to Double.NaN
        }
    }
}