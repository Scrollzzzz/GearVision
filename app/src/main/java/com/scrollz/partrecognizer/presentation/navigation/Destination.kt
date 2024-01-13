package com.scrollz.partrecognizer.presentation.navigation

sealed class Destination(val route: String) {
    data object Main: Destination(route = "main")
    data object Report: Destination(route = "report")
    data object Settings: Destination(route = "settings")
}
