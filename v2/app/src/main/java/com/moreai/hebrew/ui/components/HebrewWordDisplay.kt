package com.moreai.hebrew.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moreai.hebrew.data.model.EntryType
import com.moreai.hebrew.data.model.HebrewEntry
import com.moreai.hebrew.ui.theme.BlueAccent
import com.moreai.hebrew.ui.theme.GhostWhite

@Composable
fun HebrewEntryDisplay(
    entry: HebrewEntry,
    showNikkud: Boolean,
    largeText: Boolean,
    modifier: Modifier = Modifier
) {
    val isQuote = entry.type == EntryType.QUOTE
    val hebrewText = if (showNikkud) entry.hebrew else entry.hebrewNoNikkud

    val hebrewSize = when {
        isQuote && largeText -> 36.sp
        isQuote -> 28.sp
        largeText -> 110.sp
        else -> 92.sp
    }

    val translitSize = if (largeText) 16.sp else 12.sp

    Column(
        modifier = modifier.padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = hebrewText,
            fontSize = hebrewSize,
            color = GhostWhite,
            textAlign = TextAlign.Center,
            lineHeight = hebrewSize * 1.3f
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = entry.transliteration,
            fontSize = translitSize,
            color = BlueAccent,
            textAlign = TextAlign.Center
        )

        if (entry.source != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = entry.source,
                fontSize = 11.sp,
                color = GhostWhite.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }
    }
}
