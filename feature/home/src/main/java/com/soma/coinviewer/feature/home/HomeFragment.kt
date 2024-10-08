package com.soma.coinviewer.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soma.coinviewer.common_ui.base.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseComposeFragment() {
    override val fragmentViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeLayout() {
        fragmentViewModel.apply {
            val listSortType by listSortType.collectAsStateWithLifecycle()

            fragmentViewModel.apply {
                HomeScreen(
                    listSortType = listSortType,
                    setListSortType = ::setListSortType,
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    listSortType: ListSortType,
    setListSortType: (ListSortType) -> Unit,
) {
    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.LightGray),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                        .clickable {
                            val newListSortType = when (listSortType) {
                                ListSortType.SYMBOL_ASC -> ListSortType.SYMBOL_DESC
                                ListSortType.SYMBOL_DESC -> ListSortType.TOTAL_TRADE
                                else -> ListSortType.SYMBOL_ASC
                            }

                            setListSortType(newListSortType)
                        },
                ) {
                    val sortImage = when (listSortType) {
                        ListSortType.SYMBOL_ASC -> R.drawable.ic_up
                        ListSortType.SYMBOL_DESC -> R.drawable.ic_down
                        else -> R.drawable.ic_updown
                    }

                    Image(
                        painter = painterResource(sortImage),
                        contentDescription = "",
                    )

                    Text(
                        text = "Symbol",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                        .clickable {
                            val newListSortType = when (listSortType) {
                                ListSortType.PRICE_ASC -> ListSortType.PRICE_DESC
                                ListSortType.PRICE_DESC -> ListSortType.TOTAL_TRADE
                                else -> ListSortType.PRICE_ASC
                            }

                            setListSortType(newListSortType)
                        },
                ) {
                    val sortImage = when (listSortType) {
                        ListSortType.PRICE_ASC -> R.drawable.ic_up
                        ListSortType.PRICE_DESC -> R.drawable.ic_down
                        else -> R.drawable.ic_updown
                    }

                    Image(
                        painter = painterResource(sortImage),
                        contentDescription = "",
                    )

                    Text(
                        text = "Price (\$)",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f),
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            val newListSortType = when (listSortType) {
                                ListSortType.ONE_DAY_CHANGE_ASC -> ListSortType.ONE_DAY_CHANGE_DESC
                                ListSortType.ONE_DAY_CHANGE_DESC -> ListSortType.TOTAL_TRADE
                                else -> ListSortType.ONE_DAY_CHANGE_ASC
                            }

                            setListSortType(newListSortType)
                        },
                ) {
                    val sortImage = when (listSortType) {
                        ListSortType.ONE_DAY_CHANGE_ASC -> R.drawable.ic_up
                        ListSortType.ONE_DAY_CHANGE_DESC -> R.drawable.ic_down
                        else -> R.drawable.ic_updown
                    }

                    Image(
                        painter = painterResource(sortImage),
                        contentDescription = "",
                    )

                    Text(
                        text = "24h Change %",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}
