package com.abhi41.planet.presentation.planetInfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.abhi41.planet.domain.model.PlanetInfoWithCharacters
import com.abhi41.planet.presentation.R

private const val ANIMATION_DURATION_ONE_SEC = 1000

@Composable
fun PlanetInfoCharacterScreen(
    id: Int,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: PlanetInfoViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPlanetCharacters(id)
    }

    PlanetInfoCharacterScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onClick = onClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetInfoCharacterScreenContent(
    modifier: Modifier,
    uiState: PlanetInfoUiState,
    onClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                if (uiState.planetInfo != null){
                    Text(text = uiState.planetInfo.name)
                }
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackClick()
                    }
                )
            }
        )
    }) { innerPadding ->
        //handle animation for ProgressBar (uiState.isLoading)
        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(delayMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(delayMillis = ANIMATION_DURATION_ONE_SEC))
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
            enter = fadeIn(tween(delayMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(delayMillis = ANIMATION_DURATION_ONE_SEC))
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
            visible = uiState.planetInfo != null,
            enter = slideInVertically(tween(delayMillis = ANIMATION_DURATION_ONE_SEC)) { it }
                    + fadeIn(tween(ANIMATION_DURATION_ONE_SEC)),
            exit = slideOutVertically(tween(ANIMATION_DURATION_ONE_SEC)) +
                    fadeOut(tween(ANIMATION_DURATION_ONE_SEC))
        ) {
            uiState.planetInfo?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            model = data.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            text = data.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.characters),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    items(data.characters) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onClick(it.id)
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .height(80.dp),
                                model = it.image,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier.padding(start = 16.dp),
                                text = it.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
private fun PlanetInfoCharacterScreenContentPreview() {
    PlanetInfoCharacterScreenContent(
        modifier = Modifier,
        uiState = PlanetInfoUiState(
            isLoading = false,
            planetInfo =  PlanetInfoWithCharacters(
                characters = emptyList(),
                description = "Description",
                id = 1,
                image = "",
                name = "Name",
                isDestroed = false
            )  
        ),
        onClick = {},
        onBackClick = {  }
    )
}
