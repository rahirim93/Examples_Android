package com.example.share_data

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.share_data.databinding.ActivityMainBinding
import com.github.javafaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

/** 1. Открыть вкладку todo чтобы видеть что нужно сделать.
 *  2. Создать в папке res/xml файл paths.
 *  3. Добавить file provider в манифест.
 *  4. Добавить зависимость библиотеки генерации случайных данных.
 *  5. Добавить зависимость библиотеки корутин.
 *  6. Записать в файл случайно сгенерированные данные.
 *  7. Поделиться файлом.
 *  8. Удалить файл.
 *  */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val faker = Faker()

        binding.button.setOnClickListener {
            // TODO 6. Записать в файл случайно сгенерированные данные.
            GlobalScope.launch(Dispatchers.Unconfined) {
                val file = File(filesDir, "testFile.txt")
                val fileWriter = FileWriter(file, true)
                for (i in 0..100000) {
                    fileWriter.write("${faker.name()},${faker.app()},${faker.address()}\n")
                }
                fileWriter.close()
                log("Запись закончена.")
            }
        }
        binding.button2.setOnClickListener {
            // TODO 7. Поделиться файлом.
            val file = File(filesDir, "testFile.txt")
            val fileUri: Uri? = try {
                FileProvider.getUriForFile(
                    this@MainActivity,
                    "com.example.share_data.fileprovider",
                    file
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_STREAM, fileUri)
            intent.type = "text/plain"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent.createChooser(intent, "Share file:"))
        }
        binding.button3.setOnClickListener {
            // TODO 8. Удалить файл.
            val file = File(filesDir, "testFile.txt")
            file.delete()
        }
        binding.button4.setOnClickListener {

        }
    }

    private fun log(message: String) {
        Log.d("com.example.share_data", message)
    }
}