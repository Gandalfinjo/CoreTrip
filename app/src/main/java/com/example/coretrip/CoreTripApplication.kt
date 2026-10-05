package com.example.coretrip

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point for CoreTrip.
 *
 * Enables Hilt dependency injection for the application's Android components.
 */
@HiltAndroidApp
class CoreTripApplication : Application()