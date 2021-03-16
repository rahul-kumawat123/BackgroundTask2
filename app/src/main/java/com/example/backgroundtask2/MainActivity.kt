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


fun Context.showToast(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

class MainActivity : AppCompatActivity() {

    companion object{
        const val jobId = 1
    }

    //@RequiresApi(Build.VERSION_CODES.O)
    //@SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        scheduleJobButton.setOnClickListener {

            /*val myJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder(1,
                    ComponentName(this, JOB_SCHEDULER_SERVICE::class.java))

            val job = jobInfo.setMinimumLatency(1000)
                    //.setPeriodic(5000)
                    //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()

            myJobScheduler.schedule(job)*/

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
        val component = ComponentName(this, JOB_SCHEDULER_SERVICE::class.java)
        val info =
            JobInfo.Builder(jobId,component)
                    //.setRequiresBatteryNotLow(true)
                    .setMinimumLatency(1000)
                    //.setPersisted(true)
                    //.setPeriodic(10*60*1000)
                    .build()



        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        if(scheduler.schedule(info) == JobScheduler.RESULT_SUCCESS){
            showToast("Job Scheduled")
        }else
            showToast("There is a problem while scheduling job")
    }

}