package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.inputEmail)F
        editSenha = findViewById(R.id.inputSenha)

        btSalvarUsuario.setOnClickListener {

            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()

            auth.createUserWithEmailAndPassword(
                email,
                senha
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Erro ao criar novo usuário",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }


    }
}
