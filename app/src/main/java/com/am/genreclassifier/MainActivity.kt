package com.am.genreclassifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.am.core.state.ViewState
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.model.Genre
import com.am.genreclassifier.state.MainViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO
// TDD
// Use https://github.com/adrielcafe/AndroidAudioConverter to convert any audio to wav.


class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(mainViewModel.state.collectAsState().value)
            }
        }

        lifecycleScope.launchWhenCreated {
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.pop_00094, "pop_00094"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.reggae_00008,
                    "reggae_00008"
                )
            )
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.blues, "blues"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.rock_example,
                    "rock_example"
                )
            )
        }


    }

    @Composable
    fun MainScreen(state: MainViewState = MainViewState.Idle) {
        when (state) {
            is MainViewState.Error -> Text(text = state.e.message!!)
            MainViewState.Idle -> Text(text = "Idle")
            is MainViewState.Loading -> Text(text = state.msg)
            is MainViewState.Success -> LazyColumn {
                items(state.state.toList()) {
                    when (val viewState = it.second) {
                        ViewState.Idle -> Text(text = "Idle" + " ${it.first}")
                        is ViewState.Error -> Text(text = viewState.e.message!! + " ${it.first}")
                        is ViewState.Loading -> Text(text = viewState.msg + " ${it.first}")
                        is ViewState.Success<*> -> Text(text = (viewState.data as Genre).genre + " ${it.first}")
                    }
                }
            }
        }

    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMainScreen() {
        MaterialTheme {
            MainScreen()
        }
    }


}