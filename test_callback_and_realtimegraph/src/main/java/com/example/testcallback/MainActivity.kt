package com.example.testcallback

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class MainActivity : AppCompatActivity(), MyCallback {

    private lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val thread = TestThread(this, applicationContext)
        thread.start()
        chart = findViewById(R.id.chart)

        // enable description text

        // enable description text
        chart.getDescription().setEnabled(true)

        // enable touch gestures

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging

        // enable scaling and dragging
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)

        // if disabled, scaling can be done on x- and y-axis separately

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true)

        // set an alternative background color

        // set an alternative background color
        chart.setBackgroundColor(Color.LTGRAY)

        val data = LineData()
        data.setValueTextColor(Color.WHITE)

        // add empty data

        // add empty data
        chart.setData(data)

        // get the legend (only possible after setting data)

        // get the legend (only possible after setting data)
        val l: Legend = chart.getLegend()

        // modify the legend ...

        // modify the legend ...
        l.setForm(LegendForm.LINE)
        l.setTextColor(Color.WHITE)

        val xl: XAxis = chart.getXAxis()
        xl.textColor = Color.WHITE
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis: YAxis = chart.getAxisLeft()
        leftAxis.textColor = Color.WHITE
        //leftAxis.axisMaximum = 1000f
        //leftAxis.axisMinimum = -1000f

        leftAxis.setDrawGridLines(true)

        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.isEnabled = false
    }

    override fun onWork(num: Int) {
        //Log.d("MyCallback", num)
        addEntry(num)
    }

    private fun addEntry(num: Int) {
        val data = chart.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(
                Entry(
                    set.entryCount.toFloat(),
                    //(Math.random() * 40).toFloat() + 30f
                    num.toFloat()
                ), 0
            )
            data.notifyDataChanged()

            // let the chart know it's data has changed
            chart.notifyDataSetChanged()

            // limit the number of visible entries
            //chart.setVisibleXRangeMaximum(120f)
            chart.setVisibleXRangeMaximum(50f)
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.entryCount.toFloat())

            // this automatically refreshes the chart (calls invalidate())
            // chart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    private fun createSet(): LineDataSet? {
        val set = LineDataSet(null, "Dynamic Data")
        set.axisDependency = AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.WHITE)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }
}