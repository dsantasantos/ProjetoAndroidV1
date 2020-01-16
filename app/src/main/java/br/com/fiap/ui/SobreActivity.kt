package br.com.fiap.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.fiap.R
import kotlinx.android.synthetic.main.activity_sobre.*

class SobreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)

        ivNewBack.setOnClickListener {
            onBackPressed()
        }
    }
}
