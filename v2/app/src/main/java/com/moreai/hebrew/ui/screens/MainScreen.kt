package com.moreai.hebrew.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moreai.hebrew.ui.components.CategoryPill
import com.moreai.hebrew.ui.components.HebrewEntryDisplay
import com.moreai.hebrew.ui.components.SettingsBottomSheet
import com.moreai.hebrew.ui.theme.DarkBackground
import com.moreai.hebrew.ui.theme.GhostWhite

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val settings by viewModel.settings.collectAsState()
    val bottomSheetVisible by viewModel.bottomSheetVisible.collectAsState()
    val entry by viewModel.currentEntry.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Category pill at top
        CategoryPill(
            category = settings.selectedCategory,
            selected = false,
            onClick = { viewModel.toggleBottomSheet() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
            darkMode = true
        )

        // Hebrew entry display — center
        entry?.let { currentEntry ->
            Box(modifier = Modifier.align(Alignment.Center)) {
                HebrewEntryDisplay(
                    entry = currentEntry,
                    showNikkud = settings.showNikkud,
                    largeText = settings.largeText
                )
            }

            // Translation at bottom
            Text(
                text = currentEntry.translation,
                fontSize = 16.sp,
                color = GhostWhite.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 32.dp)
            )
        }

        // Audio button — left center
        OutlinedIconButton(
            onClick = { viewModel.speakCurrent() },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 58.dp)
                .size(44.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, GhostWhite),
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = GhostWhite)
        ) {
            Icon(
                imageVector = Icons.Default.GraphicEq,
                contentDescription = "Ouvir",
                tint = GhostWhite
            )
        }

        // Next (random) button — right center
        OutlinedIconButton(
            onClick = { viewModel.pickRandom() },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 58.dp)
                .size(44.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, GhostWhite),
            colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = GhostWhite)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Próximo",
                tint = GhostWhite
            )
        }
    }

    if (bottomSheetVisible) {
        SettingsBottomSheet(
            settings = settings,
            onDismiss = { viewModel.hideBottomSheet() },
            onCategorySelected = {
                viewModel.selectCategory(it)
                viewModel.hideBottomSheet()
            },
            onToggleNikkud = { viewModel.toggleNikkud() },
            onToggleOnlyWords = { viewModel.toggleOnlyWords() },
            onToggleLargeText = { viewModel.toggleLargeText() }
        )
    }
}
