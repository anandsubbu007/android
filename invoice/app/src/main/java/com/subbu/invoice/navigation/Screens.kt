package com.subbu.invoice.navigation

sealed class Screens(val route: String) {
    object home : Screens("home")
    object ProductListing : Screens("ProductListing")
    object NewInvoice : Screens("NewInvoice/{InvId}")
}