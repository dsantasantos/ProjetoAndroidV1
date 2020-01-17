package br.com.fiap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import java.util.*

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

        ivNewCnhBack.setOnClickListener {
            onBackPressed()
        }

        initViewModel()

        editTextFullName = findViewById(R.id.etNewFullName)
        editTextBirthDate = findViewById(R.id.etNewBirthDate)
        editTextCnhNumber = findViewById(R.id.etNewNumberCnh)
        editTextCnhDate = findViewById(R.id.etNewDateCnh)
        editTextCnhExpired = findViewById(R.id.etNewDateExpired)
        editTextTelephone = findViewById(R.id.etNewTelephoneNumber)

        setDateEditText(editTextBirthDate)
        setDateEditText(editTextCnhDate)
        setDateEditText(editTextCnhExpired)

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
        cnhViewModel.full_name = intent.getStringExtra("full_name")
        cnhViewModel.birthdate = intent.getStringExtra("birthdate")
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

    fun setDateEditText(fieldDate: EditText) {

        fieldDate.addTextChangedListener(object : TextWatcher {

            private var current = ""
            private val ddmmyyyy = "DDMMYYYY"
            private val cal = Calendar.getInstance()

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != current) {
                    var clean = p0.toString().replace("[^\\d.]|\\.".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]|\\.", "")

                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean == cleanC) sel--

                    if (clean.length < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length)
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        var day = Integer.parseInt(clean.substring(0, 2))
                        var mon = Integer.parseInt(clean.substring(2, 4))
                        var year = Integer.parseInt(clean.substring(4, 8))

                        mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                        cal.set(Calendar.YEAR, year)
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(
                            Calendar.DATE
                        ) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }

                    clean = String.format(
                        "%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8)
                    )

                    sel = if (sel < 0) 0 else sel
                    current = clean
                    fieldDate.setText(current)
                    fieldDate.setSelection(if (sel < current.count()) sel else current.count())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {

            }
        })
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val newWordActivityRequestCode = 1
    }
}
