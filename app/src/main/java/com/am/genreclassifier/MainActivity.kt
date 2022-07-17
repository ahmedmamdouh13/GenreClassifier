package com.am.genreclassifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.am.core.state.ViewState
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.state.MainViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO
// TDD
// Use https://github.com/adrielcafe/AndroidAudioConverter to convert any audio to wav.
// write unit tests.
// Implement Design


class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private val chooseAudioFileHelper = ChooseAudioFileHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(mainViewModel.state.collectAsState().value) {

                    chooseAudioFileHelper.chooseFromStorage { waveFile, fileName ->
                        lifecycleScope.launch {
                            mainViewModel.channel.send(MainViewIntent.ScanTrackFile(file = waveFile, fileName))
                        }
                    }
                }
            }
        }
        requestPermissions(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 111
        )


    }

    @Composable
    fun MainScreen(state: MainViewState = MainViewState(), onAddTrackClicked: () -> Unit) {

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.scanLoading.isActive) {
                Text(text = state.scanLoading.msg)
            }
            if (state.scanError.isActive) {
                Text(text = state.scanError.msg)
            }
        }



        LazyColumn {
            items(state.genreResultList.toList()) {
                when (val viewState = it.second) {
                    ViewState.Idle -> Text(text = "Idle" + " ${it.first}")
                    is ViewState.Error -> Text(text = "Error " + viewState.e.message!! + " ${it.first}")
                    is ViewState.Loading -> Text(text = viewState.msg + " ${it.first}")
                    is ViewState.Success<*> -> Text(text = (viewState.data as Genre).genre + " ${it.first}")
                }
            }
        }

        Button(onClick = onAddTrackClicked) {
            Text(text = "Add Track")
        }

    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMainScreen() {
        MaterialTheme {
//            MainScreen()
        }
    }


}