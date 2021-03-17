package com.example.backgroundtask2

import android.app.Notification
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.annotation.RequiresApi
import kotlin.concurrent.thread

class JobSchedulerService: JobService() {


    override fun onStartJob(params: JobParameters?): Boolean {

        myJobHandler.sendMessage(Message.obtain(myJobHandler,MainActivity.jobId,params))

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        myJobHandler.removeMessages(MainActivity.jobId)

        return false
    }

    private val myJobHandler : Handler = Handler(object  : Handler.Callback {

        override fun handleMessage(msg: Message): Boolean {
            Log.e("Job Scheduler", "Job running")
            showToast("Job Running")
            for (i in 0..5){
                showToast("Running Job: $i")
                Log.e("Job Scheduler", "Running Job: $i")
            }
            jobFinished(msg.obj as JobParameters,
                    false)
            showToast("Job Finished")
            return true
        }
    })
}