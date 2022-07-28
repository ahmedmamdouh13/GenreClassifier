package com.am.genreclassifier

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.am.genreclassifier.helper.ChooseAudioFileHelper
import com.am.genreclassifier.helper.PermissionManager
import com.am.genreclassifier.intent.MainViewIntent
import com.am.genreclassifier.ui.mainscreen.MainScreen
import com.am.genreclassifier.ui.theme.GenreClassifierTheme
import com.am.genreclassifier.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO
// TDD
// Use https://github.com/adrielcafe/AndroidAudioConverter to convert any audio to wav.
// write unit tests.
// Implement Design
// Write better idempotent logic


class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private val chooseAudioFileHelper: ChooseAudioFileHelper = ChooseAudioFileHelper(this)
    private val permissionManager: PermissionManager = PermissionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenreClassifierTheme {
                MainScreen(mainViewModel.state.collectAsState().value, {
                    if (permissionManager.requestPermission() == PackageManager.PERMISSION_GRANTED) {
                        lifecycleScope.launchWhenCreated {
                            mainViewModel.channel.send(
                                MainViewIntent.ChooseTrackFile(
                                    chooseAudioFileHelper
                                )
                            )
                        }
                    }
                }, {
                    lifecycleScope.launchWhenCreated {
                        mainViewModel.channel.send(MainViewIntent.Scan)
                    }
                })

            }
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