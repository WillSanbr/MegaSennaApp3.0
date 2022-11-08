package com.example.megasennawillian10

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var qtdNumber: Int? = null
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Captura as referencias dos Componentes
        val btnGenerateNumber: Button = findViewById(R.id.btn_generateNumber)
        val editInputNumber: EditText = findViewById(R.id.editInput_Number)
        val txtResult: TextView = findViewById(R.id.txtresult)
        //Armazena os dados SmartPhone
        prefs = getSharedPreferences("db", MODE_PRIVATE)
        val messageApp = prefs.getString("result", null)
        //Se o app foi usado o app armazena a sessao usada
        messageApp?.let {
            val outMessage = """
                Ultima aposta: 
                
                 $messageApp  
            """.trimIndent()

            txtResult.text = outMessage
        }

        btnGenerateNumber.setOnClickListener {
            numberGenerate(editInputNumber.text.toString(), txtResult)
        }
    }

    private fun numberGenerate(text: String, textView: TextView) {
        val checkInputUser = checkValid(text) //checkValid = verifica os erros digitado
        val listNumber = mutableSetOf<Int>()
        val random = Random
        //Expressao do sucesso!!!
        if (checkInputUser) {
            while (true) {
                val number = random.nextInt(61)
                listNumber.add(number + 1)
                //expressao que cancela loop quando lista tiver mesmo numero quantidade sorteado
                if (listNumber.size == qtdNumber) break
            }
            textView.text = listNumber.joinToString(" - ")
            prefs.edit().apply {
                putString("result", textView.text.toString())
                apply()
            }
        }
    }

    private fun checkValid(text: String): Boolean {
        //Erro 1: Campo é vazio
        if (text.isEmpty()) {
            Toast.makeText(this, "Digite um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return false
        }
        val number = text.toInt()
        //Erro 2: Verifica se o numero esta entre 6 e 15 caso contrario Erro!!!
        if (number < 6 || number > 15) {
            Toast.makeText(this, "Digite um numero entre 6 e 15", Toast.LENGTH_SHORT).show()
            return false
        }
        //Armazena valor do numero escolhido
        this.qtdNumber = number
        return true
    }

}