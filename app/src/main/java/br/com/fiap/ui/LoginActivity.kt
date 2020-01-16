package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editEmailLogin: EditText
    private lateinit var editSenhaLogin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editEmailLogin = findViewById(R.id.inputEmailLogin)
        editSenhaLogin = findViewById(R.id.inputSenhaLogin)

        val buttonCadastrar = findViewById<Button>(R.id.btCadastrar)
        val buttonEntrar = findViewById<Button>(R.id.btEntrar)

        buttonCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroUsuarioActivity::class.java)
            startActivity(intent)
        }

        buttonEntrar.setOnClickListener {

            val email = editEmailLogin.text.toString()
            val senha = editSenhaLogin.text.toString()

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "E-mail ou senha inválido",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }


    }
}
