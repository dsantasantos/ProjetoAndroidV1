package br.com.fiap.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.fiap.R

class InicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        Handler().postDelayed({
            val intent = Intent(this, ListagemCnhActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000L)
    }
}
