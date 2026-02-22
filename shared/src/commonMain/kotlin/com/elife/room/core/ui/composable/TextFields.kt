package com.elife.room.core.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.elife.room.shared.Res
import com.elife.room.shared.required_mark
import org.jetbrains.compose.resources.stringResource

/**
 * @author Lian
 * @date 2025/12/9
 */

/**
 * 带有必填标识的
 */
@Composable
fun TextWithRequiredMark(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        text = buildAnnotatedString {
            append(text)
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.errorContainer)) {
                append(stringResource(Res.string.required_mark))
            }
        },
        modifier = modifier,
        style = style
    )
}

/**
 * 输入框
 */
@Composable
fun InputTextField(
    state: TextFieldState = rememberTextFieldState(),
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.colors().copy(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        errorContainerColor = MaterialTheme.colorScheme.surface
    ),
    contentPadding: PaddingValues = TextFieldDefaults.contentPaddingWithoutLabel(
        top = 0.dp,
        bottom = 0.dp
    )
) {
    TextField(
        state = state,
        modifier = modifier.then(Modifier.fillMaxWidth().height(48.dp)),
        placeholder = placeholder,
        lineLimits = lineLimits,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding
    )
}