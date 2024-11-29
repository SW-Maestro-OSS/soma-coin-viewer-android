package com.soma.coinviewer.domain.datastore

enum class PriceCurrencyUnit(val value: String) {
    DOLLAR("dollar"),
    WON("won"),
}

enum class Language(val value: String) {
    ENGLISH("english"),
    KOREAN("korean"),
}

enum class ShowSymbols(val value: String) {
    LINEAR("linear"),
    Grid2x2("2x2_grid"),
}