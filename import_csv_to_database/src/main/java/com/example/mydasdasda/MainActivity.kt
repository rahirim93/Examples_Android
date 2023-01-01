package com.example.mydasdasda

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.testworker.database.WeatherItemDB
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonLog: Button
    private lateinit var buttonBD: Button
    private lateinit var buttonDelete: Button

    private lateinit var textView: TextView

    private lateinit var inputStream: InputStream
    private lateinit var bufferedReader: BufferedReader

    private val mainViewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        buttonLog = findViewById(R.id.buttonLog)
        buttonLog.setOnClickListener {
            printLogTest()
        }
        buttonBD = findViewById(R.id.buttonBD)
        buttonBD.setOnClickListener {
            importCsvToDatabase()
        }
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            mainViewModel.deleteAll()
        }

        val databaseObserver = Observer<List<WeatherItemDB>> {
            textView.text = "Кол-во записей: ${it.size}"
        }
        mainViewModel.weatherItemsLiveData.observe(this, databaseObserver)

        //inputStream = resources.openRawResource(R.raw.sample)
        //bufferedReader = BufferedReader(InputStreamReader(inputStream))
//        try {
//            while (bufferedReader.readLine() != null) {
//                var data = bufferedReader.readLine().split(",")
//                //var data = bufferedReader.readLine()
//                Log.d("csv", "$data")
//                Log.d("csv", data[0])
//                Log.d("csv", data[1])
//                Log.d("csv", data[2])
//                Log.d("csv", data[3])
//                var itemDB = WeatherItemDB()
//                itemDB.id = UUID.fromString(data[0])
//                itemDB.dateInMillis = data[1].toLong()
//                itemDB.temperature = data[2].toFloat()
//                itemDB.pressure = data[3].toInt()
//                WeatherRepository.get().addItem(weatherItemDB = itemDB)
//            }
//        } catch (e: java.lang.Exception) {}
    }

    private fun printLog() {
        inputStream = resources.openRawResource(R.raw.sample)
        bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val inputStreamSize = inputStream.available()
        var counterReaderSize = 0
        try {
            while (bufferedReader.readLine() != null) {
                val data = bufferedReader.readLine().split(",")
                Log.d("csv", "$data")
                Log.d("csv", data[0])
                Log.d("csv", data[1])
                Log.d("csv", data[2])
                Log.d("csv", data[3])
                counterReaderSize += data.toString().toByteArray().size

            }
        } catch (e: java.lang.Exception) {
            Log.d("csv", "Exception")
            Log.d("csv", "${e.printStackTrace()}")
        }
        Log.d("csv", "$inputStreamSize")
        Log.d("csv", "$counterReaderSize")
        inputStream.close()
        bufferedReader.close()
    }

    private fun printLogTest() {
        inputStream = resources.openRawResource(R.raw.sample)
        bufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.readLine() // Считываем первую строку заголовков столбов чтобы не использовать ее в дальнейшем
        var counter = 1
        try {
            while (true) {
                val data = bufferedReader.readLine()
                if (data != null) {
                    Log.d("csv", "$counter Data:\t\t $data")
                    val dataSplit = data.split(",")
                    Log.d("csv", "\tData split:\t$dataSplit")
                    Log.d("csv", "")
                    counter += 1
                } else {
                    break
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d("csv", "Exception")
            Log.d("csv", "${e.printStackTrace()}")
        }
        inputStream.close()
        bufferedReader.close()
        Log.d("csv", "End")
    }

    private fun importCsvToDatabase() {
        inputStream = resources.openRawResource(R.raw.sample)
        bufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.readLine() // Считываем первую строку заголовков столбов чтобы не использовать ее в дальнейшем при заполнении БД
        try {
            while (true) {
                val data = bufferedReader.readLine()
                if (data != null) {
                    val dataSplit = data.split(",")
                    val weatherItemDB = WeatherItemDB().apply {
                        id = UUID.fromString(dataSplit[0])
                        dateInMillis = dataSplit[1].toLong()
                        temperature = dataSplit[2].toFloat()
                        pressure = dataSplit[3].toInt()
                    }
                    mainViewModel.addItem(weatherItemDB)
                } else {
                    break
                }
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