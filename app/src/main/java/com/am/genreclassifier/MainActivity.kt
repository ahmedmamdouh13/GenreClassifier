package com.am.genreclassifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO
// TDD
// Use https://github.com/adrielcafe/AndroidAudioConverter to convert any audio to wav.
// write unit tests.
// Implement Design


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
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.blues, "blues7"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.rock_example,
                    "rock6"
                )
            )
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.pop_00094, "pop_000945"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.reggae_00008,
                    "reggae_00008"
                )
            )
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.blues, "blues6"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.rock_example,
                    "rock_example5"
                )
            )
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.pop_00094, "pop_000954"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.reggae_00008,
                    "reggae_00001"
                )
            )
            mainViewModel.channel.send(MainViewIntent.ScanTrackRawRes(R.raw.blues, "blues6"))
            mainViewModel.channel.send(
                MainViewIntent.ScanTrackRawRes(
                    R.raw.rock_example,
                    "rock_example3"
                )
            )
        }


    }

    @Composable
    fun MainScreen(state: MainViewState = MainViewState()) {

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

    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMainScreen() {
        MaterialTheme {
            MainScreen()
        }
    }


}