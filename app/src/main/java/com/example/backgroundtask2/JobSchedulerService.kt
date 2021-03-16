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

//@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class JobSchedulerService: JobService() {

    private var IS_JOB_CANCELLED = false


    /*private val myJobHandler : Handler = Handler(object  : Handler.Callback {

        override fun handleMessage(msg: Message): Boolean {
            Log.e("Job Scheduler", "Job running")
            jobFinished(msg.obj as JobParameters, false)
            return true
        }
    })*/



    override fun onStartJob(params: JobParameters?): Boolean {

        showToast("Job Started")
       // myJobHandler.sendMessage(Message.obtain(myJobHandler,1,params))
        backgroundJobSchedulerTask(params)
        
        return true
    }

    private fun backgroundJobSchedulerTask(params: JobParameters?) {

        thread {
            kotlin.run {
                (0..5).forEach { it -> showToast("Running Job: $it")
                    if (IS_JOB_CANCELLED){
                        return@run
                    }
                    Thread.sleep( 1000)
                }
                showToast("Job Finished")
                jobFinished(params,false )
            }
        }.start()

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        showToast("Job cancelled before completion")
        IS_JOB_CANCELLED = true
        //myJobHandler.removeMessages(1 )
        return true
    }
}