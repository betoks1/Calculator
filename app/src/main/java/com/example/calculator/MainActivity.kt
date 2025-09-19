package com.example.calculator

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner con el array de operaciones
        ArrayAdapter.createFromResource(
            this,
            R.array.ops_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerOp.adapter = adapter
        }

        binding.btnCalcular.setOnClickListener {
            ocultarTeclado()
            if (!validarEntradas()) return@setOnClickListener

            val a = binding.input1.editText!!.text.toString().trim().toDouble()
            val b = binding.input2.editText!!.text.toString().trim().toDouble()
            val op = binding.spinnerOp.selectedItem.toString()

            if (op == "División" && b == 0.0) {
                binding.input2.error = "No se puede dividir entre cero"
                return@setOnClickListener
            }

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("num1", a)
                putExtra("num2", b)
                putExtra("op", op)
            }
            startActivity(intent)
        }
    }

    private fun validarEntradas(): Boolean {
        var ok = true
        val s1 = binding.input1.editText!!.text.toString().trim()
        val s2 = binding.input2.editText!!.text.toString().trim()

        binding.input1.error = null
        binding.input2.error = null

        val n1 = s1.toDoubleOrNull()
        val n2 = s2.toDoubleOrNull()

        if (s1.isEmpty()) {
            binding.input1.error = "Ingresa un número"
            ok = false
        } else if (n1 == null) {
            binding.input1.error = "Formato inválido (usa punto decimal)"
            ok = false
        }

        if (s2.isEmpty()) {
            binding.input2.error = "Ingresa un número"
            ok = false
        } else if (n2 == null) {
            binding.input2.error = "Formato inválido (usa punto decimal)"
            ok = false
        }
        if (!ok) {
            Snackbar.make(binding.root, "Corrige los campos marcados", Snackbar.LENGTH_SHORT).show()
        }
        return ok
    }

    private fun ocultarTeclado() {
        currentFocus?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}