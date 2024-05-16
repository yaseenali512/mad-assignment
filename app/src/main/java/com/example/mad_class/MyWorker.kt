package com.example.mad_class

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

//Step 1: Creating work
class MyWorker(context: Context, workparams:WorkerParameters) :
    Worker(context, workparams) {

    override fun doWork(): Result {
        var username = inputData.getString("username")
        var password = inputData.getString("password")


        if (username == "admin" && password == "admin"){
            return Result.success()
        }else{
            return Result.failure()
        }
    }
}