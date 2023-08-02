package com.subbu.invoice.navigation

sealed class Screens(val route: String) {
    object home : Screens("home")
    object Note : Screens("")
    object Add : Screens("")
}