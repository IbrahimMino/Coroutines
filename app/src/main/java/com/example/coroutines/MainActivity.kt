package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        runBlocking {
            getRetrived()
        }
        val tv = findViewById<TextView>(R.id.tv_hello)
     val job: Job =   GlobalScope.launch(Dispatchers.IO) {
            Log.d("TAG", "COROUTINES - ${Thread.currentThread().name}")
             val response = getRetrived()
            withContext(Dispatchers.Main){
                tv.text = response
            }
        }

        runBlocking {
            delay(4000)
            job.cancel() }



        GlobalScope.launch {
            val ret = async { getRetrived() }
            Log.d("TAG", ret.await())

            GlobalScope.launch {
                val retDate = async { getDataBase() }
                Log.d("TAG", retDate.await())
            }
             }
        }

    }
//suspend function ni Agar GlobalScope dan tashqari runBlocking{} ichida chaqirish mumkin.
    suspend fun getRetrived():String{
        delay(5000)
        return "Date retrived"
    }

    suspend fun getDataBase():String {
        delay(3000)
        return "DateBase retrived"
    }
}