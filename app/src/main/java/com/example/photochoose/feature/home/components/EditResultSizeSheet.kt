package com.example.photochoose.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.photochoose.ui.theme.LocalSpacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditResultSizeSheet(
    modifier: Modifier = Modifier,
    hideEditSheet: () -> Unit,
    setResultSize: (Int) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = hideEditSheet,
        sheetState = bottomSheetState
    ) {
        EditResultSizeSheet(
            setResultSize = setResultSize,
            hideBottomSheet = {
                scope
                    .launch { bottomSheetState.hide() }
                    .invokeOnCompletion { hideEditSheet() }
            }
        )
    }

}

@Composable
private fun EditResultSizeSheet(
    modifier: Modifier = Modifier,
    setResultSize: (Int) -> Unit,
    hideBottomSheet: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.dimen24)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
    ) {
        Text(text = "Edit Result Size", style = MaterialTheme.typography.titleLarge)

        var textFieldValue by rememberSaveable { mutableStateOf("") }
        var errorMsg: String? by rememberSaveable { mutableStateOf(null) }

        Column {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    errorMsg = null
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = {
                    Text(text = "Result Size")
                },
                placeholder = {
                    Text(text = "Result Size")
                }
            )
            val msg = errorMsg
            if (msg != null) {
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Spacer(modifier = Modifier.height(LocalSpacing.current.dimen4))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val resultSize = textFieldValue.toIntOrNull()
                if (resultSize != null) {
                    setResultSize(resultSize)
                    hideBottomSheet()
                } else {
                    errorMsg = "Invalid Input"
                }
            }
        ) {
            Text(text = "Done")
        }
    }
}
