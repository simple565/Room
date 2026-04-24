package com.elife.room.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elife.room.core.ext.isCompact
import com.elife.room.core.ui.composable.AdaptiveDialog
import com.elife.room.core.ui.composable.CommonDialogTitle
import com.elife.room.entity.SearchPluginInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPluginDialog(
    onDismiss: () -> Unit,
    onSave: (SearchPluginInfo) -> Unit,
    modifier: Modifier = Modifier,
    plugin: SearchPluginInfo? = null,
    windowInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    var name by remember { mutableStateOf(plugin?.name ?: "") }
    var baseUrl by remember { mutableStateOf(plugin?.url ?: "") }

    AdaptiveDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = modifier.then(
                    Modifier.fillMaxWidth(if (windowInfo.isCompact()) 1F else 0.7F)
                        .fillMaxHeight(if (windowInfo.isCompact()) 0.75F else 0.55F)
                )
            )  {
                CommonDialogTitle(
                    title = if (plugin == null) "添加搜索插件" else "编辑搜索插件",
                    onCancelClick = onDismiss,
                    onConfirmClick = {
                        val newPlugin = SearchPluginInfo(
                            id = plugin?.id ?: 0,
                            name = name,
                            url = baseUrl,
                            mapping = emptyList()
                        )
                        onSave(newPlugin)
                    }
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("插件名称") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = baseUrl,
                    onValueChange = { baseUrl = it },
                    label = { Text("API 基础 URL") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }
    )
}
