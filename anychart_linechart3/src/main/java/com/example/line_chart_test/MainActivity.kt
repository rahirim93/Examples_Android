package com.example.line_chart_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.ScaleTypes


class MainActivity : AppCompatActivity() {

    private lateinit var anyChartView: AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        anyChartView = findViewById(R.id.any_chart_view)

        var line = AnyChart.line()

        var data = arrayListOf<DataEntry>()
        data.add(ValueDataEntry(0, 1))
        data.add(ValueDataEntry(2, 3))
        data.add(ValueDataEntry(5, 4))
        data.add(ValueDataEntry(10, 3))
        data.add(ValueDataEntry(20, 6))
        data.add(ValueDataEntry(50, 8))
        data.add(ValueDataEntry(51, 0))
        data.add(ValueDataEntry(52, 1))
        data.add(ValueDataEntry(53, 5))

        var seriesData = line.line(data)

        /* line.xScale(ScaleTypes.LINEAR)
        Тип линейного масштаба - это тип масштаба по умолчанию для
        большинства диаграмм. Значения для этой шкалы должны быть числами,
        а интервалы шкалы будут равномерно распределены вдоль оси.
        Этот тип шкалы используется, когда значения находятся в разумных пределах.*/
        line.xScale(ScaleTypes.LINEAR)

        anyChartView.setChart(line)
    }
}