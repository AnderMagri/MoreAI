package com.moreai.hebrew.data

data class HebrewLetterSegment(
    val cluster: String,
    val sound: String
)

private val consonantSounds: Map<Char, String> = mapOf(
    'א' to "A", 'ב' to "V", 'ג' to "G", 'ד' to "D",
    'ה' to "H", 'ו' to "V", 'ז' to "Z", 'ח' to "Ch",
    'ט' to "T", 'י' to "Y", 'כ' to "Ch", 'ך' to "Ch",
    'ל' to "L", 'מ' to "M", 'ם' to "M", 'נ' to "N",
    'ן' to "N", 'ס' to "S", 'ע' to "A", 'פ' to "F",
    'ף' to "F", 'צ' to "Ts", 'ץ' to "Ts", 'ק' to "K",
    'ר' to "R", 'ש' to "Sh", 'ת' to "T"
)

private val vowelSounds: Map<Char, String> = mapOf(
    'ְ' to "",      // Shva
    'ֱ' to "e",     // Hataf Segol
    'ֲ' to "a",     // Hataf Patach
    'ֳ' to "o",     // Hataf Kamatz
    'ִ' to "i",     // Hiriq
    'ֵ' to "e",     // Tsere
    'ֶ' to "e",     // Segol
    'ַ' to "a",     // Patach
    'ָ' to "a",     // Kamatz
    'ֹ' to "o",     // Holam
    'ֺ' to "o",     // Holam Haser
    'ֻ' to "u",     // Kubutz
    'ּ' to "",      // Dagesh
    'ׁ' to "",      // Shin dot
    'ׂ' to ""       // Sin dot
)

private fun isHebrewConsonant(c: Char): Boolean =
    c in 'א'..'ת'

private fun isHebrewMark(c: Char): Boolean =
    c in '֑'..'ׇ'

fun segmentHebrew(text: String): List<HebrewLetterSegment> {
    val segments = mutableListOf<HebrewLetterSegment>()
    var i = 0
    val chars = text.toCharArray()

    while (i < chars.size) {
        if (isHebrewConsonant(chars[i])) {
            val start = i
            val consonant = chars[i]
            i++
            while (i < chars.size && isHebrewMark(chars[i])) {
                i++
            }
            val cluster = String(chars, start, i - start)

            val hasDagesh = 'ּ' in cluster
            val hasSinDot = 'ׂ' in cluster
            var vowel = ""
            for (c in cluster) {
                val v = vowelSounds[c]
                if (v != null && c != 'ּ' && c != 'ׁ' && c != 'ׂ') {
                    vowel = v
                }
            }

            var base = consonantSounds[consonant] ?: "?"
            if (consonant == 'ב' && hasDagesh) base = "B"
            if (consonant == 'פ' && hasDagesh) base = "P"
            if (consonant == 'כ' && hasDagesh) base = "K"
            if (consonant == 'ש' && hasSinDot) base = "S"

            val sound = if (vowel.isNotEmpty()) "$base$vowel" else base
            segments.add(HebrewLetterSegment(cluster, sound))
        } else {
            i++
        }
    }

    return segments
}
