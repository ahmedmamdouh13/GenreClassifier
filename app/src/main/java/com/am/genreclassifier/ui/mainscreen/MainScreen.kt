package com.am.genreclassifier.ui.mainscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.am.core.state.ViewState
import com.am.genreclassifier.state.MainViewState
import com.am.genreclassifier.ui.util.Padding
import com.am.genreclassifier.ui.util.Position


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(state: MainViewState = MainViewState(), onAddTrackClicked: () -> Unit) {

    Position(position = Alignment.Center) {

                LazyColumn {


                    state.genreResultDatedMap.forEach { (t, u) ->

                        this.stickyHeader {
                            Text(text = t)
                        }


                        val toList =
                            ((u as ViewState.Success<*>).data as MutableMap<String, ViewState>).toList()

                        items(toList){ item ->


                            val itemState = item.second
                            when (itemState) {

                                ViewState.Idle -> Text(text = "Idle" + " ${item.first}")
                                is ViewState.Error -> Text(text = "Error " + itemState.e.message!! + " ${item.first}")
                                is ViewState.Loading -> Text(text = itemState.msg + " ${item.first}")
//                    is ViewState.Success<*> -> Text(text = (viewState.data as Genre).genre + " ${it.first}")
                                is ViewState.Success<*> -> {

                                    TrackItem(item)
                                }
                            }
                        }


                    }





//                }
//            }
        }
    }

    Position(position = Alignment.Center) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (state.scanLoading.isActive) {
                Text(text = state.scanLoading.msg)
            }
            if (state.scanError.isActive) {
                Text(text = state.scanError.msg)
            }
        }
    }


    Padding(vertical = 16.dp, horizontal = 16.dp) {
        Position(Alignment.BottomEnd) {
            AddButton(onAddTrackClicked)
        }
    }

    Padding(vertical = 16.dp) {
        Position(position = Alignment.BottomCenter) {
            ScanButtonItem(scanButton = state.scanButton) {

            }
        }
    }
}


@Composable
fun TracksList() {


}

