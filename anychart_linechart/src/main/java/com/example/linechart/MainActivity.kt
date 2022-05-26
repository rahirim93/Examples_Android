package com.example.linechart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode

class MainActivity : AppCompatActivity() {

    private lateinit var anyChartView: AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anyChartView = findViewById(R.id.any_chart_view)

        anyChartView = findViewById(R.id.any_chart_view)
        anyChartView.setProgressBar(findViewById(R.id.progress_bar))

        var cartesian: Cartesian = AnyChart.line()

        //cartesian.animation(true)

        //cartesian.padding(10, 20, 5, 20)

        //cartesian.crosshair().enabled(true)
        //cartesian.crosshair().yLabel(true).yStroke( null as Stroke, null, null, null as String, null as String)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        //cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.")

        //cartesian.yAxis(0).title("Number of Bottles Sold (thousand)")
        //cartesian.xAxis(0).labels().padding(5,5,5,5)

        var seriesData = arrayListOf<DataEntry>()
        seriesData.add(CustomDataEntry("1986", 3.6, 2.3, 2.8))
        seriesData.add(CustomDataEntry("1987", 7.1, 4.0, 4.1))
        seriesData.add(CustomDataEntry("1988", 8.5, 6.2, 5.1))
        seriesData.add(CustomDataEntry("1989", 9.2, 11.8, 6.5))

        var set = Set.instantiate()
        set.data(seriesData)
        var series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        var series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        var series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        var series1 = cartesian.line(series1Mapping).apply {
            name("Brandy")
            hovered().markers().enabled(true)
            hovered().markers().type(MarkerType.CIRCLE).size(4)
            tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5)
                .offsetY(5)
        }

        var series2 = cartesian.line(series2Mapping).apply {
            name("Whiskey")
            hovered().markers().enabled(true)
            hovered().markers().type(MarkerType.CIRCLE).size(4)
            tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5)
                .offsetY(5)
        }

        var series3 = cartesian.line(series3Mapping).apply {
            name("Tequila")
            hovered().markers().enabled(true)
            hovered().markers().type(MarkerType.CIRCLE).size(4)
            tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5)
                .offsetY(5)
        }

//        cartesian.legend().apply {
//            enabled(true)
//            fontSize(13)
//            padding(0,0,10,0)
//        }

        anyChartView.setChart(cartesian)

            //set.append()
    }

    private class CustomDataEntry2(
        x: String,
        value: Number
    ): ValueDataEntry(x, value)

    private class CustomDataEntry(
        var x: String,
        var value: Number,
        value2: Number,
        value3: Number): ValueDataEntry(x, value) {

    }

    fun sampleTwo() {

    }

    fun sampleOne(){

    }
}