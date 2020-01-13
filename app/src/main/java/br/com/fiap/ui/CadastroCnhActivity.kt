package br.com.fiap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.R
import br.com.fiap.entidades.Cnh
import br.com.fiap.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_cadastro_cnh.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class CadastroCnhActivity : AppCompatActivity() {

    private lateinit var editNomeCompleto: EditText
    private lateinit var editDataNascimento: EditText
    private lateinit var editNumeroCnh: EditText
    private lateinit var editDataCnh: EditText
    private lateinit var editDataCnhVenc: EditText
    private lateinit var editTelefone: EditText

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cnh)

        ivVoltar.setOnClickListener {
            onBackPressed()
        }

        editNomeCompleto = findViewById(R.id.inputNomeCompleto)
        editDataNascimento = findViewById(R.id.inputDataNascimento)
        editNumeroCnh = findViewById(R.id.inputNumeroCnh)
        editDataCnh = findViewById(R.id.inputDataCnh)
        editDataCnhVenc = findViewById(R.id.inputVencimento)
        editTelefone = findViewById(R.id.inputTelefone)

        val numeroCnh = intent.getStringExtra("numero_cnh")

        editNomeCompleto.setText(intent.getStringExtra("nome_completo"))
        editDataNascimento.setText(intent.getStringExtra("data_nascimento"))
        editNumeroCnh.setText(numeroCnh)
        editDataCnh.setText(intent.getStringExtra("data_cnh"))
        editDataCnhVenc.setText(intent.getStringExtra("data_venc_cnh"))
        editTelefone.setText(intent.getStringExtra("telefone"))

        if (numeroCnh != "") {
            editNumeroCnh.setEnabled(false)
        } else {
            editNumeroCnh.setEnabled(true)
        }

        val button = findViewById<Button>(R.id.btSalvarCnh)

        button.setOnClickListener {

            if (campoPreenchido(editNomeCompleto, "Nome Completo")
                && campoPreenchido(editDataNascimento, "Data Nascimento")
                && campoPreenchido(editNumeroCnh, "Numero CNH")
                && campoPreenchido(editDataCnh, "Data CNH")
                && campoPreenchido(editDataCnhVenc, "Data CNH Vencimento")
                && campoPreenchido(editTelefone, "Telefone")
            ) {

                val df = SimpleDateFormat("dd/MM/yyyy")

                var nomeCompleto = editNomeCompleto.text.toString()
                var dataNascimento = df.parse(editDataNascimento.text.toString()).time
                var numeroCnh = editNumeroCnh.text.toString()
                var dataCnh = df.parse(editDataCnh.text.toString()).time
                var dataVencCnh = df.parse(editDataCnhVenc.text.toString()).time
                var telefone = editTelefone.text.toString()

                val objeto =
                    Cnh(
                        nomeCompleto,
                        dataNascimento,
                        numeroCnh,
                        dataCnh,
                        dataVencCnh,
                        telefone
                    )

                if (editNumeroCnh.isEnabled) {
                    mainViewModel.insert(objeto)
                } else {
                    mainViewModel.update(
                        objeto.numeroCnh,
                        objeto.nomeCompleto,
                        objeto.dataNascimento,
                        objeto.dataCnh,
                        objeto.dataVencCnh,
                        objeto.telefone
                    )
                }



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
