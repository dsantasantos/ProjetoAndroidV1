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

        editNomeCompleto.setText(intent.getStringExtra("nome_completo"))
        editDataNascimento.setText(intent.getStringExtra("data_nascimento"))
        editNumeroCnh.setText(intent.getStringExtra("numero_cnh"))
        editDataCnh.setText(intent.getStringExtra("data_cnh"))
        editDataCnhVenc.setText(intent.getStringExtra("data_venc_cnh"))

        val button = findViewById<Button>(R.id.btSalvarCnh)

        button.setOnClickListener {

            if (campoPreenchido(editNomeCompleto, "Nome Completo")
                && campoPreenchido(editDataNascimento, "Data Nascimento")
                && campoPreenchido(editNumeroCnh, "Numero CNH")
                && campoPreenchido(editDataCnh, "Data CNH")
                && campoPreenchido(editDataCnhVenc, "Data CNH Vencimento")
            ) {

                val df = SimpleDateFormat("dd/MM/yyyy")

                var nomeCompleto = editNomeCompleto.text.toString()
                var dataNascimento = df.parse(editDataNascimento.text.toString()).time
                var numeroCnh = editNumeroCnh.text.toString()
                var dataCnh = df.parse(editDataCnh.text.toString()).time
                var dataVencCnh = df.parse(editDataCnhVenc.text.toString()).time

                val objeto =
                    TesteTabela(nomeCompleto, dataNascimento, numeroCnh, dataCnh, dataVencCnh)

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
