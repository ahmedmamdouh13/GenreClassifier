package com.am.genreclassifier.helper

import android.app.Activity
import android.content.Intent
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File

class ChooseAudioFileHelper(activity: ComponentActivity) {

    fun chooseFromStorage(onFileChosen: (waveFile: File, fileName: String) -> Unit) {
        val intent = Intent()
        intent.type = "audio/x-wav"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)

        onChosen = onFileChosen
    }

    private val resultLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val path = result?.data?.data?.path!!
                val name = path.split(":").last()

                val file = File(Environment.getExternalStorageDirectory().absolutePath + "/$name")
                file.setReadable(true, false)

                onChosen(file, name)
            }
        }

    companion object {
        private lateinit var onChosen: (File, String) -> Unit
    }
}