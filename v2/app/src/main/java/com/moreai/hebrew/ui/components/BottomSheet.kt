package com.moreai.hebrew.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moreai.hebrew.data.model.AppSettings
import com.moreai.hebrew.data.model.Category
import com.moreai.hebrew.ui.theme.DarkBackground
import com.moreai.hebrew.ui.theme.GhostWhite

@Composable
fun SettingsBottomSheet(
    settings: AppSettings,
    onDismiss: () -> Unit,
    onCategorySelected: (Category) -> Unit,
    onToggleNikkud: () -> Unit,
    onToggleOnlyWords: () -> Unit,
    onToggleLargeText: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GhostWhite)
    ) {
        // Close button — top right
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Fechar",
                modifier = Modifier.size(24.dp),
                tint = DarkBackground
            )
        }

        // Content — centered
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side — toggles
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                SettingToggle(
                    label = "Large text",
                    enabled = settings.largeText,
                    onClick = onToggleLargeText
                )
                SettingToggle(
                    label = "Only words",
                    enabled = settings.onlyWords,
                    onClick = onToggleOnlyWords
                )
            }

            Spacer(modifier = Modifier.width(80.dp))

            // Right side — category pills
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Category.entries.forEach { category ->
                    CategoryPill(
                        category = category,
                        selected = category == settings.selectedCategory,
                        onClick = { onCategorySelected(category) },
                        darkMode = false
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingToggle(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = if (enabled) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = DarkBackground
        )
        Text(
            text = label,
            fontSize = 16.sp,
            color = DarkBackground
        )
    }
}
