package com.moreai.hebrew.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moreai.hebrew.data.model.Category
import com.moreai.hebrew.data.model.EntryType
import com.moreai.hebrew.data.model.HebrewEntry

data class RawEntry(
    val id: String,
    val type: String,
    val hebrew: String,
    val hebrewNoNikkud: String,
    val transliteration: String,
    val translation: String,
    val categories: List<String>,
    val source: String?
)

data class RawData(val entries: List<RawEntry>)

class DataRepository(context: Context) {

    private val allEntries: List<HebrewEntry>

    init {
        val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val raw = Gson().fromJson(json, RawData::class.java)
        allEntries = raw.entries.map { r ->
            HebrewEntry(
                id = r.id,
                type = if (r.type == "quote") EntryType.QUOTE else EntryType.WORD,
                hebrew = r.hebrew,
                hebrewNoNikkud = r.hebrewNoNikkud,
                transliteration = r.transliteration,
                translation = r.translation,
                categories = r.categories.mapNotNull { cat ->
                    when (cat) {
                        "torah" -> Category.TORAH
                        "siddur" -> Category.SIDDUR
                        "tehillim" -> Category.TEHILLIM
                        "songs" -> Category.SONGS
                        else -> null
                    }
                },
                source = r.source
            )
        }
    }

    fun getEntries(category: Category, onlyWords: Boolean): List<HebrewEntry> {
        return allEntries.filter { entry ->
            category in entry.categories &&
                    (if (onlyWords) entry.type == EntryType.WORD else true)
        }
    }

    fun getRandomEntry(category: Category, onlyWords: Boolean): HebrewEntry? {
        val filtered = getEntries(category, onlyWords)
        return filtered.randomOrNull()
    }
}
