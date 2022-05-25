package com.example.bluetoothconnecting

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder

class ConnectedThread (mmSocket: BluetoothSocket?, myHandler: Handler) : Thread() {
    private val mmInStream: InputStream? = mmSocket?.inputStream
    private val mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream
    var handler = myHandler

    override fun run() {
        var numBytes: Int // bytes returned from read()
        var stringBuilder = StringBuilder()

        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            // Read from the InputStream.
            try {
                numBytes = mmInStream?.read(mmBuffer)!! // Запись входящего потока в буффер mmBuffer. numBytes присваиваем количество считанных байтов
                var stringIncome = String(mmBuffer,0, numBytes) // Формируем строку на основе принятого массива байтов
                stringBuilder.append(stringIncome)                      // Добавляем сформированную строку в StringBuilder
                /** Далее записываем переменной endLineIndex индекс конца строки
                 * по символу переноса каретки. В данном случае входящая строка
                 * одна (с ардуино для теста отправляем только одну строку), если их
                 * несколько, то со стороны ардуино нужно отправлять после каждого слова
                 * символ-метку и искать индексы символов-меток с присвоением их отдельным
                 * переменным */
                var endLineIndex = stringBuilder.indexOf("\r\n")

                if (endLineIndex > 0) {
                    // Если в принятом массиве что-то есть (индекс конца строки не равен 0)
                    var stringToPrint = stringBuilder.substring(0, endLineIndex) // Извлекаем строку в отдельную переменную
                    /** В данном случае выводим строку в лог, но если принимаемых
                     * параметров несколько, продолжаем их извлечение по символам-меткам */
                    Log.d("MyLog", "Message: $stringToPrint")
                    stringBuilder.delete(0, stringBuilder.length) // Очищаем StringBuilder

                    //Формируем сообщение и отправляев строку в handler для отображения в другом потоке
                    var msg = handler.obtainMessage(0, stringToPrint)
                    handler.sendMessage(msg)
                }
            } catch (e: IOException) {
                Log.d("MyLog", "Input stream was disconnected", e)
                break
            }
        }
    }
}