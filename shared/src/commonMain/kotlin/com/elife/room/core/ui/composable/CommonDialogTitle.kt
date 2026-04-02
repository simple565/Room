package com.elife.room.core.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elife.room.core.ui.theme.AppTheme
import com.elife.room.core.ui.theme.spacing
import com.elife.room.core.ui.tooling.UiModePreview
import com.elife.room.shared.Res
import com.elife.room.shared.cancel
import com.elife.room.shared.done
import org.jetbrains.compose.resources.stringResource

/**
 * @author Lian
 * @date 2026/4/3
 */
@Composable
fun CommonDialogTitle(
    title: String,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    cancelIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = stringResource(Res.string.cancel),
            tint = MaterialTheme.colorScheme.onSurface
        )
    },
    onConfirmClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.then(
            Modifier.fillMaxWidth().wrapContentHeight()
                .padding(vertical = MaterialTheme.spacing.extraSmall)
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (null != onConfirmClick) {
            IconButton(
                onClick = onCancelClick,
                shape = CircleShape,
                content = cancelIcon
            )
        } else {
            Spacer(Modifier.width(MaterialTheme.spacing.medium))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        if (null == onConfirmClick) {
            Spacer(Modifier.weight(1F))
        }

        if (null == onConfirmClick) {
            IconButton(
                onClick = onCancelClick,
                shape = CircleShape,
                content = cancelIcon
            )
        } else {
            IconButton(
                onClick = onConfirmClick,
                shape = CircleShape,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = stringResource(Res.string.done),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

@UiModePreview
@Composable
private fun TitlePreview() {
    AppTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            CommonDialogTitle(
                title = "Test",
                onCancelClick = {},
            )
            CommonDialogTitle(
                title = "Test",
                onCancelClick = {},
                onConfirmClick = {}
            )
        }
    }
}