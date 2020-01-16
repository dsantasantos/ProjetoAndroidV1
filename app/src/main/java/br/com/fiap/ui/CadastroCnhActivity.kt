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

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var cnhViewModel: CnhViewModel

    private lateinit var editTextFullName: EditText
    private lateinit var editTextBirthDate: EditText
    private lateinit var editTextCnhNumber: EditText
    private lateinit var editTextCnhDate: EditText
    private lateinit var editTextCnhExpired: EditText
    private lateinit var editTextTelephone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cnh)
        val dataFormat = SimpleDateFormat("dd/MM/yyyy")

        ivNewBack.setOnClickListener {
            onBackPressed()
        }

        initViewModel()

        editTextFullName = findViewById(R.id.etNewFullName)
        editTextBirthDate = findViewById(R.id.etNewBirthDate)
        editTextCnhNumber = findViewById(R.id.etNewNumberCnh)
        editTextCnhDate = findViewById(R.id.etNewDateCnh)
        editTextCnhExpired = findViewById(R.id.etNewDateExpired)
        editTextTelephone = findViewById(R.id.etNewTelephoneNumber)

        editTextFullName.setText(cnhViewModel.nomeCompleto)
        editTextBirthDate.setText(cnhViewModel.dataNascimento)
        editTextCnhNumber.setText(cnhViewModel.numeroCnh)
        editTextCnhDate.setText(cnhViewModel.dataCnh)
        editTextCnhExpired.setText(cnhViewModel.dataVencCnh)
        editTextTelephone.setText(cnhViewModel.telefone)

        if (cnhViewModel.numeroCnh != "") {
            editTextCnhNumber.setEnabled(false)
        } else {
            editTextCnhNumber.setEnabled(true)
        }

        val button = findViewById<Button>(R.id.btNewSave)

        button.setOnClickListener {
            if (campoPreenchido(
                    editTextFullName,
                    resources.getString(R.string.screen_new_cnh_full_name)
                )
                && campoPreenchido(
                    editTextBirthDate,
                    resources.getString(R.string.screen_new_cnh_birth_date)
                )
                && campoPreenchido(
                    editTextCnhNumber,
                    resources.getString(R.string.screen_new_cnh_number)
                )
                && campoPreenchido(
                    editTextCnhDate,
                    resources.getString(R.string.screen_new_cnh_date)
                )
                && campoPreenchido(
                    editTextCnhExpired,
                    resources.getString(R.string.screen_new_cnh_expired)
                )
                && campoPreenchido(
                    editTextTelephone,
                    resources.getString(R.string.screen_new_cnh_telephone)
                )
            ) {

                var fullName = editTextFullName.text.toString()
                var birthDate = dataFormat.parse(editTextBirthDate.text.toString()).time
                var cnhNumber = editTextCnhNumber.text.toString()
                var cnhDate = dataFormat.parse(editTextCnhDate.text.toString()).time
                var cnhExpired = dataFormat.parse(editTextCnhExpired.text.toString()).time
                var telephone = editTextTelephone.text.toString()

                val objeto =
                    Cnh(
                        fullName,
                        birthDate,
                        cnhNumber,
                        cnhDate,
                        cnhExpired,
                        telephone
                    )

                if (editTextCnhNumber.isEnabled) {
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
