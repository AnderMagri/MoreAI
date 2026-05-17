package com.moreai.hebrew.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.ImportContacts
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moreai.hebrew.data.model.Category
import com.moreai.hebrew.ui.theme.DarkBackground
import com.moreai.hebrew.ui.theme.GhostWhite
import com.moreai.hebrew.ui.theme.SurfaceLight

fun Category.icon(): ImageVector = when (this) {
    Category.TORAH -> Icons.Outlined.MenuBook
    Category.SIDDUR -> Icons.Outlined.ImportContacts
    Category.TEHILLIM -> Icons.Outlined.MusicNote
    Category.SONGS -> Icons.Outlined.QueueMusic
}

@Composable
fun CategoryPill(
    category: Category,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    darkMode: Boolean = true
) {
    val shape = if (selected) RoundedCornerShape(10.dp) else RoundedCornerShape(5.dp)
    val backgroundColor = when {
        darkMode && selected -> Color.Transparent
        darkMode -> Color.Transparent
        selected -> SurfaceLight
        else -> GhostWhite
    }
    val borderColor = if (darkMode) GhostWhite else DarkBackground
    val textColor = if (darkMode) GhostWhite else DarkBackground
    val iconTint = if (darkMode) GhostWhite else DarkBackground

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.label,
                fontSize = 20.sp,
                color = textColor
            )
            Icon(
                imageVector = category.icon(),
                contentDescription = category.label,
                modifier = Modifier.size(24.dp),
                tint = iconTint
            )
        }
    }
}
