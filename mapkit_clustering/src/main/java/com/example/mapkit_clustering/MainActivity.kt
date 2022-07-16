package com.example.mapkit_clustering

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import java.util.*

private const val FONT_SIZE = 15f
private const val MARGIN_SIZE = 3f
private const val STROKE_SIZE = 3f
private const val PLACEMARKS_NUMBER = 2000





class MainActivity : AppCompatActivity(), ClusterListener, ClusterTapListener {

    private val MAPKIT_API_KEY = "9364f347-6156-4b9a-939e-300993a4c56e"

    private lateinit var manager: WindowManager

    private lateinit var mapView: MapView
    private val CLUSTER_CENTERS = Arrays.asList(
        Point(55.756, 37.618),
        Point(59.956, 30.313),
        Point(56.838, 60.597),
        Point(43.117, 131.900),
        Point(56.852, 53.204)
    )

    class TextImageProvider(private val text: String, private val manager: WindowManager) : ImageProvider() {

        override fun getId(): String {
            return "text_$text"
        }

        override fun getImage(): Bitmap {
            val metrics = DisplayMetrics()

            manager.defaultDisplay.getMetrics(metrics)
            val textPaint = Paint()
            textPaint.textSize = FONT_SIZE * metrics.density
            textPaint.textAlign = Align.CENTER
            textPaint.style = Paint.Style.FILL
            textPaint.isAntiAlias = true
            val widthF = textPaint.measureText(text)
            val textMetrics = textPaint.fontMetrics
            val heightF = Math.abs(textMetrics.bottom) + Math.abs(textMetrics.top)
            val textRadius =
                Math.sqrt((widthF * widthF + heightF * heightF).toDouble()).toFloat() / 2
            val internalRadius: Float =
                textRadius + MARGIN_SIZE * metrics.density
            val externalRadius: Float =
                internalRadius + STROKE_SIZE * metrics.density
            val width = (2 * externalRadius + 0.5).toInt()
            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val backgroundPaint = Paint()
            backgroundPaint.isAntiAlias = true
            backgroundPaint.color = Color.RED
            canvas.drawCircle(
                (width / 2).toFloat(),
                (width / 2).toFloat(),
                externalRadius,
                backgroundPaint
            )
            backgroundPaint.color = Color.WHITE
            canvas.drawCircle(
                (width / 2).toFloat(),
                (width / 2).toFloat(),
                internalRadius,
                backgroundPaint
            )
            canvas.drawText(
                text, (
                        width / 2).toFloat(),
                width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
                textPaint
            )
            return bitmap
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        mapView = findViewById(R.id.mapview)
        mapView.map.move(
            CameraPosition(
                CLUSTER_CENTERS[0], 3F, 0F, 0F
            )
        )
        val imageProvider = ImageProvider.fromResource(
            this@MainActivity, R.drawable.search_result
        )

        // Note that application must retain strong references to both
        // cluster listener and cluster tap listener

        // Note that application must retain strong references to both
        // cluster listener and cluster tap listener
        val clusterizedCollection = mapView.map.mapObjects.addClusterizedPlacemarkCollection(this)

        var points: List<Point>? =
            createPoints(PLACEMARKS_NUMBER)
        clusterizedCollection.addPlacemarks(points!!, imageProvider, IconStyle())


        clusterizedCollection.clusterPlacemarks(60.0, 15)

    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }



    override fun onClusterAdded(cluster: Cluster) {
// We setup cluster appearance and tap handler in this method

        // We setup cluster appearance and tap handler in this method
        cluster.appearance.setIcon(
            TextImageProvider(cluster.size.toString(), manager)
        )
        cluster.addClusterTapListener(this)
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        Toast.makeText(
            applicationContext,
            String.format(getString(R.string.cluster_tap_message), cluster.getSize()),
            Toast.LENGTH_SHORT
        ).show()

        // We return true to notify map that the tap was handled and shouldn't be
        // propagated further.

        // We return true to notify map that the tap was handled and shouldn't be
        // propagated further.
        return true
    }

    private fun createPoints(count: Int): List<Point>? {
        val points = ArrayList<Point>()
        val random = Random()
        for (i in 0 until count) {
            val clusterCenter = CLUSTER_CENTERS[random.nextInt(CLUSTER_CENTERS.size)]
            val latitude = clusterCenter.latitude + Math.random() - 0.5
            val longitude = clusterCenter.longitude + Math.random() - 0.5
            points.add(Point(latitude, longitude))
        }
        return points
    }
}