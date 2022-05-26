package com.example.line_chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Validators.or
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Line
import com.anychart.charts.Cartesian
import com.anychart.scales.Base
import com.anychart.scales.Ordinal

class MainActivity : AppCompatActivity() {

    private lateinit var anyChartView: AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anyChartView = findViewById(R.id.any_chart_view)

        var line = AnyChart.line()

        //chart.xScale(anychart.scales.ordinal());


        //line.xScale().mode('continuous');

        var data = arrayListOf<DataEntry>()
        data.add(ValueDataEntry("a", 1))
        data.add(ValueDataEntry("b", 3))
        data.add(ValueDataEntry("c", 4))
        data.add(ValueDataEntry("d", 3))
        data.add(ValueDataEntry("e", 6))
        data.add(ValueDataEntry("f", 8))
        data.add(ValueDataEntry("g", 0))
        data.add(ValueDataEntry("j", 1))
        data.add(ValueDataEntry("k", 5))

        line.data(data)

        anyChartView.setChart(line)
    }
}