package com.abhi41.animeapp.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface BaseNavGraph {

    fun build(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )

}