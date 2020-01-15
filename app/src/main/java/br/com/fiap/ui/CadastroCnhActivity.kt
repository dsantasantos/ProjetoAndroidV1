package br.com.fiap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import br.com.fiap.R
import br.com.fiap.entidades.Cnh
import br.com.fiap.viewmodel.CnhViewModel
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
    private lateinit var cnhViewModel: CnhViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cnh)

        ivVoltar.setOnClickListener {
            onBackPressed()
        }

        initViewModel()

        editNomeCompleto = findViewById(R.id.inputNomeCompleto)
        editDataNascimento = findViewById(R.id.inputDataNascimento)
        editNumeroCnh = findViewById(R.id.inputNumeroCnh)
        editDataCnh = findViewById(R.id.inputDataCnh)
        editDataCnhVenc = findViewById(R.id.inputVencimento)
        editTelefone = findViewById(R.id.inputTelefone)

        editNomeCompleto.setText(cnhViewModel.nomeCompleto)
        editDataNascimento.setText(cnhViewModel.dataNascimento)
        editNumeroCnh.setText(cnhViewModel.numeroCnh)
        editDataCnh.setText(cnhViewModel.dataCnh)
        editDataCnhVenc.setText(cnhViewModel.dataVencCnh)
        editTelefone.setText(cnhViewModel.telefone)

        if (cnhViewModel.numeroCnh != "") {
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

    private fun initViewModel() {
        cnhViewModel = ViewModelProviders.of(this).get(CnhViewModel::class.java)
        cnhViewModel.numeroCnh = intent.getStringExtra("numero_cnh")
        cnhViewModel.nomeCompleto = intent.getStringExtra("nome_completo")
        cnhViewModel.dataNascimento = intent.getStringExtra("data_nascimento")
        cnhViewModel.dataCnh = intent.getStringExtra("data_cnh")
        cnhViewModel.dataVencCnh = intent.getStringExtra("data_venc_cnh")
        cnhViewModel.telefone = intent.getStringExtra("telefone")
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
