package br.com.fiap.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.novo_cnh -> {
                    val nextScreen = Intent(this, CadastroCnhActivity::class.java)
                    nextScreen.putExtra("numero_cnh", "")
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.listagem_cnh -> {
                    val nextScreen = Intent(this, ListagemCnhActivity::class.java)
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.sobre -> {
                    val nextScreen = Intent(this, SobreActivity::class.java)
                    startActivity(nextScreen)
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }

    }
}
