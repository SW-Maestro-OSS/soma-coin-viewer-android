package com.soma.coinviewer.feature.home

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
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
import coil.compose.AsyncImage
import com.soma.coinviewer.common_ui.base.BaseComposeFragment
import com.soma.coinviewer.domain.entity.BinanceTickerData
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class HomeFragment : BaseComposeFragment() {
    override val fragmentViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeLayout() {
        fragmentViewModel.apply {
            val listSortType by listSortType.collectAsStateWithLifecycle()
            val coinData by coinData.collectAsStateWithLifecycle()

            fragmentViewModel.apply {
                HomeScreen(
                    listSortType = listSortType,
                    coinData = coinData,
                    setListSortType = ::setListSortType,
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    listSortType: ListSortType,
    coinData: List<BinanceTickerData>,
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
                    modifier = Modifier
                        .weight(1f)
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
                    modifier = Modifier
                        .weight(1f)
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
                itemsIndexed(
                    items = coinData,
                    key = { idx, data -> data.symbol },
                ) { idx, data ->
                    CoinItem(data)

                    if (idx != 29) {
                        HorizontalDivider(color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

@Composable
private fun CoinItem(coinData: BinanceTickerData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
    ) {
        AsyncImage(
            model = coinData.coinIconUrl,
            placeholder = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            error = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            onError = { Log.d("Img Error", coinData.coinIconUrl) },
            contentDescription = "",
            modifier = Modifier.size(20.dp),
        )

        Text(
            text = coinData.symbol,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = coinData.price.toPlainString(),
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )

        val priceChangePercentColor = if (coinData.priceChangePercent >= BigDecimal(0.0)) {
            Color.Green
        } else {
            Color.Red
        }

        Text(
            text = coinData.priceChangePercent.toString(),
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            color = priceChangePercentColor,
            modifier = Modifier.weight(1f),
        )
    }
}