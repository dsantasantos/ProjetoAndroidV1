package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemMenuNew -> {
                    val nextScreen = Intent(this, CadastroCnhActivity::class.java)
                    nextScreen.putExtra("numero_cnh", "")
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.itemMenuList -> {
                    val nextScreen = Intent(this, ListagemCnhActivity::class.java)
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.itemMenuExit -> {
                    FirebaseAuth.getInstance().signOut()
                    val nextScreen = Intent(this, LoginActivity::class.java)
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }

    }
}
