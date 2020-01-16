package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var editLoginEmail: EditText
    private lateinit var editLoginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editLoginEmail = findViewById(R.id.etLoginEmail)
        editLoginPassword = findViewById(R.id.etLoginPassword)

        val btNewUser = findViewById<Button>(R.id.btLoginNewUser)
        val btSignIn = findViewById<Button>(R.id.btLoginSignIn)

        btNewUser.setOnClickListener {
            val intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        }

        btSignIn.setOnClickListener {

            firebaseAuth.signInWithEmailAndPassword(
                editLoginEmail.text.toString(),
                editLoginPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.screen_login_message_error_sigin),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }
    }
}
