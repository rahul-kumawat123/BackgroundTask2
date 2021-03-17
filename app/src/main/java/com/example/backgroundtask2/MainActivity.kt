package com.example.backgroundtask2

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    companion object{
        const val jobId = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        scheduleJobButton.setOnClickListener {
            startJob()
        }


        cancelJobButton.setOnClickListener {
            cancelJob()
        }

    }

    private fun cancelJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(jobId)
        showToast("Job Cancelled")
    }


    private fun startJob() {
        showToast("Job wll start in a moment..")
        val component = ComponentName(this, JobSchedulerService::class.java)
        val info =
            JobInfo.Builder(jobId,component)
                    .setMinimumLatency(1000)
                    .setPersisted(true)
                    .build()


        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        if(scheduler.schedule(info) == JobScheduler.RESULT_SUCCESS){
            showToast("Job Scheduled")
        }else
            showToast("There is a problem while scheduling job")
    }
}