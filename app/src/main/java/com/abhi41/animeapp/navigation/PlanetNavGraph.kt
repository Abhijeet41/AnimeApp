package com.abhi41.animeapp.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.abhi41.planet.presentation.planetInfo.PlanetInfoCharacterScreen
import kotlinx.serialization.Serializable

object PlanetNavGraph : BaseNavGraph{

    sealed interface Destination{
        @Serializable
        data class PlanetWithCharacter(val id: Int) : Destination
    }

    override fun build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.composable<Destination.PlanetWithCharacter> {
            val id = requireNotNull(it.toRoute<Destination.PlanetWithCharacter>().id)
            PlanetInfoCharacterScreen(
                modifier = modifier,
                id = id,
                onClick = {
                    navHostController.navigate(AnimeNavGraph.Destination.CharacterDetails(it))
                },
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}