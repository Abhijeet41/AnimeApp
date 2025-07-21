package com.abhi41.anime.presentation.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.presentation.components.CharacterItem

@Composable
fun CharacterListScreen(modifier: Modifier = Modifier, onClick: (Int) -> Unit) {
    val viewModel: CharactersViewModel = hiltViewModel<CharactersViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllCharacters()
    }
    CharacterListScreenContent(
        modifier = modifier.fillMaxSize(),
        uiState = uiState.value,
        onClick = onClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreenContent(
    modifier: Modifier = Modifier,
    uiState: CharactersState,
    onClick: (Int) -> Unit
) {


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Anime Characters") },
                modifier = Modifier.background(color = Color.Blue)
            )
        }
    ) { innerPadding ->

        //handle animation for ProgressBar (uiState.isLoading)
        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(delayMillis = 1000)),
            exit = fadeOut(tween(delayMillis = 1000))
        ) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        //handle animation for Error message (uiState.error)
        AnimatedVisibility(
            visible = uiState.error.isNotBlank(),
            enter = fadeIn(tween(delayMillis = 1000)),
            exit = fadeOut(tween(delayMillis = 1000))
        ) {
            Box(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }

        AnimatedVisibility(
            visible = uiState.characters.isNotEmpty(),
            enter = slideInVertically(tween(delayMillis = 1000)) { it }
                    + fadeIn(tween(1000)),
            exit = slideOutVertically(tween(1000)) +
                    fadeOut(tween(1000))
        ) {
            uiState.characters.let { data ->
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    items(data, key = { it.id }) { character ->
                        CharacterItem(
                            character = character,
                            onClick = onClick
                        )
                    }
                }
            }
        }

    }


}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun CharacterListScreenContentPreview() {
    CharacterItem(
        character = Character(
            id = 1,
            name = "Dodoria",
            image = "https://dragonball-api.com/characters/dodoria.webp"
        ),
        onClick = {}
    )


}

