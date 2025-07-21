package com.abhi41.animeapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.abhi41.anime.presentation.character_details.CharacterDetailScreen
import com.abhi41.anime.presentation.characters.CharacterListScreen
import kotlinx.serialization.Serializable

object AnimeNavGraph : BaseNavGraph {

    sealed interface Destination {
        @Serializable
        data object Root : Destination
        @Serializable
        data object CharactersList : Destination
        @Serializable
        data class CharacterDetails(val id: Int) : Destination

    }

    override fun build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Destination.Root>(
            startDestination = Destination.CharactersList
        ) {
            composable<Destination.CharactersList> {
                CharacterListScreen(
                    modifier = modifier.fillMaxSize(),
                    onClick = {
                        navHostController.navigate(Destination.CharacterDetails(it))
                    }
                )
            }

            composable<Destination.CharacterDetails> {
               val id = requireNotNull(it.toRoute<Destination.CharacterDetails>().id)
                CharacterDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    id = id,
                    seeAllCharacter = {
                        navHostController.navigate(PlanetNavGraph.Destination.PlanetWithCharacter(it))
                    },
                    onBackClick = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }


}