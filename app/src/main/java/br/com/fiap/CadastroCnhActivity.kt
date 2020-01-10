package br.com.fiap

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
    private lateinit var editDataNascimento: EditText
    private lateinit var editNumeroCnh: EditText
    private lateinit var editDataCnh: EditText
    private lateinit var editDataCnhVenc: EditText

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cnh)

        editNomeCompleto = findViewById(R.id.inputNomeCompleto)
        editDataNascimento = findViewById(R.id.inputDataNascimento)
        editNumeroCnh = findViewById(R.id.inputNumeroCnh)
        editDataCnh = findViewById(R.id.inputDataCnh)
        editDataCnhVenc = findViewById(R.id.inputVencimento)

        val button = findViewById<Button>(R.id.btSalvarCnh)

        button.setOnClickListener {

            if (campoPreenchido(editNomeCompleto, "Nome Completo")
                && campoPreenchido(editDataNascimento, "Data Nascimento")
                && campoPreenchido(editNumeroCnh, "Numero CNH")
                && campoPreenchido(editDataCnh, "Data CNH")
                && campoPreenchido(editDataCnhVenc, "Data CNH Vencimento")
            ) {

                val word = editNomeCompleto.text.toString()
                val numeroCnh = editNumeroCnh.text.toString()
                //replyIntent.putExtra(EXTRA_REPLY, word)

                val df = SimpleDateFormat("yyyy/MM/dd")
                val longTeste = df.parse("2019/05/05").time

                val objeto = TesteTabela(word, longTeste)
                mainViewModel.insert(objeto)

                finish()
            }
        }
    }

    fun campoPreenchido(campo: EditText, b: String): Boolean {

        if (TextUtils.isEmpty(campo.text)) {
            Toast.makeText(applicationContext, "O campo $b não está preenchido", Toast.LENGTH_LONG)
                .show()
            return false
        }

        return true
    }


    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val newWordActivityRequestCode = 1
    }
}
