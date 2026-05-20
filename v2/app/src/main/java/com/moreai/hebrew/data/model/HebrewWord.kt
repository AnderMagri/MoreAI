package com.moreai.hebrew.data.model

enum class Category(val label: String) {
    TORAH("Torah"),
    SIDDUR("Siddur"),
    TEHILLIM("Tehillim"),
    SONGS("Songs")
}

enum class EntryType {
    WORD,
    QUOTE
}

data class HebrewEntry(
    val id: String,
    val type: EntryType,
    val hebrew: String,
    val hebrewNoNikkud: String,
    val transliteration: String,
    val translation: String,
    val categories: List<Category>,
    val source: String? = null
)

data class AppSettings(
    val showNikkud: Boolean = true,
    val onlyWords: Boolean = true,
    val largeText: Boolean = false,
    val selectedCategory: Category = Category.TORAH
)
