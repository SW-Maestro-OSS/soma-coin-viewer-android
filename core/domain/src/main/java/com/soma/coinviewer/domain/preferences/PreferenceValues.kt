package com.soma.coinviewer.domain.preferences

enum class PriceCurrencyUnit(val value: String) {
    DOLLAR("dollar"),
    WON("won"),
    DEFAULT(DOLLAR.value);

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): PriceCurrencyUnit {
            return entries.find { it.value == value } ?: DEFAULT
        }
    }
}

enum class Language(val value: String) {
    ENGLISH("english"),
    KOREAN("korean"),
    DEFAULT(ENGLISH.value);

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): Language {
            return entries.find { it.value == value } ?: DEFAULT
        }
    }
}

enum class HowToShowSymbols(val value: String) {
    LINEAR("linear"),
    GRID2X2("2x2_grid"),
    DEFAULT(LINEAR.value);

    companion object {
        // 아직 설정된 값이 없거나 잘못된 값이면, Default 값 설정
        fun fromValue(value: String?): HowToShowSymbols {
            return entries.find { it.value == value } ?: DEFAULT
        }
    }
}