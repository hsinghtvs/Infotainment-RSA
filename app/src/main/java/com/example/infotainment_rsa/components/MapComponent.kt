package com.example.infotainment_rsa.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.infotainment_rsa.MainActivity
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.Mappls
import com.mappls.sdk.services.account.MapplsAccountManager


@Composable
fun MapComponent(modifier: Modifier = Modifier, mainActivity: MainActivity) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    MapplsAccountManager.getInstance().restAPIKey = "4a0bbbe5ab800eca852f98bc67c7313b"
    MapplsAccountManager.getInstance().mapSDKKey = "4a0bbbe5ab800eca852f98bc67c7313b"
    MapplsAccountManager.getInstance().atlasClientId =
        "33OkryzDZsLz1faUP1SNrZP5d9Oo8yH_Ag8ko-lc_S-sMJj3F-VyMom7tOVYx2rP6KuofD3D900SGFrH5oNdIS7IyTIDUHf8"
    MapplsAccountManager.getInstance().atlasClientSecret =
        "lrFxI-iSEg_CDKS_hj7cdc1set2FQlVUHt5tz5JT3oNPDd-cWNKCmYetPlaiZ7Do4Vuecep5QTQiGAjH2wNjl1caGjvrAVNmspaGWbF8T74="
    Mappls.getInstance(LocalContext.current)


    val mapView = remember { MapView(context).apply { onCreate(null) } }
    mapView.getMapAsync(mainActivity)

    // Manage the lifecycle of the MapView
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }

        // Add observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // Cleanup when the effect leaves the composition
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()  // Proper cleanup of the MapView
        }
    }

    // AndroidView to display the MapView
    AndroidView(
        factory = { mapView }, modifier = modifier
    )
}