package br.com.fiap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class CadastroCnhActivity : AppCompatActivity() {

    private lateinit var editNomeCompleto: EditText
    private lateinit var editNumeroCnh: EditText


    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cnh)

        editNomeCompleto = findViewById(R.id.inputNomeCompleto)
        editNumeroCnh = findViewById(R.id.inputNumeroCnh)

        val button = findViewById<Button>(R.id.btSalvarCnh)

        button.setOnClickListener {

            if (TextUtils.isEmpty(editNomeCompleto.text)) {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show()
            } else {
                val word = editNomeCompleto.text.toString()
                val numeroCnh = editNumeroCnh.text.toString()
                //replyIntent.putExtra(EXTRA_REPLY, word)

                val df = SimpleDateFormat("yyyy/MM/dd")
                val longTeste = df.parse("2019/05/05").time

                val objeto = TesteTabela(word, longTeste)
                mainViewModel.insert(objeto)
            }

            finish()
        }
    }



    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val newWordActivityRequestCode = 1
    }
}
