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

    MapplsAccountManager.getInstance().restAPIKey = "2d41f81e76cc1d89d9b6b4b844077d99"
    MapplsAccountManager.getInstance().mapSDKKey = "2d41f81e76cc1d89d9b6b4b844077d99"
    MapplsAccountManager.getInstance().atlasClientId =
        "33OkryzDZsJ8dJjI-t4d2H_3CTEJwhqMdK2i-OJqw1ttDHIqWxnKtnyw6jZghgz2qyzCrHKkC8sKw50_2Rmwj165aOps_hUf"
    MapplsAccountManager.getInstance().atlasClientSecret =
        "lrFxI-iSEg99lrIJ6Uwix23qKhphCgN_8QZDdXWtrKBajvIL3P5qOfd8d0itvPXK7ndQN5dEqpRWNSZ3UqzdHUX901-bOm5J99dghKPNsWE="
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