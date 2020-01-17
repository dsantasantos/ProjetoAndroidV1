package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import br.com.fiap.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_user.*

class NewUserActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var editUserEmail: EditText
    private lateinit var editUserPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        firebaseAuth = FirebaseAuth.getInstance()

        editUserEmail = findViewById(R.id.etUserEmail)
        editUserPassword = findViewById(R.id.etUserPassword)

        ivNewUserBack.setOnClickListener {
            onBackPressed()
        }

        btUserSave.setOnClickListener {

            firebaseAuth.createUserWithEmailAndPassword(
                editUserEmail.text.toString(),
                editUserPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.screen_new_user_message_error_create),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
        }
    }
}
