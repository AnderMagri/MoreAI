package com.moreai.hebrew.data

data class HebrewLetterSegment(
    val cluster: String,
    val sound: String
)

private val consonantSounds: Map<Char, String> = mapOf(
    'א' to "", 'ב' to "V", 'ג' to "G", 'ד' to "D",
    'ה' to "H", 'ו' to "V", 'ז' to "Z", 'ח' to "Rr",
    'ט' to "T", 'י' to "Y", 'כ' to "Rr", 'ך' to "Rr",
    'ל' to "L", 'מ' to "M", 'ם' to "M", 'נ' to "N",
    'ן' to "N", 'ס' to "S", 'ע' to "", 'פ' to "F",
    'ף' to "F", 'צ' to "Ts", 'ץ' to "Ts", 'ק' to "K",
    'ר' to "R", 'ש' to "Sh", 'ת' to "T"
)

private val vowelSounds: Map<Char, String> = mapOf(
    'ְ' to "",      // Shva (handled dynamically as na/nach)
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

    // Pre-scan to find consonant positions for shva na detection
    val consonantPositions = mutableListOf<Int>()
    for (idx in chars.indices) {
        if (isHebrewConsonant(chars[idx])) {
            consonantPositions.add(idx)
        }
    }

    var consonantIndex = 0  // which consonant we're on (0-based)
    var prevHadShva = false  // track if previous consonant ended with shva

    while (i < chars.size) {
        if (isHebrewConsonant(chars[i])) {
            val start = i
            val consonant = chars[i]
            val isFirstConsonant = consonantIndex == 0
            i++
            while (i < chars.size && isHebrewMark(chars[i])) {
                i++
            }
            val cluster = String(chars, start, i - start)
            val isLastConsonant = consonantIndex == consonantPositions.size - 1

            val hasDagesh = 'ּ' in cluster
            val hasSinDot = 'ׂ' in cluster
            val hasShva = 'ְ' in cluster

            // Find the explicit vowel (not dagesh/shin/sin dots, not shva)
            var vowel = ""
            var hasExplicitVowel = false
            for (c in cluster) {
                val v = vowelSounds[c]
                if (v != null && c != 'ּ' && c != 'ׁ' && c != 'ׂ' && c != 'ְ') {
                    vowel = v
                    hasExplicitVowel = true
                }
            }

            // Handle vav as vowel letter (matres lectionis)
            if (consonant == 'ו') {
                if (hasDagesh && !hasExplicitVowel) {
                    // Shuruk: וּ = "u"
                    segments.add(HebrewLetterSegment(cluster, "u"))
                    prevHadShva = false
                    consonantIndex++
                    continue
                } else if ('ֹ' in cluster || 'ֺ' in cluster) {
                    // Cholam male: וֹ = "o"
                    segments.add(HebrewLetterSegment(cluster, "o"))
                    prevHadShva = false
                    consonantIndex++
                    continue
                }
            }

            // Determine consonant base sound
            var base = consonantSounds[consonant] ?: "?"
            if (consonant == 'ב' && hasDagesh) base = "B"
            if (consonant == 'פ' && hasDagesh) base = "P"
            if (consonant == 'כ' && hasDagesh) base = "K"
            if (consonant == 'ש' && hasSinDot) base = "S"

            // Handle final silent He (no mapiq/dagesh)
            if (consonant == 'ה' && isLastConsonant && !hasDagesh && !hasExplicitVowel) {
                segments.add(HebrewLetterSegment(cluster, ""))
                prevHadShva = false
                consonantIndex++
                continue
            }

            // Handle shva: determine if na (mobile) or nach (silent)
            if (hasShva && !hasExplicitVowel) {
                val isShvaNa = isFirstConsonant ||
                        prevHadShva ||
                        (hasDagesh && !isLastConsonant)
                vowel = if (isShvaNa) "e" else ""
            }

            val sound = if (vowel.isNotEmpty()) "$base$vowel" else base
            segments.add(HebrewLetterSegment(cluster, sound))

            prevHadShva = hasShva && !hasExplicitVowel
            consonantIndex++
        } else {
            i++
        }
    }

    return segments
}
