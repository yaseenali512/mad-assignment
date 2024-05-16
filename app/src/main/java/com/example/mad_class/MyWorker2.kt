package com.example.mad_class

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker2(context:Context, workparams:WorkerParameters)
    : Worker(context, workparams) {
    override fun doWork(): Result {
        Log.d("Work Manager", "Work has been done")
        return Result.success()
    }
}