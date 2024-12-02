package com.soma.coinviewer.domain.datastore

import com.soma.coinviewer.domain.datastore.PriceCurrencyUnit.DOLLAR

enum class PriceCurrencyUnit(val value: String) {
    DOLLAR("dollar"),
    WON("won");

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): PriceCurrencyUnit {
            return values().find { it.value == value } ?: DOLLAR
        }
    }
}

enum class Language(val value: String) {
    ENGLISH("english"),
    KOREAN("korean");

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): Language {
            return Language.values().find { it.value == value } ?: ENGLISH
        }
    }
}

enum class HowToShowSymbols(val value: String) {
    LINEAR("linear"),
    Grid2x2("2x2_grid");

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): HowToShowSymbols {
            return HowToShowSymbols.values().find { it.value == value } ?: LINEAR
        }
    }
}