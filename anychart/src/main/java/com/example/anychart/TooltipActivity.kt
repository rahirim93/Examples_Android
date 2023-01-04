package com.example.anychart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.TooltipDisplayMode
import com.anychart.enums.TooltipPositionMode
import kotlinx.android.synthetic.main.activity_tooltip.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TooltipActivity : AppCompatActivity() {

    private lateinit var chart: com.anychart.charts.Cartesian

    private lateinit var inputStream: InputStream
    private lateinit var bufferedReader: BufferedReader

    private var counterDisplayMode = 0
    private var counterPositionMode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tooltip)

        chart = AnyChart.line()
        any_chart_view.setChart(chart)

        importCsvToDatabase()

        buttonTooltip.setOnClickListener {
            /** Для форматирования tooltip в данном случае передаем в функцию format функцию javaScript в виде строки
             * this.value это величина y в формате String. Для дальнейшей работы нужно перевести ее в формат чисел.
             * Для этого используем функцию Number(value: String). toFixed обрезает количество знаков после запятой
             * до указанного количества. this.series это имя набора данных которые указано при создании линии. Для
             * проверки как это работает при запуске окна тыкнуть на график посмотреть как она выглядит до форматирования.
             * Далее нажать на кнопку и тыкнуть на график еще раз чтобы увидеть ее в отформатированном виде.
             * Функция typeof value возвращает тип переменной в виде строки*/
            // Форматируем подсказку давления
            chart
                .getSeriesAt(0)
                .tooltip()
                .format("function() {\n" +
                        //"var typeOfValue = typeof this.value;" +                               // Пример использования typeof
                        "var pressureInString = this.value;" +                                   // Величина у в виде String
                        "var pressureInLong = Number(pressureInString);" +                       // Преобразуем у в число
                        "var pressureInLongFixed = pressureInLong.toFixed(2);" +                 // Обрезаем количество знаков после запятой до 2
                        "return this.seriesName + ': ' + pressureInLongFixed + ' мм рт.ст.';" +
                        "\n" +
                        "}")
            /** Те же самые шаги что и для давления. Далее величины давления температуры очень различаются и будут
             * на одной оси у далеко друг от друга. Чтобы сделать их поближе при добавлении линии в график домножаем
             * температуру на коэффициент (в давнном случае 2.7). Чтобы температура корректно отображалась в подсказке
             * при форматировании делим ее на этот же коэффициент (2.7) и отнимаем 273 чтобы перевести градусы кельвина
             * в градусы по цельсию. В конце добавляем символ градусов цельсия по кодировке UTF. */
            // Форматируем подсказку температуры
            chart
                .getSeriesAt(1)
                .tooltip()
                .format("function() {\n" +
                        "var tempInString = this.value;" +                          // Величина у в виде String
                        "var tempInLong = Number(tempInString);" +                  // Преобразуем у в число
                        "var tempCorrected = tempInLong / 2.7;" +                   // Домножаем на 2.7 (см. описание выше)
                        "var tempInCelsius = tempCorrected - 273;" +                // Преобразуем в градусы Цельсия
                        "var tempFixed = tempInCelsius.toFixed(2);" +               // Обрезаем количество знаков после запятой до 2
                        "return this.seriesName + ': ' + tempFixed + ' \u2103';" +
                        "\n" +
                        "}")
            // Форматируем заголовок подсказки
            /** Те же самые шаги только для заголовка подсказки который является величиной х.
             * this.x возвращает значение х в формате строки, поэтому его предварительно надо
             * преобразовать в число. Далее на основе числа создаем объет Date и раскладываем
             * его на составляющие. */
            chart.tooltip().titleFormat("function() {\n" +
                    "var timeMillisString = this.x;" +          // Время в милликекундах в формате строки
                    "var timeMillisNumber = Number(this.x);" +  // Преобразовываем время в число
                    "var date = new Date(timeMillisNumber);" +  // Создаем объект Date на основе времени в миллисекундах и раскладываем его на составляющие
                    "var year = date.getFullYear();" +
                    "var month = date.getMonth() + 1;" +        // Месяц возвращается от 0 до 11, поэтому прибавляем 1
                    "var day = date.getDate();" +
                    "var hours = date.getHours();" +
                    "var minutes = date.getMinutes();" +
                    "var seconds = date.getSeconds();" +
                    //"return typeof timeMillisNumber;" +
                    "return day + '.' + month + '.' + year + '  ' + hours + ':' + minutes + ':' + seconds;" +
                    "\n" +
                    "}")
        }

        buttonTooltip3.setOnClickListener {
            // Средство настройки режима отображения. Примечание: Работает только для всплывающей подсказки диаграммы.
            when (counterDisplayMode) {
                0 -> {
                    chart.tooltip().displayMode(TooltipDisplayMode.SEPARATED) // Каждая серия диаграммы имеет свою собственную всплывающую подсказку.
                    counterDisplayMode += 1
                }
                1 -> {
                    chart.tooltip().displayMode(TooltipDisplayMode.SINGLE) // Всплывающая подсказка отображается в ближайшей к курсору точке. TextFormatter содержит информацию только об одной точке.
                    counterDisplayMode += 1
                }
                2 -> {
                    chart.tooltip().displayMode(TooltipDisplayMode.UNION) // Отображает только одну всплывающую подсказку (не зависит от количества серий), но TextFormatter содержит информацию обо всех точках серии.
                    counterDisplayMode = 0
                }
            }
        }

        buttonTooltip4.setOnClickListener {
            // Установщик для всех режимов позиционирования всплывающих подсказок.
            when (counterPositionMode) {
                0 -> {
                    chart.tooltip().positionMode(TooltipPositionMode.CHART) // Положение всплывающей подсказки определяется диаграммой.
                    counterPositionMode += 1
                }
                1 -> {
                    chart.tooltip().positionMode(TooltipPositionMode.POINT) // Всплывающая подсказка следует за курсором.
                    counterPositionMode += 1
                }
                2 -> {
                    chart.tooltip().positionMode(TooltipPositionMode.FLOAT) // Всплывающая подсказка отображается в фиксированном положении рядом с точкой.
                    counterPositionMode = 0
                }
            }
        }

        buttonChangeAnchor.setOnClickListener {
            // Не долелано
            chart.getSeriesAt(0).tooltip().anchor(Anchor.RIGHT_TOP)
            chart.getSeriesAt(1).tooltip().anchor(Anchor.LEFT_TOP)
        }

    }

    private fun updateTextView() {
        //var displayMode = if(counterDisplayMode)
    }

    private fun importCsvToDatabase() {
        val dataPressure = arrayListOf<DataEntry>()
        val dataTemperature = arrayListOf<DataEntry>()
        inputStream = resources.openRawResource(R.raw.sample)
        bufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.readLine() // Считываем первую строку заголовков столбов чтобы не использовать ее в дальнейшем при заполнении БД
        try {
            while (true) {
                val data = bufferedReader.readLine()
                if (data != null) {
                    val dataSplit = data.split(",")
                    dataPressure.add(ValueDataEntry(dataSplit[1].toLong(), (dataSplit[3].toInt())/1.33)) // Делим на 1.33 чтобы перевести давление из гПа в мм рт.ст.
                    dataTemperature.add(ValueDataEntry(dataSplit[1].toLong(), dataSplit[2].toFloat() * 2.7)) // Домножаем на 2.7 чтобы сделать линии поближе друг к другу
                } else {
                    break
                }
            }
            chart.run {
                line(dataPressure).stroke("0.4 black").name("Давление")
                line(dataTemperature).stroke("0.4 red").name("Температура")
            }
        } catch (e: java.lang.Exception) {
            Log.d("csv", "Exception")
            Log.d("csv", "${e.printStackTrace()}")
        }
        inputStream.close()
        bufferedReader.close()
        Log.d("csv", "End")
    }
}