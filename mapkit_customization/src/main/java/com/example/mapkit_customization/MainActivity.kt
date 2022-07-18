package com.example.mapkit_customization

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * This is a basic example that displays a map and sets camera focus on the target location.
 * Note: When working on your projects, remember to request the required permissions.
 */

class MainActivity : AppCompatActivity() {

    private val MAPKIT_API_KEY = "9364f347-6156-4b9a-939e-300993a4c56e"
    private val TARGET_LOCATION = Point(59.945933, 30.320045)

    private val TAG = "CustomizationActivity"

    private lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Set the api key before calling initialize on MapKitFactory.
         * It is recommended to set api key in the Application.onCreate method,
         * but here we do it in each activity to make examples isolated.
         */
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        /**
         * Initialize the library to load required native libraries.
         * It is recommended to initialize the MapKit library in the Activity.onCreate method
         * Initializing in the Application.onCreate method may lead to extra calls and increased battery use.
         */
        MapKitFactory.initialize(this)
        // Now MapView can be created.

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapview)

        val map = mapView.map
        map.mapType = MapType.VECTOR_MAP

        // Apply customization
        try {
            map.setMapStyle(style())
        } catch (e: IOException) {
            Log.e(TAG, "Failed to read customization style", e)
        }
        // And to show what can be done with it, we move the camera to the center of Saint Petersburg.
        map.move(CameraPosition(TARGET_LOCATION, 15.0f, 0.0f, 0.0f))
    }

    @Throws(IOException::class)
    private fun readRawResource(name: String): String {
        val builder = StringBuilder()
        val resourceIdentifier = resources.getIdentifier(name, "raw", packageName)
        val a = resources.openRawResource(resourceIdentifier)
        val reader = BufferedReader(InputStreamReader(a))
        try {
            var line: String? = reader.readLine()
            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
            }
        } catch (ex: IOException) {
            Log.e(TAG, "Cannot read raw resource $name")
            throw ex
        } finally {
            reader.close()
        }
        return builder.toString()
    }

    @Throws(IOException::class)
    private fun style(): String {
        return readRawResource("customization_example")
    }


    override fun onStop() {
        // Activity onStop call must be passed to both MapView and MapKit instance.
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

}