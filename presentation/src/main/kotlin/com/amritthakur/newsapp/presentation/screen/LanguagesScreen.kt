package com.amritthakur.newsapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amritthakur.newsapp.presentation.component.PrimaryButton
import com.amritthakur.newsapp.presentation.state.LanguagesUiState
import com.amritthakur.newsapp.presentation.viewmodel.LanguagesInput
import com.amritthakur.newsapp.presentation.viewmodel.LanguagesOutput

@Composable
fun LanguagesScreen(
    input: LanguagesInput,
    output: LanguagesOutput
) {

    LanguagesContent(
        uiState = output.uiState.collectAsStateWithLifecycle().value,
        onLanguage = input.onLanguage
    )
}

@Composable
fun LanguagesContent(
    uiState: LanguagesUiState,
    onLanguage: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = uiState.languages.toList(),
            key = { (languageCode, _) -> languageCode }
        ) { (languageCode, languageName) ->
            PrimaryButton(
                title = languageName,
                onClick = { onLanguage(languageCode) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguagesContentPreview() {
    MaterialTheme {
        LanguagesContent(
            uiState = LanguagesUiState(),
            onLanguage = { }
        )
    }
}