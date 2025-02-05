package com.soma.coinviewer.feature.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.soma.coinviewer.common_ui.base.BaseComposeFragment
import com.soma.coinviewer.domain.preferences.HowToShowSymbols
import com.soma.coinviewer.feature.home.ro.CoinInfoDataRO
import com.soma.coinviewer.i18n.Currency
import com.soma.coinviewer.i18n.USDCurrency
import com.soma.coinviewer.navigation.DeepLinkRoute
import com.soma.coinviewer.navigation.NavigationTarget
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseComposeFragment() {
    override val fragmentViewModel: HomeViewModel by viewModels()

    @Composable
    override fun ComposeLayout() {
        fragmentViewModel.apply {
            val listSortType by listSortType.collectAsStateWithLifecycle()
            val currentData by when (listSortType) {
                ListSortType.TOTAL_TRADE -> totalTradeData
                ListSortType.SYMBOL_ASC -> symbolAscData
                ListSortType.SYMBOL_DESC -> symbolDescData
                ListSortType.PRICE_ASC -> priceAscData
                ListSortType.PRICE_DESC -> priceDescData
                ListSortType.ONE_DAY_CHANGE_ASC -> priceChangeAscData
                ListSortType.ONE_DAY_CHANGE_DESC -> priceChangeDescData
            }.collectAsStateWithLifecycle()
            val howToShowSymbols by howToShowSymbols.collectAsStateWithLifecycle()
            val currency by currency.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                loadHowToShowSymbols()
                initExchangeRate()
            }

            HomeScreen(
                howToShowSymbols = howToShowSymbols,
                listSortType = listSortType,
                currency = currency,
                coinData = currentData,
                updateSortType = ::updateSortType,
                navigateToCoinDetail = { coinId ->
                    navigationHelper.navigateTo(NavigationTarget(DeepLinkRoute.CoinDetail(coinId)))
                },
            )
        }
    }
}

@Composable
private fun HomeScreen(
    howToShowSymbols: HowToShowSymbols,
    listSortType: ListSortType,
    currency: Currency,
    coinData: List<CoinInfoDataRO>,
    updateSortType: (ListSortType, ListSortType) -> Unit,
    navigateToCoinDetail: (String) -> Unit,
) {
    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.LightGray),
            ) {
                HeaderItem(
                    text = stringResource(R.string.symbol),
                    listSortType = listSortType,
                    currentAscType = ListSortType.SYMBOL_ASC,
                    currentDescType = ListSortType.SYMBOL_DESC,
                    updateSortType = updateSortType,
                    modifier = Modifier.weight(1f)
                )

                HeaderItem(
                    text = stringResource(R.string.price) + "(${currency.postUnit})",
                    listSortType = listSortType,
                    currentAscType = ListSortType.PRICE_ASC,
                    currentDescType = ListSortType.PRICE_DESC,
                    updateSortType = updateSortType,
                    modifier = Modifier.weight(1f)
                )

                HeaderItem(
                    text = stringResource(R.string.change_24h),
                    listSortType = listSortType,
                    currentAscType = ListSortType.ONE_DAY_CHANGE_ASC,
                    currentDescType = ListSortType.ONE_DAY_CHANGE_DESC,
                    updateSortType = updateSortType,
                    modifier = Modifier.weight(1f)
                )
            }

            when (howToShowSymbols.value) {
                HowToShowSymbols.LINEAR.value -> {
                    val listState = rememberLazyListState()

                    LaunchedEffect(listSortType) {
                        listState.scrollToItem(0)
                    }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(
                            items = coinData,
                            key = { _, data -> data.symbol },
                        ) { idx, data ->
                            CoinRow(
                                coinData = data,
                                navigateToCoinDetail = navigateToCoinDetail,
                            )

                            if (idx != coinData.lastIndex) {
                                HorizontalDivider(color = Color.DarkGray)
                            }
                        }
                    }
                }

                HowToShowSymbols.GRID2X2.value -> {
                    val listState = rememberLazyGridState()

                    LaunchedEffect(listSortType) {
                        listState.scrollToItem(0)
                    }

                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = coinData,
                            key = { data -> data.symbol },
                        ) { data ->
                            CoinGridCard(
                                coinData = data,
                                navigateToCoinDetail = navigateToCoinDetail,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderItem(
    text: String,
    listSortType: ListSortType,
    currentAscType: ListSortType,
    currentDescType: ListSortType,
    updateSortType: (ListSortType, ListSortType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sortImage = remember(listSortType) {
        when (listSortType) {
            currentAscType -> R.drawable.ic_up
            currentDescType -> R.drawable.ic_down
            else -> R.drawable.ic_updown
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { updateSortType(currentAscType, currentDescType) },
    ) {
        Image(
            painter = painterResource(sortImage),
            contentDescription = "",
        )

        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CoinRow(
    coinData: CoinInfoDataRO,
    navigateToCoinDetail: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { navigateToCoinDetail(coinData.symbol) }
            .padding(horizontal = 4.dp),
    ) {
        AsyncImage(
            model = coinData.coinIconUrl,
            placeholder = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            error = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            onError = { Log.w("Img Error", coinData.coinIconUrl) },
            contentDescription = "",
            modifier = Modifier.size(40.dp),
        )

        Text(
            text = coinData.symbol,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = coinData.price,
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = coinData.priceChangePercent.text,
            fontSize = 13.sp,
            textAlign = TextAlign.End,
            color = coinData.priceChangePercent.color,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CoinGridCard(
    coinData: CoinInfoDataRO,
    navigateToCoinDetail: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.5.dp, color = Color.DarkGray)
            .clickable { navigateToCoinDetail(coinData.symbol) }
            .padding(horizontal = 4.dp, vertical = 10.dp),
    ) {
        AsyncImage(
            model = coinData.coinIconUrl,
            placeholder = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            error = painterResource(com.soma.coinviewer.common_ui.R.drawable.ic_coin_placeholder),
            onError = { Log.w("Img Error", coinData.coinIconUrl) },
            contentDescription = "",
            modifier = Modifier.size(60.dp),
        )

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = coinData.symbol,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp,
            )

            Text(
                text = coinData.price,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp,
            )

            Text(
                text = coinData.priceChangePercent.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 15.sp,
                textAlign = TextAlign.End,
                color = coinData.priceChangePercent.color,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreenLinear() {
    val dummyCoinDataRO = listOf(
        CoinInfoDataRO(
            symbol = "BTC",
            totalTradedQuoteAssetVolume = "$1,000,000",
            price = "$40,000.50",
            priceChangePercent = CoinInfoDataRO.FormattedText(
                text = "+2.5%",
                color = Color.Green
            ),
            coinIconUrl = "https://cryptologos.cc/logos/bitcoin-btc-logo.png"
        ),
        CoinInfoDataRO(
            symbol = "ETH",
            totalTradedQuoteAssetVolume = "$500,000",
            price = "$2,500.75",
            priceChangePercent = CoinInfoDataRO.FormattedText(
                text = "-1.2%",
                color = Color.Red
            ),
            coinIconUrl = "https://cryptologos.cc/logos/ethereum-eth-logo.png"
        )
    )

    HomeScreen(
        howToShowSymbols = HowToShowSymbols.LINEAR,
        listSortType = ListSortType.TOTAL_TRADE,
        currency = USDCurrency,
        coinData = dummyCoinDataRO,
        updateSortType = { _, _ -> },
        navigateToCoinDetail = {}
    )
}

@Preview
@Composable
private fun PreviewHomeScreenGrid() {
    val dummyCoinDataRO = listOf(
        CoinInfoDataRO(
            symbol = "BTC",
            totalTradedQuoteAssetVolume = "$1,000,000",
            price = "$40,000.50",
            priceChangePercent = CoinInfoDataRO.FormattedText(
                text = "+2.5%",
                color = Color.Green
            ),
            coinIconUrl = "https://cryptologos.cc/logos/bitcoin-btc-logo.png"
        ),
        CoinInfoDataRO(
            symbol = "ETH",
            totalTradedQuoteAssetVolume = "$500,000",
            price = "$2,500.75",
            priceChangePercent = CoinInfoDataRO.FormattedText(
                text = "-1.2%",
                color = Color.Red
            ),
            coinIconUrl = "https://cryptologos.cc/logos/ethereum-eth-logo.png"
        )
    )

    HomeScreen(
        howToShowSymbols = HowToShowSymbols.GRID2X2,
        listSortType = ListSortType.TOTAL_TRADE,
        currency = USDCurrency,
        coinData = dummyCoinDataRO,
        updateSortType = { _, _ -> },
        navigateToCoinDetail = {}
    )
}

@Preview
@Composable
private fun PreviewCoinRow() {
    val dummyCoinDataRO = CoinInfoDataRO(
        symbol = "BTC",
        totalTradedQuoteAssetVolume = "$1,000,000",
        price = "$40,000.50",
        priceChangePercent = CoinInfoDataRO.FormattedText(
            text = "+2.5%",
            color = Color.Green
        ),
        coinIconUrl = "https://cryptologos.cc/logos/bitcoin-btc-logo.png"
    )

    CoinRow(
        coinData = dummyCoinDataRO,
        navigateToCoinDetail = {}
    )
}

@Preview
@Composable
private fun PreviewCoinGridCard() {
    val dummyCoinDataRO = CoinInfoDataRO(
        symbol = "BTC",
        totalTradedQuoteAssetVolume = "$1,000,000",
        price = "$40,000.50",
        priceChangePercent = CoinInfoDataRO.FormattedText(
            text = "+2.5%",
            color = Color.Green
        ),
        coinIconUrl = "https://cryptologos.cc/logos/bitcoin-btc-logo.png"
    )

    CoinGridCard(
        coinData = dummyCoinDataRO,
        navigateToCoinDetail = {}
    )
}
