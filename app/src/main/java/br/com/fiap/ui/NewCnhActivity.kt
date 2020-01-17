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
import kotlinx.android.synthetic.main.activity_new_cnh.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class NewCnhActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_new_cnh)
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

        editTextFullName.setText(cnhViewModel.full_name)
        editTextBirthDate.setText(cnhViewModel.birthdate)
        editTextCnhNumber.setText(cnhViewModel.cnh_number)
        editTextCnhDate.setText(cnhViewModel.cnh_date)
        editTextCnhExpired.setText(cnhViewModel.cnh_expired)
        editTextTelephone.setText(cnhViewModel.telephone)

        if (cnhViewModel.cnh_number != "") {
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
                        objeto.cnh_number,
                        objeto.full_name,
                        objeto.birthdate,
                        objeto.cnh_date,
                        objeto.cnh_expired,
                        objeto.telephone
                    )
                }

                finish()
            }
        }
    }

    private fun initViewModel() {
        cnhViewModel = ViewModelProviders.of(this).get(CnhViewModel::class.java)
        cnhViewModel.cnh_number = intent.getStringExtra("cnh_number")
        cnhViewModel.full_name = intent.getStringExtra("birthdate")
        cnhViewModel.cnh_date = intent.getStringExtra("cnh_date")
        cnhViewModel.cnh_expired = intent.getStringExtra("cnh_expired")
        cnhViewModel.telephone = intent.getStringExtra("telephone")
    }

    fun campoPreenchido(campo: EditText, name: String): Boolean {

        if (TextUtils.isEmpty(campo.text)) {
            Toast.makeText(
                applicationContext,
                "${name} ${resources.getString(R.string.screen_new_cnh_message_field_empty)}",
                Toast.LENGTH_LONG
            )
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
