package com.am.genreclassifier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.am.librosa.Librosa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val genreClassifier = GenreClassifier(this)

        CoroutineScope(Dispatchers.IO).launch {
            val stft = Librosa.getStftFromRaw(this@MainActivity, R.raw.reggae_00008)

            val result = genreClassifier.scan(stft)

            runOnUiThread {
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
                println("Genre: $result")
            }

        }

    }

}