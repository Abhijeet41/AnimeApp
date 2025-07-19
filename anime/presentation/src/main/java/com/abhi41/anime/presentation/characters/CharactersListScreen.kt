package com.abhi41.anime.presentation.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.abhi41.anime.domain.models.Character

@Composable
fun CharacterListScreen(modifier: Modifier = Modifier) {
    val viewModel: CharactersViewModel = hiltViewModel<CharactersViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
}

@Composable
fun CharacterListScreenContent(modifier: Modifier = Modifier, uiState: CharactersState) {


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        //Loading state handled
        if (uiState.isLoading) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        //Error state handled
        if (uiState.error.isNotBlank()) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }

        }
        //Success state handled
        uiState.characters?.let { data ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(data, key = { it.id }) { character ->
                    CharacterItem(character = character)
                }
            }
        }


    }


}

@Composable
fun CharacterItem(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = character.image,
            contentDescription = null
        )
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview
@Composable
private fun CharacterListScreenContentPreview() {
    CharacterListScreenContent(modifier = Modifier, uiState = CharactersState())
}

