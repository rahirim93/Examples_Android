package com.example.bluetoothconnecting

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Данный пример показывает как найти и подсоединиться к заранее известному
 * блютуз устройству.
 * 1) Нужно объявить необходимые разрешения в манифесте
 * 2) Запросить необходимые разрешения (функция requestPermissions)
 * 3) Настройить шикоровещательный приемкник для поиска устройств
 */
class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    private lateinit var handler: Handler

    private lateinit var buttonConnect: Button

    //Инициализация встроенного блютуз устройства
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    //Искомый, заранее известный, адрес
    val bluetoothAddress = "98:D3:31:F9:8D:32"

    //Переменная для хранения найденного устройства
    private lateinit var myDevice: BluetoothDevice

    //Переменная-признак что искомое устройство найдено
    var founded = false

    private lateinit var connectThread: ConnectThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        //Запрос необходимых разрешений
        requestPermissions()

        // Register for broadcasts when a device is discovered.
        createBroadcastReceiver()

    }


    private fun init() {
        buttonConnect = findViewById(R.id.buttonConnect)
        buttonConnect.isEnabled = false //Заблокировать кнопку
        buttonConnect.setBackgroundColor(Color.RED) //Изменить цвет кнопки

        textView = findViewById(R.id.textView)

        //Handler для приема сообщения из потока соединения и вывода его в textView
        handler = object: Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) {
                textView.text = "Сообщение: ${msg.obj}"
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN),
            1 )
    }

    private fun createBroadcastReceiver() {
        //Настройка фильтра для перехвата найденных устройств
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object  : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    println("Name: ${device?.name}   Address: ${device?.address}")
                    /* Если адрес найденного устройства совпадает с искомым
                    и устройство найдено не было (founded == false)*/
                    if (device?.address.equals(bluetoothAddress) && !founded) {
                        if (device != null) {
                            myDevice = device //Инициализируем myDevice, найденным устройством
                            founded = true //Устройство найдено (founded = true)
                            buttonConnect.isEnabled = true //Разблокировать кнопку соединения
                            buttonConnect.setBackgroundColor(Color.GREEN) //Изменить цвет кнопки
                            Toast.makeText(context, "Устройство найдено", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }

    fun buttonConnect(view: android.view.View) {
        if(myDevice != null) {
            //Создаем переменную потока и запускаем
            connectThread = ConnectThread(myDevice, handler)
            connectThread.start()
            Toast.makeText(this, "Попытка соединения", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Device = null", Toast.LENGTH_SHORT).show()
        }
    }

    fun buttonStartDiscovery(view: android.view.View) {
        //Запуск поиска устройств
        bluetoothAdapter?.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }
}