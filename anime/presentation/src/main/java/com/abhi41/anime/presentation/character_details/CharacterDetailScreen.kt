package com.abhi41.anime.presentation.character_details

import android.view.RoundedCorner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.abhi41.anime.domain.models.CharacterDetails
import com.abhi41.anime.domain.models.OriginPlanet
import com.abhi41.anime.domain.models.Transformation
import com.abhi41.anime.presentation.R

private const val ANIMATION_DURATION_ONE_SEC = 1000

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    seeAllCharacter: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: CharacterDetailViewModel = hiltViewModel<CharacterDetailViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getCharacterDetails(id)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    CharacterDetailsScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        seeAllCharacter = seeAllCharacter,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreenContent(
    modifier: Modifier = Modifier,
    uiState: CharacterDetailState,
    seeAllCharacter: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                   if (uiState.characterDetails != null){
                       Text(text = uiState.characterDetails.name)
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
        }
    ) { innerPadding ->
        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(durationMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(durationMillis = ANIMATION_DURATION_ONE_SEC))
        ) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = uiState.error.isNotBlank(),
            enter = fadeIn(tween(durationMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(durationMillis = ANIMATION_DURATION_ONE_SEC))
        ) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }

        AnimatedVisibility(
            visible = uiState.characterDetails != null,
            enter = slideInVertically(tween(delayMillis = ANIMATION_DURATION_ONE_SEC)) { it }
                    + fadeIn(tween(ANIMATION_DURATION_ONE_SEC)),
            exit = slideOutVertically(tween(ANIMATION_DURATION_ONE_SEC)) +
                    fadeOut(tween(ANIMATION_DURATION_ONE_SEC))
        ) {
            uiState.characterDetails?.let { data ->
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
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = "${data.name}",
                            style = MaterialTheme.typography.headlineLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            text = "${data.description}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    item {
                        Text(
                            text = stringResource(R.string.transformations),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                    items(data.transformations) {
                        TransformationInfo(it)
                    }

                    item {
                        Text(
                            text = stringResource(R.string.planet_information),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }

                    item {
                        PlanetInfo(data, seeAllCharacter)
                    }

                }
            }
        }

    }

}

@Composable
private fun TransformationInfo(transformation: Transformation) {
    Row(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp),
            model = transformation.image,
            contentDescription = null,
            contentScale = ContentScale.FillHeight
        )
        Spacer(Modifier.width(12.dp))
        Text(text = transformation.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun PlanetInfo(data: CharacterDetails, seeAllCharacter: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(Modifier.height(16.dp))
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .height(200.dp),
            model = data.originPlanet.image,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "${data.originPlanet.name}",
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "${data.originPlanet.description}",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                seeAllCharacter(data.originPlanet.id)
            }
        ) {
            Text(stringResource(R.string.see_all_characters))
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun DetailsScreenContentPreview(modifier: Modifier = Modifier) {
    CharacterDetailsScreenContent(
        modifier = modifier,
        uiState = CharacterDetailState(
            isLoading = false,
            error = "",
            characterDetails = CharacterDetails(
                affiliation = "",
                description = "lklfhsdjkfhdsjhfjsdh",
                gender = "Male",
                id = 12,
                image = "",
                ki = "",
                maxKi = "",
                name = "Goku",
                originPlanet = OriginPlanet(
                    id = 2,
                    name = "TODO()",
                    image = "",
                    description = "dasjhdkjs"
                ),
                race = "race",
                transformations = listOf(
                    Transformation(
                        id = 1,
                        name = "Super Sayne",
                        ki = "",
                        image = ""
                    )
                )
            )
        ),
        {
            println("See All Characters")
        },
        {
            println("Back Clicked")
        }
    )
}

